import { inject } from '@angular/core';
import { Router, type CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  
  // Simulamos una función de verificación de autenticación
  const isAuthenticated = !!sessionStorage.getItem('userToken'); // Verifica si existe un token en el localStorage
  
  if (!isAuthenticated) {
    router.navigate(['']); // Redirige a la página de inicio si no está autenticado
    return false;
  }
  
  return true; // Permite el acceso si está autenticado
};