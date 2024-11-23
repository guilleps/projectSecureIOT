import { CommonModule } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FirebaseNotifyService } from '../../../../../services/firebase-notify.service';
import { LogService, LogEntry } from '../../../../../services/Log.service';

interface Control {
  name: string;
  status: boolean;
}

@Component({
  selector: 'app-control',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './control.component.html',
  styles: ``,
})
export class ControlComponent implements OnInit {
  @ViewChild('logContainer') logContainer!: ElementRef

  public control: Control[] = [{ name: 'Seguridad Total', status: false }];
  public today: string;
  logs: LogEntry[] = [];
  // isLoading = false;

  constructor(private firebaseService: FirebaseNotifyService, private logService: LogService) {
    const currentDate = new Date();
    const day = String(currentDate.getDate()).padStart(2, '0');
    const month = String(currentDate.getMonth() + 1).padStart(2, '0');
    const year = currentDate.getFullYear();

    this.today = `${day}/${month}/${year}`;
  }

  ngOnInit(): void {
    this.firebaseService.getAlarmaCompletaStatus().subscribe((status) => {
      const prevStatus = this.control[0].status;
      this.control[0].status = status;

      if (this.control[0].status !== prevStatus) {
        const action = this.control[0].status ? 'Se activó el sistema' : 'Se desactivó el sistema';
        this.logService.addLog('Seguridad Total', '', action);
        this.scrollToBottom();
      }
    });

    // this.firebaseService.getMovimientoPatio().subscribe((movimiento) => {
    //   // solo quiero que salga que hubo movimeinto una vez, dada la deteccion del momento segun instrucciones
    // });
  
    // // Escuchar cambios en el sensor de la sala
    // this.firebaseService.getMovimientoSala().subscribe((movimiento) => {
    //   // solo quiero que salga que hubo movimeinto una vez, dada la deteccion del momento segun instrucciones
    // });
    
    this.logs = this.logService.getLogs();
  }

  toggleControlStatus(control: Control): void {
    const newStatus = !control.status;
    control.status = newStatus;
    this.firebaseService.updateAlarmCompletaStatus(newStatus);

    const action = newStatus ? 'Se activó el sistema' : 'Se desactivó el sistema';
    this.logService.addLog('Seguridad Total', '', action);
  }

  private scrollToBottom(): void {
    setTimeout(() => {
      if (this.logContainer) {
        const container = this.logContainer.nativeElement;
        container.scrollTop = container.scrollHeight;
      }
    }, 0); // Esperar a que Angular renderice el contenido
  }
}
