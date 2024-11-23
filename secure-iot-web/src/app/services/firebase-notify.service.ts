import { Injectable } from '@angular/core';
import { Database, ref, set } from '@angular/fire/database';
import { onValue } from 'firebase/database';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FirebaseNotifyService {
  constructor(private db: Database) {}

  getAlarmaStatus(): Observable<boolean> {
    const alarmRef = ref(this.db, 'alarma');
    return new Observable((observer) => {
      onValue(alarmRef, (snapshot) => {
        observer.next(snapshot.val() === true);
      });
    });
  }

  updateAlarmStatus(status: boolean) {
    const alarmRef = ref(this.db, 'alarma');
    return set(alarmRef, status);
  }

  getAlarmaCompletaStatus(): Observable<boolean> {
    const alarmCompletaRef = ref(this.db, 'alarma_completa');
    return new Observable((observer) => {
      onValue(alarmCompletaRef, (snapshot) => {
        observer.next(snapshot.val() === true);
      });
    });
  }

  updateAlarmCompletaStatus(status: boolean) {
    const alarmCompletaRef = ref(this.db, 'alarma_completa');
    return set(alarmCompletaRef, status);
  }

  getMovimientoPatio(): Observable<boolean> {
    const sensorRef = ref(this.db, '/sensores/movPatio');
    return new Observable<boolean>((observer) => {
      onValue(sensorRef, (snapshot) => {
        observer.next(snapshot.val() as boolean);
      });
    });
  }

  getMovimientoSala(): Observable<boolean> {
    const sensorRef = ref(this.db, '/sensores/movSala');
    return new Observable<boolean>((observer) => {
      onValue(sensorRef, (snapshot) => {
        observer.next(snapshot.val() as boolean);
      });
    });
  }
}
