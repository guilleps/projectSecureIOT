import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router, RouterLink, RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styles: ``
})
export class SidebarComponent {
  @Input() currentSection!: string;
  @Output() sectionSelected = new EventEmitter<string>();

  constructor(private router: Router) {}

  // Método para emitir el nombre de la sección seleccionada
  selectSection(section: string) {
    this.sectionSelected.emit(section);
  }

  inauthenticated(): void {
    sessionStorage.removeItem('userToken');
    this.router.navigate(['']);
  }

  isActive(route: string): boolean {
    return this.router.url === route;
  }

  isActiveCategoria(): boolean {
    const url = this.router.url;
    return url === '/panel';
  }
  
   // Métodos específicos para cada ruta
   isActiveLights(): boolean {
    return this.router.url === '/panel/lights';
  }

  isActiveDoors(): boolean {
    return this.router.url === '/panel/doors';
  }

  isActiveControl(): boolean {
    return this.router.url === '/panel/control';
  }

  isActiveNotify(): boolean {
    return this.router.url === '/panel/notify';
  }

  
}
