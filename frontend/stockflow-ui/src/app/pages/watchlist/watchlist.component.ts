import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { WatchlistService } from '../../services/watchlist.service';
import { Watchlist } from '../../models/watchlist.model';

@Component({
  selector: 'app-watchlist',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="space-y-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Watchlist</h1>
          <p class="text-gray-500 mt-1">Monitor your favourite stocks</p>
        </div>
      </div>

      @if (watchlists.length === 0) {
        <div class="card p-12 text-center">
          <svg class="w-16 h-16 text-gray-300 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
              d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"/>
          </svg>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">No stocks in watchlist</h3>
          <p class="text-gray-500 mb-4">Add stocks from the trading page to track them here</p>
          <a routerLink="/trading" class="btn-primary inline-block">Browse Stocks</a>
        </div>
      }

      @for (watchlist of watchlists; track watchlist.id) {
        <div class="card overflow-hidden">
          <div class="p-4 border-b border-gray-100 flex items-center justify-between">
            <h2 class="text-lg font-semibold text-gray-900">{{watchlist.name}}</h2>
            <span class="text-sm text-gray-500">{{watchlist.stocks.length}} stocks</span>
          </div>

          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-gray-50">
                  <th class="table-header">Stock</th>
                  <th class="table-header text-right">LTP</th>
                  <th class="table-header text-right">Change</th>
                  <th class="table-header text-right">% Change</th>
                  <th class="table-header text-right">Action</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-100">
                @for (stock of watchlist.stocks; track stock.symbol) {
                  <tr class="hover:bg-gray-50 transition-colors">
                    <td class="table-cell">
                      <a [routerLink]="['/trading', stock.symbol]" class="font-semibold text-indigo-600 hover:text-indigo-700">
                        {{stock.symbol}}
                      </a>
                      <p class="text-xs text-gray-500">{{stock.name}}</p>
                    </td>
                    <td class="table-cell text-right font-medium">₹{{stock.price.toFixed(2)}}</td>
                    <td class="table-cell text-right" [class.stock-positive]="stock.change >= 0" [class.stock-negative]="stock.change < 0">
                      {{stock.change >= 0 ? '+' : ''}}{{stock.change.toFixed(2)}}
                    </td>
                    <td class="table-cell text-right" [class.stock-positive]="stock.change >= 0" [class.stock-negative]="stock.change < 0">
                      {{stock.changePercent >= 0 ? '+' : ''}}{{stock.changePercent.toFixed(2)}}%
                    </td>
                    <td class="table-cell text-right">
                      <button (click)="removeFromWatchlist(stock.symbol)" class="text-red-500 hover:text-red-600 text-sm font-medium">
                        Remove
                      </button>
                    </td>
                  </tr>
                }
              </tbody>
            </table>
          </div>
        </div>
      }
    </div>
  `
})
export class WatchlistComponent implements OnInit {
  watchlists: Watchlist[] = [];

  constructor(private watchlistService: WatchlistService) {}

  ngOnInit(): void {
    this.watchlistService.getWatchlists().subscribe(data => {
      this.watchlists = data;
    });
  }

  removeFromWatchlist(symbol: string): void {
    this.watchlistService.removeFromWatchlist(symbol);
    this.watchlistService.getWatchlists().subscribe(data => {
      this.watchlists = data;
    });
  }
}
