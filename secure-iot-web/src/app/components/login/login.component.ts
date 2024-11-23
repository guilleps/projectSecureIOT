import { Component } from '@angular/core';
import { initializeApp } from 'firebase/app';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { getAuth, signInWithEmailAndPassword } from 'firebase/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styles: ``,
})
export class LoginComponent {
  loginForm = new FormGroup({
    email: new FormControl<string>('', [Validators.required, Validators.email]),
    password: new FormControl<string>('', [Validators.required]),
  });

  isLoading = false;
  errorMessage: string | null = null;
  showPassword: boolean = false;

  private app = initializeApp(environment.firebaseConfig);
  private auth = getAuth(this.app);

  constructor(private fb: FormBuilder, private router: Router) {}

  authenticated(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.get('email')?.value;
      const password = this.loginForm.get('password')?.value;

      if (email && password) {
        this.isLoading = true;
        this.errorMessage = null;

        signInWithEmailAndPassword(this.auth, email, password)
          .then((userCredential) => {
            const user = userCredential.user;
            user.getIdToken().then((token) => {
              sessionStorage.setItem('userToken', token);
              this.router.navigate(['/panel']);
            });
          })
          .catch((error) => {
            this.errorMessage =
              'Credenciales incorrectas. Por favor, inténtelo de nuevo.';
          })
          .finally(() => {
            this.isLoading = false;
          });
      }
    }
  }

  toggleShowPassword() {
    this.showPassword = !this.showPassword;
  }
}
