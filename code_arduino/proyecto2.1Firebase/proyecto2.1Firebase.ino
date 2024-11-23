#include <WiFi.h>
#include <FirebaseESP32.h>
#include <WebServer.h>
#include <MFRC522.h>
#include <SPI.h>
#include <ESP32Servo.h>
#include <addons/TokenHelper.h>
#include <addons/RTDBHelper.h>

// Pines de LEDs
const int ledComedor = 15;
const int ledCocina = 2;
const int ledPatioTrasero = 4;
const int ledCuarto = 16;
const int ledBano = 17;
const int ledSala = 5;
const int ledCochera = 18;
const int ledPatioDelantero = 19;

struct Led {
  const int pin;
  const char* nombre;
};

Led leds[] = {
  { ledComedor, "Comedor" },
  { ledCocina, "Cocina" },
  { ledPatioTrasero, "PatioTrasero" },
  { ledCuarto, "Cuarto" },
  { ledBano, "Bano" },
  { ledSala, "Sala" },
  { ledCochera, "Cochera" },
  { ledPatioDelantero, "PatioDelantero" }
};

// Pines para los servos
const int servoPuertaPin = 12;
const int servoPortonPin = 13;

Servo servoPuerta;
Servo servoPorton;

// Pines del buzzer y sensores
const int buzzer = 21;
const int movPatio = 23;
const int movSala = 22;
const int sensorContacto = 36;

// Pines del módulo MFRC522
#define SDA 14
#define SCK 27
#define MOSI 26
#define MISO 25
#define RST 33

// Credenciales Firebase
#define WIFI_SSID ""
#define WIFI_PASSWORD ""
#define API_KEY ""
#define DATABASE_URL ""
#define USER_EMAIL ""
#define USER_PASSWORD ""

FirebaseConfig config;
FirebaseAuth auth;
FirebaseData firebaseData;
FirebaseData streamData;
MFRC522 mfrc522(SDA, RST);

bool alarmaActivada = false;
bool alarmaCompletaActivada = false;

bool movimientoDetectado = false;
bool contactoDetectado = true;

void apagarLuces() {
  static bool lucesApagadas = false;
  if (lucesApagadas) return; // Evitar apagar las luces si ya están apagadas

  for (Led led : leds) {
    digitalWrite(led.pin, LOW);
  }
  lucesApagadas = true; // Marcar las luces como apagadas
  Serial.println("Todas las luces apagadas.");
}

void streamCallback(StreamData data) {
  Serial.println("Datos recibidos del Stream:");
  Serial.println("Path: " + data.dataPath());
  Serial.println("Type: " + data.dataType());
  Serial.println("Event: " + data.eventType());

  // Verificar si el cambio fue en el nodo de los LEDs
  if (data.dataPath().startsWith("/leds/")) {
    String ledNombre = data.dataPath().substring(6);  // Extraer el nombre del LED
    String estado = data.stringData();

    // Buscar el LED correspondiente en el array y actualizar su estado
    for (Led led : leds) {
      if (ledNombre == led.nombre) {
        if (digitalRead(led.pin) != (estado == "on" ? HIGH : LOW)) {
          digitalWrite(led.pin, estado == "on" ? HIGH : LOW);
          Serial.println("LED " + ledNombre + " actualizado a: " + estado);
        }
        return;
      }
    }
  }

  else if (data.dataPath().startsWith("/servos/")) {
    String servoNombre = data.dataPath().substring(8);  // Extraer el nombre del servo
    bool estado = data.boolData();  // Leer el estado como booleano

    // Controlar los servos según el nombre
    if (servoNombre == "Puerta") {
      servoPuerta.write(estado ? 90 : 0);  // 90 para abrir, 0 para cerrar
      Serial.println("Servo Puerta actualizado a: " + String(estado ? "Abierto" : "Cerrado"));
    } else if (servoNombre == "Porton") {
      servoPorton.write(estado ? 90 : 0);  // 90 para abrir, 0 para cerrar
      Serial.println("Servo Porton actualizado a: " + String(estado ? "Abierto" : "Cerrado"));
    }
  }

  else if (data.dataPath() == "/alarma" && !alarmaCompletaActivada) {
    alarmaActivada = data.boolData();
    Serial.println(alarmaActivada ? "Alarma activada" : "Alarma desactivada");
  }

  else if (data.dataPath() == "/alarma_completa") {
    alarmaCompletaActivada = data.boolData();
    Serial.println(alarmaCompletaActivada ? "Alarma completa activada" : "Alarma completa desactivada");
  }
}

