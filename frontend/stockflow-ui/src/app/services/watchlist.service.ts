import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Watchlist, WatchlistStock } from '../models/watchlist.model';
import { MarketDataService } from './market-data.service';

@Injectable({ providedIn: 'root' })
export class WatchlistService {
  private readonly API_URL = '/api/watchlist';
  private watchlists = signal<Watchlist[]>([]);

  constructor(private http: HttpClient, private marketData: MarketDataService) {
    this.loadDefaultWatchlist();
  }

  getWatchlists(): Observable<Watchlist[]> {
    return of(this.watchlists());
  }

  addToWatchlist(symbol: string, watchlistName: string = 'Default'): void {
    const stock = this.marketData.getStockDetail(symbol);
    if (!stock) return;

    const wlIndex = this.watchlists().findIndex(w => w.name === watchlistName);
    if (wlIndex === -1) {
      this.watchlists.update(list => [...list, {
        id: Date.now().toString(),
        name: watchlistName,
        stocks: [this.toWatchlistStock(stock)],
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString()
      }]);
    } else {
      this.watchlists.update(list => {
        const updated = [...list];
        const exists = updated[wlIndex].stocks.some(s => s.symbol === symbol);
        if (!exists) {
          updated[wlIndex] = {
            ...updated[wlIndex],
            stocks: [...updated[wlIndex].stocks, this.toWatchlistStock(stock)],
            updatedAt: new Date().toISOString()
          };
        }
        return updated;
      });
    }
  }

  removeFromWatchlist(symbol: string, watchlistName: string = 'Default'): void {
    this.watchlists.update(list =>
      list.map(w => w.name === watchlistName ? {
        ...w,
        stocks: w.stocks.filter(s => s.symbol !== symbol),
        updatedAt: new Date().toISOString()
      } : w).filter(w => w.stocks.length > 0 || w.name !== watchlistName)
    );
  }

  isInWatchlist(symbol: string): boolean {
    return this.watchlists().some(w => w.stocks.some(s => s.symbol === symbol));
  }

  private toWatchlistStock(stock: any): WatchlistStock {
    return {
      symbol: stock.symbol,
      name: stock.name,
      price: stock.currentPrice,
      change: stock.change,
      changePercent: stock.changePercent,
      addedAt: new Date().toISOString()
    };
  }

  private loadDefaultWatchlist(): void {
    const symbols = ['RELIANCE', 'TCS', 'HDFCBANK', 'INFY', 'ICICIBANK'];
    symbols.forEach(s => this.addToWatchlist(s));
  }
}
