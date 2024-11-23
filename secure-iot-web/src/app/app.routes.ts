import { Routes } from '@angular/router';
import { authGuard } from './guard/Auth.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./components/home/home.component').then((m) => m.HomeComponent),
  },
  {
    path: 'panel',
    loadComponent: () =>
      import('./components/sections/panel/panel.component').then((m) => m.PanelComponent),
    canActivate: [authGuard],
    children: [
      {
        path: '',
        redirectTo: 'doors', // Redirige a doors si no se especifica ruta
        pathMatch: 'full',
      },
      {
        path: 'doors',
        loadComponent: () =>
          import('./components/sections/sidebar/content/doors/doors.component').then((m) => m.DoorsComponent),
        canActivate: [authGuard],
      },
      {
        path: 'lights',
        loadComponent: () =>
          import('./components/sections/sidebar/content/lights/lights.component').then((m) => m.LightsComponent),
        canActivate: [authGuard],
      },
      {
        path: 'control',
        loadComponent: () =>
          import('./components/sections/sidebar/content/control/control.component').then((m) => m.ControlComponent),
        canActivate: [authGuard],
      },
      {
        path: 'notify',
        loadComponent: () =>
          import('./components/sections/sidebar/content/notify/notify.component').then((m) => m.NotifyComponent),
        canActivate: [authGuard],
      },
    ],
  },

 


  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];
