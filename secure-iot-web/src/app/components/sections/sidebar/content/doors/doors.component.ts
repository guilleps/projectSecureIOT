import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FirebaseDoorsService } from '../../../../../services/firebase-doors.service';
import { LogService } from '../../../../../services/Log.service';
import { FirebaseNotifyService } from '../../../../../services/firebase-notify.service';

interface Door {
  name: string;
  status: boolean;
}

@Component({
  selector: 'app-doors',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './doors.component.html',
  styles: ``,
})
export class DoorsComponent implements OnInit {
  public doors: Door[] = [
    { name: 'Puerta', status: false },
    { name: 'Porton', status: false },
  ];

  securityTotalActive = false;

  constructor(
    private firebaseNotifyService: FirebaseNotifyService,
    private fireBaseService: FirebaseDoorsService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.firebaseNotifyService.getAlarmaCompletaStatus().subscribe((status) => {
      this.securityTotalActive = status;
      if (this.securityTotalActive) {
        this.closaAllDoors();
      }
    })

    this.fireBaseService.getAllDoorsStatus().subscribe((doorsStatus) => {
      this.doors.forEach((door) => {
        const prevStatus = door.status;
        door.status = doorsStatus[door.name] === true;

        if (door.status !== prevStatus) {
          const action = door.status
            ? 'Se abrió la puerta'
            : 'Se cerró la puerta';
          this.logService.addLog('Puertas', door.name, action);
        }
      });
    });
  }

  toggleDoorStatus(door: Door): void {
    if (!this.securityTotalActive) {
      const newStatus = !door.status;
      door.status = newStatus;
      this.fireBaseService.updateDoorStatus(door.name, newStatus);
  
      const action = newStatus ? 'Se abrió la puerta' : 'Se cerró la puerta';
      this.logService.addLog('Puertas', door.name, action);
    }
  }

  closaAllDoors(): void {
    this.doors.forEach((door) => {
      door.status = false;
      this.fireBaseService.updateDoorStatus(door.name, false);
    });
  }

  get allClosed(): boolean {
    return this.doors.every((door) => !door.status);
  }

  get allOpen(): boolean {
    return this.doors.every((door) => door.status);
  }

  toggleAllDoors(): void {
    if (!this.securityTotalActive) {
      const newStatus = !this.allOpen;
      this.doors.forEach((door) => {
        door.status = newStatus;
        this.fireBaseService.updateDoorStatus(door.name, newStatus);
      });
    }
  }
}
