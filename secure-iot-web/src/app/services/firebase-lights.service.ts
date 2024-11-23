import { Injectable } from '@angular/core';
import { Database, ref, set } from '@angular/fire/database';
import { onValue } from 'firebase/database';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FirebaseLightsService {
  constructor(private db: Database) {}

  getLightStatus(lightName: string): Observable<boolean> {
    const lightRef = ref(this.db, `leds/${lightName}`);
    return new Observable((observer) => {
      onValue(lightRef, (snapshot) => {
        const status = snapshot.val();
        observer.next(status === true);  // Cambiado para leer valores booleanos
      });
    });
  }

  updateLightStatus(lightName: string, status: boolean) {
    const lightRef = ref(this.db, `leds/${lightName}`);
    set(lightRef, status ? 'on' : 'off');
}

  getAllLightsStatus(): Observable<any> {
    const lightsRef = ref(this.db, 'leds');
    return new Observable((observer) => {
      onValue(lightsRef, (snapshot) => {
        observer.next(snapshot.val());
      });
    });
  }
}
