import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-indigo-50 via-white to-purple-50 px-4">
      <div class="w-full max-w-md">
        <div class="text-center mb-8">
          <div class="w-16 h-16 bg-indigo-600 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-lg shadow-indigo-200">
            <span class="text-white font-bold text-2xl">SF</span>
          </div>
          <h1 class="text-2xl font-bold text-gray-900">Welcome back to StockFlow</h1>
          <p class="text-gray-500 mt-1">Sign in to your trading account</p>
        </div>

        <div class="bg-white rounded-2xl shadow-xl border border-gray-100 p-8">
          @if (error) {
            <div class="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-sm text-red-600">
              {{error}}
            </div>
          }

          <form (ngSubmit)="onSubmit()" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input type="email" [(ngModel)]="email" name="email" required
                class="input-field" placeholder="you@example.com">
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Password</label>
              <input type="password" [(ngModel)]="password" name="password" required
                class="input-field" placeholder="Enter your password">
            </div>

            <div class="flex items-center justify-between">
              <label class="flex items-center gap-2">
                <input type="checkbox" class="rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
                <span class="text-sm text-gray-600">Remember me</span>
              </label>
              <a href="#" class="text-sm text-indigo-600 hover:text-indigo-700 font-medium">Forgot password?</a>
            </div>

            <button type="submit" [disabled]="loading"
              class="btn-primary w-full flex items-center justify-center gap-2">
              @if (loading) {
                <svg class="animate-spin h-4 w-4" viewBox="0 0 24 24">
                  <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" fill="none"/>
                  <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4z"/>
                </svg>
              }
              {{loading ? 'Signing in...' : 'Sign In'}}
            </button>
          </form>

          <div class="mt-6 text-center">
            <p class="text-sm text-gray-500">
              Don't have an account?
              <a routerLink="/register" class="text-indigo-600 hover:text-indigo-700 font-medium">Create one</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  `
})
export class LoginComponent {
  email = '';
  password = '';
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    this.authService.login({ email: this.email, password: this.password })
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/dashboard']);
        },
        error: (err) => {
          this.loading = false;
          this.error = err?.error?.message || 'Login failed. Please try again.';
        }
      });
  }
}
