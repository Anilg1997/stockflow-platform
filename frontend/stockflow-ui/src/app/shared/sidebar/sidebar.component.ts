import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <aside class="fixed left-0 top-16 bottom-0 w-64 bg-white border-r border-gray-200 p-4 overflow-y-auto hidden lg:block">
      <nav class="space-y-1">
        <a routerLink="/dashboard" routerLinkActive="sidebar-link-active" [routerLinkActiveOptions]="{exact:true}"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"/>
          </svg>
          Dashboard
        </a>

        <a routerLink="/trading" routerLinkActive="sidebar-link-active"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"/>
          </svg>
          Trading
        </a>

        <a routerLink="/portfolio" routerLinkActive="sidebar-link-active"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"/>
          </svg>
          Portfolio
        </a>

        <a routerLink="/watchlist" routerLinkActive="sidebar-link-active"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"/>
          </svg>
          Watchlist
        </a>

        <div class="pt-4 pb-2">
          <p class="px-3 text-xs font-semibold text-gray-400 uppercase tracking-wider">Investments</p>
        </div>

        <a routerLink="/mutual-funds" routerLinkActive="sidebar-link-active"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
          Mutual Funds
        </a>

        <a routerLink="/ipos" routerLinkActive="sidebar-link-active"
           class="sidebar-link-inactive flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                  transition-all duration-200 hover:bg-gray-100 hover:text-gray-900">
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6"/>
          </svg>
          IPOs
        </a>
      </nav>

      <div class="absolute bottom-0 left-0 right-0 p-4 border-t border-gray-200 bg-white">
        <div class="flex items-center justify-between">
          <div>
            <p class="text-xs text-gray-500">Available Balance</p>
            <p class="text-sm font-bold text-gray-900">₹1,80,000</p>
          </div>
          <button class="btn-primary text-xs px-3 py-1.5">Add Funds</button>
        </div>
      </div>
    </aside>
  `
})
export class SidebarComponent {}
