import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FirebaseLightsService } from '../../../../../services/firebase-lights.service';
import { LogService } from '../../../../../services/Log.service';
import { FirebaseNotifyService } from '../../../../../services/firebase-notify.service';

interface Light {
  name: string;
  status: boolean;
}

@Component({
  selector: 'app-lights',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lights.component.html',
  styles: ``,
})
export class LightsComponent implements OnInit {
  public lights: Light[] = [
    { name: 'PatioDelantero', status: false },
    { name: 'Cochera', status: false },
    { name: 'Sala', status: false },
    { name: 'Comedor', status: false },
    { name: 'Cocina', status: false },
    { name: 'Cuarto', status: false },
    { name: 'Bano', status: false },
    { name: 'PatioTrasero', status: false },
  ];

  securityTotalActive = false;

  constructor(
    private firebaseService: FirebaseLightsService,
    private firebaseNotifyService: FirebaseNotifyService,
    private logService: LogService
  ) {}

  ngOnInit(): void {
    this.firebaseNotifyService.getAlarmaCompletaStatus().subscribe((status) => {
      this.securityTotalActive = status;
      if (this.securityTotalActive) {
        this.turnOffAllLights();
      }
    });
    
    this.firebaseService.getAllLightsStatus().subscribe((lightsStatus) => {
      this.lights.forEach((light) => {
        const prevStatus = light.status;
        light.status = lightsStatus[light.name] === 'on';

        if (light.status !== prevStatus) {
          const action = light.status ? 'Se prendieron las luces' : 'Se apagaron las luces';
          this.logService.addLog('Luces', light.name, action);
        }
      });
    });
  }

  toggleLightStatus(light: Light): void {
    if (!this.securityTotalActive) {
      const newStatus = !light.status;
      light.status = newStatus;
      this.firebaseService.updateLightStatus(light.name, newStatus);

      const action = newStatus ? 'Se prendieron las luces' : 'Se apagaron las luces';
      this.logService.addLog('Luces', light.name, action);
    }
  }

  get allOff(): boolean {
    return this.lights.every((light) => !light.status);
  }

  get allOn(): boolean {
    return this.lights.every((light) => light.status);
  }

  get toggleAllLabel(): string {
    return this.allOn ? 'Apagar Todo' : 'Prender Todo';
  }

  turnOffAllLights(): void {
    this.lights.forEach((light) => {
      light.status = false;
      this.firebaseService.updateLightStatus(light.name, false);
    });
  }

  toggleAllLights(): void {
    if (!this.securityTotalActive) {
      const newStatus = !this.lights.every((light) => light.status);
      this.lights.forEach((light) => {
        light.status = newStatus;
        this.firebaseService.updateLightStatus(light.name, newStatus);
      });
    }
  }
}