void streamTimeoutCallback(bool timeout) {
  if (timeout) {
    Serial.println("El stream de Firebase se ha desconectado. Reintentando...");
  }
}

void setup() {
  Serial.begin(115200);
  SPI.begin(SCK, MISO, MOSI, SDA);
  mfrc522.PCD_Init();

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Conectando a WiFi...");
  }
  Serial.println("\nConectado a WiFi con IP: " + WiFi.localIP().toString());

  // Configuración de Firebase
  config.api_key = API_KEY;
  config.database_url = DATABASE_URL;
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;
  config.token_status_callback = tokenStatusCallback;
  config.signer.tokens.legacy_token = "jcy55RW3AtFHVVkgG5gniWtKl7lzcw3NqjnCY3Ck";

  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  // Configuración de Pines de LEDs
  for (Led led : leds) {
    pinMode(led.pin, OUTPUT);
    digitalWrite(led.pin, LOW);  // Apagar todos los LEDs al inicio
  }

  pinMode(buzzer, OUTPUT);
  pinMode(movPatio, INPUT);
  pinMode(movSala, INPUT);
  pinMode(sensorContacto, INPUT);
  digitalWrite(buzzer, LOW); // Buzzer apagado al inicio

  delay(2000);

  // Configuración de los servos
  servoPuerta.attach(servoPuertaPin);
  servoPorton.attach(servoPortonPin);
  servoPuerta.write(0); // Inicialmente cerrado
  servoPorton.write(0); // Inicialmente cerrado

  // Inicia el Stream en el nodo `leds`
  if (!Firebase.beginStream(streamData, "/")) {
    Serial.println("No se pudo iniciar el Stream: " + streamData.errorReason());
  }

  // Asigna los callbacks para el Stream
  Firebase.setStreamCallback(streamData, streamCallback, streamTimeoutCallback);
}

void actualizarEstadoFirebase(const String &ruta, bool estado) {
  if (Firebase.setBool(firebaseData, ruta, estado)) {
    Serial.println("Estado actualizado en Firebase: " + ruta + " = " + String(estado ? "true" : "false"));
  } else {
    Serial.println("Error al actualizar estado en Firebase: " + firebaseData.errorReason());
  }
}

bool movimientoDetectadoPatio = false; // Indica si ya se reportó movimiento en el patio
bool movimientoDetectadoSala = false; // Indica si ya se reportó movimiento en la sala

void manejarSeguridadTotal() {
  if (alarmaCompletaActivada) {
    movimientoDetectado = (digitalRead(movPatio) == HIGH || digitalRead(movSala) == HIGH);
    contactoDetectado = digitalRead(sensorContacto) == HIGH; // Sin contacto cuando está en LOW

    // Activar el buzzer si no hay contacto o hay movimiento detectado
    if (!contactoDetectado || movimientoDetectado) {
      digitalWrite(buzzer, HIGH);
      Serial.println("Alerta activada: buzzer encendido");
    } else {
      digitalWrite(buzzer, LOW);
    }
  }
}

void manejarAlarmaManual() {
  if (!alarmaCompletaActivada) {  // Solo funciona si seguridad total no está activada
    if (alarmaActivada) {
      digitalWrite(buzzer, HIGH);
      Serial.println("Alarma manual: activada.");
    } else {
      digitalWrite(buzzer, LOW);
      Serial.println("Alarma manual: desactivada.");
    } // Sincronizar con Firebase
  }
}

void actualizarMovimientoFirebase(const char* ruta, bool estado) {
  if (Firebase.setBool(firebaseData, ruta, estado)) {
    Serial.println(String(ruta) + " = " + (estado ? "Movimiento detectado" : "Sin movimiento"));
  } else {
    Serial.println("Error al enviar estado de movimiento: " + firebaseData.errorReason());
  }
}

void loop() {
  // Leer la tarjeta RFID para alternar el estado de seguridad total
  if (mfrc522.PICC_IsNewCardPresent() && mfrc522.PICC_ReadCardSerial()) {
    alarmaCompletaActivada = !alarmaCompletaActivada;
    Firebase.setBool(firebaseData, "/alarma_completa", alarmaCompletaActivada);
    Serial.println(alarmaCompletaActivada ? "Seguridad total activada (RFID)" : "Seguridad total desactivada (RFID)");
    mfrc522.PICC_HaltA();
  }

  manejarSeguridadTotal();
  manejarAlarmaManual();
}
