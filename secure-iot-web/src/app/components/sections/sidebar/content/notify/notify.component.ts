import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FirebaseNotifyService } from '../../../../../services/firebase-notify.service';
import { LogService } from '../../../../../services/Log.service';

interface Notify {
  name: string;
  status: boolean;
}

@Component({
  selector: 'app-notify',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notify.component.html',
  styles: ``,
})
export class NotifyComponent implements OnInit {
  public notify: Notify[] = [{ name: 'Alarma', status: true }];

  securityTotalActive = false;

  constructor(
    private firebaseService: FirebaseNotifyService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.firebaseService.getAlarmaCompletaStatus().subscribe((status) => {
      this.securityTotalActive = status;
    });

    this.firebaseService.getAlarmaStatus().subscribe((status) => {
      const prevStatus = this.notify[0].status;
      this.notify[0].status = status;

      if (this.notify[0].status !== prevStatus) {
        const action = this.notify[0].status
          ? 'Se prendió la alarma'
          : 'Se apagó la alarma';
        this.logService.addLog('Alarma', '', action);
      }
    });
  }

  toggleNotifyStatus(notify: Notify): void {
    const newStatus = !notify.status;
    notify.status = newStatus;
    this.firebaseService.updateAlarmStatus(newStatus);

    const action = newStatus ? 'Alarma activada' : 'Alarma desactivada';
    this.logService.addLog('Alarma', '', action);
  }
}
