import { Routes } from '@angular/router';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./pages/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'trading/:symbol',
    loadComponent: () => import('./pages/trading/trading.component').then(m => m.TradingComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'trading',
    loadComponent: () => import('./pages/trading/trading.component').then(m => m.TradingComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'portfolio',
    loadComponent: () => import('./pages/portfolio/portfolio.component').then(m => m.PortfolioComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'watchlist',
    loadComponent: () => import('./pages/watchlist/watchlist.component').then(m => m.WatchlistComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'mutual-funds',
    loadComponent: () => import('./pages/mutual-funds/mutual-funds.component').then(m => m.MutualFundsComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'ipos',
    loadComponent: () => import('./pages/ipos/ipos.component').then(m => m.IposComponent),
    canActivate: [AuthGuard]
  },
  {
    path: 'search',
    loadComponent: () => import('./pages/search/search.component').then(m => m.SearchComponent),
    canActivate: [AuthGuard]
  },
  { path: '**', redirectTo: '/dashboard' }
];
