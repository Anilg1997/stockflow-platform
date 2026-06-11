import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, NgClass],
  template: `
    <header class="fixed top-0 left-0 right-0 z-50 bg-white border-b border-gray-200">
      <div class="px-4 h-16 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <button (click)="showMobileMenu = !showMobileMenu" class="lg:hidden p-2 rounded-lg hover:bg-gray-100">
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
            </svg>
          </button>
          <a routerLink="/dashboard" class="flex items-center gap-2">
            <div class="w-8 h-8 bg-indigo-600 rounded-lg flex items-center justify-center">
              <span class="text-white font-bold text-sm">SF</span>
            </div>
            <span class="text-xl font-bold text-gray-900 hidden sm:block">StockFlow</span>
          </a>
        </div>

        <div class="flex items-center gap-4">
          <div class="hidden md:flex items-center bg-gray-100 rounded-lg px-3 py-1.5">
            <svg class="w-4 h-4 text-gray-400 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
            <input type="text" placeholder="Search stocks, MF, IPO..."
              class="bg-transparent outline-none text-sm w-48 text-gray-600 placeholder-gray-400"
              (keyup.enter)="search($event)">
          </div>

          <div class="flex items-center gap-3">
            <button class="relative p-2 rounded-lg hover:bg-gray-100">
              <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"/>
              </svg>
              <span class="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
            </button>

            @if (authService.currentUser()) {
              <div class="relative">
                <div class="flex items-center gap-2 cursor-pointer" (click)="toggleUserMenu()">
                  <div class="w-8 h-8 bg-indigo-100 rounded-full flex items-center justify-center">
                    <span class="text-indigo-600 font-semibold text-sm">
                      {{authService.currentUser()?.name?.charAt(0) || 'U'}}
                    </span>
                  </div>
                  <span class="text-sm font-medium text-gray-700 hidden sm:block">
                    {{authService.currentUser()?.name?.split(' ')?.[0] || 'User'}}
                  </span>
                </div>
                @if (showUserMenu) {
                  <div class="absolute right-0 mt-2 w-48 bg-white rounded-xl shadow-lg border border-gray-100 py-1 z-50" (click)="showUserMenu = false">
                    <div class="px-4 py-2 border-b border-gray-100">
                      <p class="text-sm font-semibold text-gray-900">{{authService.currentUser()?.name}}</p>
                      <p class="text-xs text-gray-500">{{authService.currentUser()?.email}}</p>
                    </div>
                    <button class="w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 flex items-center gap-2">
                      <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/></svg>
                      Profile
                    </button>
                    <button (click)="logout()" class="w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50 flex items-center gap-2">
                      <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/></svg>
                      Sign Out
                    </button>
                  </div>
                }
              </div>
            }
          </div>
        </div>
      </div>
    </header>
  `
})
export class HeaderComponent {
  showMobileMenu = false;
  showUserMenu = false;

  constructor(public authService: AuthService, private router: Router) {}

  search(event: any): void {
    const query = event.target.value.trim();
    if (query) {
      this.router.navigate(['/search'], { queryParams: { q: query } });
    }
  }

  toggleUserMenu(): void {
    this.showUserMenu = !this.showUserMenu;
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
