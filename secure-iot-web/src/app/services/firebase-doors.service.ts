import { Injectable } from '@angular/core';
import { Database, ref, set } from '@angular/fire/database';
import { onValue } from 'firebase/database';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FirebaseDoorsService {
  constructor(private db: Database) {}

  getDoorStatus(doorName: string): Observable<boolean> {
    const doorRef = ref(this.db, `servos/${doorName}`);
    return new Observable((observer) => {
      onValue(doorRef, (snapshot) => {
        observer.next(snapshot.val() === true);
      });
    });
  }

  updateDoorStatus(doorName: string, status: boolean) {
    const doorRef = ref(this.db, `servos/${doorName}`);
    return set(doorRef, status);
  }

  getAllDoorsStatus(): Observable<any> {
    const doorsRef = ref(this.db, 'servos');
    return new Observable((observer) => {
      onValue(doorsRef, (snapshot) => {
        observer.next(snapshot.val());
      });
    });
  }
}
