import { Component } from '@angular/core';
import { SidebarComponent } from "../sidebar/sidebar.component";
import { DoorsComponent } from '../sidebar/content/doors/doors.component';
import { NotifyComponent } from '../sidebar/content/notify/notify.component';
import { ControlComponent } from '../sidebar/content/control/control.component';
import { LightsComponent } from '../sidebar/content/lights/lights.component';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-panel',
  standalone: true,
  imports: [RouterOutlet, SidebarComponent, DoorsComponent, NotifyComponent, ControlComponent, LightsComponent],
  templateUrl: './panel.component.html',
  styles: ``
})
export class PanelComponent {
  currentSection: string = 'doors';

  // Cambiar la sección activa según el botón clicado en el sidebar
  onSectionSelected(section: string) {
    this.currentSection = section;
  }

}
