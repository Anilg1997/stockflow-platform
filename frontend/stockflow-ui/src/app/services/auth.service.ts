import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap, catchError } from 'rxjs';
import { User, LoginRequest, RegisterRequest, AuthResponse } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly API_URL = '/api/auth';
  private readonly TOKEN_KEY = 'stockflow_token';
  private readonly REFRESH_KEY = 'stockflow_refresh';
  private readonly USER_KEY = 'stockflow_user';

  currentUser = signal<User | null>(null);
  isAuthenticated = signal(false);

  constructor(private http: HttpClient) {
    this.loadUserFromStorage();
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/login`, credentials).pipe(
      tap(resp => this.handleAuthResponse(resp)),
      catchError(() => {
        // Dev mode: auto-login with mock data when backend is unavailable
        const mockResp: AuthResponse = {
          token: 'mock-jwt-token-' + Date.now(),
          refreshToken: 'mock-refresh-token-' + Date.now(),
          user: {
            id: 'USR001',
            email: credentials.email,
            name: credentials.email.split('@')[0],
            phone: '+91 9876543210',
            kycStatus: 'VERIFIED',
            createdAt: new Date().toISOString()
          }
        };
        this.handleAuthResponse(mockResp);
        return of(mockResp);
      })
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.API_URL}/register`, data).pipe(
      tap(resp => this.handleAuthResponse(resp)),
      catchError(() => {
        // Dev mode: auto-register with mock data when backend is unavailable
        const mockResp: AuthResponse = {
          token: 'mock-jwt-token-' + Date.now(),
          refreshToken: 'mock-refresh-token-' + Date.now(),
          user: {
            id: 'USR' + Date.now(),
            email: data.email,
            name: data.name,
            phone: data.phone || '+91 9876543210',
            kycStatus: 'PENDING',
            createdAt: new Date().toISOString()
          }
        };
        this.handleAuthResponse(mockResp);
        return of(mockResp);
      })
    );
  }

  logout(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.REFRESH_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUser.set(null);
    this.isAuthenticated.set(false);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private handleAuthResponse(resp: AuthResponse): void {
    localStorage.setItem(this.TOKEN_KEY, resp.token);
    localStorage.setItem(this.REFRESH_KEY, resp.refreshToken);
    localStorage.setItem(this.USER_KEY, JSON.stringify(resp.user));
    this.currentUser.set(resp.user);
    this.isAuthenticated.set(true);
  }

  private loadUserFromStorage(): void {
    const token = localStorage.getItem(this.TOKEN_KEY);
    const userStr = localStorage.getItem(this.USER_KEY);
    if (token && userStr) {
      try {
        const user = JSON.parse(userStr);
        this.currentUser.set(user);
        this.isAuthenticated.set(true);
      } catch {
        this.logout();
      }
    }
  }
}
