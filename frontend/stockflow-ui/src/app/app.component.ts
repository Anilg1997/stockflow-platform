import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, SidebarComponent],
  template: `
    <div class="min-h-screen bg-gray-50">
      @if (authService.isAuthenticated()) {
        <app-header></app-header>
        <div class="flex">
          <app-sidebar></app-sidebar>
          <main class="flex-1 p-6 lg:ml-64 mt-16">
            <router-outlet></router-outlet>
          </main>
        </div>
      } @else {
        <main>
          <router-outlet></router-outlet>
        </main>
      }
    </div>
  `
})
export class AppComponent {
  constructor(public authService: AuthService) {}
}
