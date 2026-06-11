import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-50 via-white to-purple-50 px-4">
      <div class="w-full max-w-md">
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-indigo-600 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-lg shadow-indigo-200">
            <span class="text-white font-bold text-2xl">SF</span>
          </div>
          <h1 class="text-2xl font-bold text-gray-900">Create your account</h1>
          <p class="text-gray-500 mt-1">Start your investing journey today</p>
        </div>

        <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
          @if (error) {
            <div class="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-sm text-red-600">{{error}}</div>
          }

          <form (ngSubmit)="onSubmit()" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
              <input type="text" [(ngModel)]="name" name="name" required
                class="input-field" placeholder="John Doe">
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input type="email" [(ngModel)]="email" name="email" required
                class="input-field" placeholder="you@example.com">
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Phone (optional)</label>
              <input type="tel" [(ngModel)]="phone" name="phone"
                class="input-field" placeholder="+91 98765 43210">
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Password</label>
              <input type="password" [(ngModel)]="password" name="password" required
                class="input-field" placeholder="Create a strong password">
            </div>

            <button type="submit" [disabled]="loading"
              class="btn-primary w-full flex items-center justify-center gap-2">
              @if (loading) {
                <svg class="animate-spin h-4 w-4" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" fill="none"/>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
                </svg>
              }
              {{loading ? 'Creating account...' : 'Create Account'}}
            </button>
          </form>

          <div class="mt-6 text-center">
            <p class="text-sm text-gray-500">
              Already have an account?
              <a routerLink="/login" class="text-indigo-600 hover:text-indigo-700 font-medium">Sign in</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  `
})
export class RegisterComponent {
  name = '';
  email = '';
  phone = '';
  password = '';
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    this.authService.register({
      email: this.email,
      password: this.password,
      name: this.name,
      phone: this.phone
    }).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.loading = false;
        this.error = err?.error?.message || 'Registration failed. Please try again.';
      }
    });
  }
}
