import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MarketDataService } from '../../services/market-data.service';
import { OrderService } from '../../services/order.service';
import { WatchlistService } from '../../services/watchlist.service';
import { Stock, PriceHistory } from '../../models/stock.model';
import { Order } from '../../models/order.model';

@Component({
  selector: 'app-trading',
  standalone: true,
  imports: [FormsModule, RouterLink],
  template: `
    <div class="space-y-6">
      <!-- Header -->
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Trading</h1>
          <p class="text-gray-500 mt-1">Search and trade stocks</p>
        </div>
      </div>

      <!-- Stock Search -->
      <div class="card p-4">
        <div class="flex gap-3 items-center">
          <div class="flex-1 relative">
            <svg class="absolute left-3 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
            </svg>
            <input type="text" [(ngModel)]="searchQuery" (input)="onSearch()"
              placeholder="Search by symbol or company name..."
              class="input-field pl-10">
          </div>
          <select [(ngModel)]="selectedSector" (change)="onSearch()" class="input-field w-48">
            <option value="">All Sectors</option>
            <option value="IT">IT</option>
            <option value="Banking">Banking</option>
            <option value="Oil & Gas">Oil & Gas</option>
            <option value="FMCG">FMCG</option>
            <option value="Telecom">Telecom</option>
            <option value="Automobile">Automobile</option>
          </select>
        </div>
      </div>

      @if (selectedStock) {
        <!-- Selected Stock Details -->
        <div class="card p-6">
          <div class="flex items-start justify-between">
            <div>
              <div class="flex items-center gap-3">
                <h2 class="text-xl font-bold text-gray-900">{{selectedStock.symbol}}</h2>
                <button (click)="toggleWatchlist()"
                  class="p-1.5 rounded-lg hover:bg-gray-100 transition-colors">
                  <svg class="w-5 h-5" [class.text-yellow-400]="isInWatchlist" [class.text-gray-300]="!isInWatchlist"
                    fill="currentColor" viewBox="0 0 24 24">
                    <path d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"/>
                  </svg>
                </button>
              </div>
              <p class="text-gray-500">{{selectedStock.name}}</p>
              <span class="badge-blue mt-2 inline-block">{{selectedStock.sector}}</span>
            </div>
            <div class="text-right">
              <p class="text-3xl font-bold text-gray-900">₹{{selectedStock.currentPrice.toFixed(2)}}</p>
              <div class="flex items-center gap-1 justify-end mt-1">
                <span [class]="selectedStock.change >= 0 ? 'stock-positive' : 'stock-negative'" class="text-lg font-semibold">
                  {{selectedStock.change >= 0 ? '+' : ''}}{{selectedStock.change.toFixed(2)}}
                  ({{selectedStock.changePercent >= 0 ? '+' : ''}}{{selectedStock.changePercent.toFixed(2)}}%)
                </span>
              </div>
            </div>
          </div>

          <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mt-6 pt-6 border-t border-gray-100">
            <div>
              <p class="stat-label">Open</p>
              <p class="font-semibold">₹{{selectedStock.open.toFixed(2)}}</p>
            </div>
            <div>
              <p class="stat-label">High</p>
              <p class="font-semibold">₹{{selectedStock.high.toFixed(2)}}</p>
            </div>
            <div>
              <p class="stat-label">Low</p>
              <p class="font-semibold">₹{{selectedStock.low.toFixed(2)}}</p>
            </div>
            <div>
              <p class="stat-label">Volume</p>
              <p class="font-semibold">{{(selectedStock.volume / 100000).toFixed(1)}}L</p>
            </div>
            <div>
              <p class="stat-label">Market Cap</p>
              <p class="font-semibold">₹{{(selectedStock.marketCap / 100000000).toFixed(0)}}Cr</p>
            </div>
            <div>
              <p class="stat-label">P/E Ratio</p>
              <p class="font-semibold">{{selectedStock.pe}}</p>
            </div>
            <div>
              <p class="stat-label">Dividend</p>
              <p class="font-semibold">{{selectedStock.dividend}}%</p>
            </div>
            <div>
              <p class="stat-label">Prev Close</p>
              <p class="font-semibold">₹{{selectedStock.previousClose.toFixed(2)}}</p>
            </div>
          </div>
        </div>

        <!-- Buy/Sell Card -->
        <div class="card p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Place Order</h3>
          <div class="flex gap-3 mb-4">
            <button (click)="orderSide = 'BUY'"
              class="flex-1 py-2.5 rounded-lg font-medium transition-all duration-200"
              [class]="orderSide === 'BUY' ? 'btn-success' : 'bg-gray-100 text-gray-600 hover:bg-gray-200'">
              Buy
            </button>
            <button (click)="orderSide = 'SELL'"
              class="flex-1 py-2.5 rounded-lg font-medium transition-all duration-200"
              [class]="orderSide === 'SELL' ? 'btn-danger' : 'bg-gray-100 text-gray-600 hover:bg-gray-200'">
              Sell
            </button>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Order Type</label>
              <select [(ngModel)]="orderType" class="input-field">
                <option value="MARKET">Market</option>
                <option value="LIMIT">Limit</option>
                <option value="STOP_LOSS">Stop Loss</option>
              </select>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Quantity</label>
              <input type="number" [(ngModel)]="quantity" class="input-field" placeholder="0" min="1">
            </div>
            @if (orderType !== 'MARKET') {
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Price (₹)</label>
                <input type="number" [(ngModel)]="price" class="input-field" [placeholder]="selectedStock.currentPrice.toFixed(2)">
              </div>
            }
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-1">Validity</label>
              <select [(ngModel)]="validity" class="input-field">
                <option value="DAY">Day</option>
                <option value="IOC">IOC</option>
              </select>
            </div>
          </div>

          <div class="mt-4 p-3 bg-gray-50 rounded-lg">
            <div class="flex justify-between text-sm">
              <span class="text-gray-600">Total Estimate</span>
              <span class="font-semibold text-gray-900">
                ₹{{(quantity * (orderType === 'MARKET' ? selectedStock.currentPrice : (price || selectedStock.currentPrice))).toFixed(2)}}
              </span>
            </div>
            <div class="flex justify-between text-sm mt-1">
              <span class="text-gray-600">Est. Brokerage</span>
              <span class="text-gray-600">₹{{(quantity * (orderType === 'MARKET' ? selectedStock.currentPrice : (price || selectedStock.currentPrice)) * 0.0005).toFixed(2)}}</span>
            </div>
          </div>

          <button (click)="placeOrder()" [disabled]="!quantity || quantity < 1"
            class="w-full mt-4 py-3 rounded-xl font-semibold text-white transition-all duration-200"
            [class]="orderSide === 'BUY' ? 'bg-emerald-500 hover:bg-emerald-600' : 'bg-red-500 hover:bg-red-600'"
            [class.opacity-50]="!quantity || quantity < 1">
            {{orderSide === 'BUY' ? 'Buy' : 'Sell'}} {{selectedStock.symbol}}
          </button>
        </div>
      } @else {
        <!-- Stock List -->
        <div class="card overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-gray-50">
                  <th class="table-header">Symbol</th>
                  <th class="table-header">Company Name</th>
                  <th class="table-header">Sector</th>
                  <th class="table-header text-right">LTP</th>
                  <th class="table-header text-right">Change</th>
                  <th class="table-header text-right">% Change</th>
                  <th class="table-header text-right">Volume</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-100">
                @for (stock of filteredStocks; track stock.symbol) {
                  <tr (click)="selectStock(stock.symbol)" class="hover:bg-gray-50 cursor-pointer transition-colors">
                    <td class="table-cell font-semibold">{{stock.symbol}}</td>
                    <td class="table-cell">{{stock.name}}</td>
                    <td class="table-cell"><span class="badge-gray">{{stock.sector}}</span></td>
                    <td class="table-cell text-right font-medium">₹{{stock.currentPrice.toFixed(2)}}</td>
                    <td class="table-cell text-right" [class.stock-positive]="stock.change >= 0" [class.stock-negative]="stock.change < 0">
                      {{stock.change >= 0 ? '+' : ''}}{{stock.change.toFixed(2)}}
                    </td>
                    <td class="table-cell text-right" [class.stock-positive]="stock.change >= 0" [class.stock-negative]="stock.change < 0">
                      {{stock.changePercent >= 0 ? '+' : ''}}{{stock.changePercent.toFixed(2)}}%
                    </td>
                    <td class="table-cell text-right">{{(stock.volume / 100000).toFixed(1)}}L</td>
                  </tr>
                }
              </tbody>
            </table>
          </div>
        </div>
      }

      <!-- Recent Orders -->
      @if (recentOrders.length > 0) {
        <div class="card p-6">
          <h3 class="text-lg font-semibold text-gray-900 mb-4">Recent Orders</h3>
          <div class="space-y-3">
            @for (order of recentOrders; track order.id) {
              <div class="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                <div class="flex items-center gap-3">
                  <span class="badge" [class.badge-green]="order.side === 'BUY'" [class.badge-red]="order.side === 'SELL'">
                    {{order.side}}
                  </span>
                  <div>
                    <p class="font-semibold text-gray-900">{{order.symbol}}</p>
                    <p class="text-sm text-gray-500">{{order.quantity}} shares &#64; ₹{{order.price}}</p>
                  </div>
                </div>
                <div class="text-right">
                  <span class="badge" [class]="order.status === 'EXECUTED' ? 'badge-green' : order.status === 'PENDING' ? 'badge-blue' : 'badge-gray'">
                    {{order.status}}
                  </span>
                </div>
              </div>
            }
          </div>
        </div>
      }
    </div>
  `
})
export class TradingComponent implements OnInit {
  selectedStock: Stock | null = null;
  filteredStocks: Stock[] = [];
  allStocks: Stock[] = [];
  searchQuery = '';
  selectedSector = '';
  orderSide: 'BUY' | 'SELL' = 'BUY';
  orderType = 'MARKET';
  quantity = 0;
  price = 0;
  validity = 'DAY';
  recentOrders: Order[] = [];
  isInWatchlist = false;
  priceHistory: PriceHistory[] = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private marketDataService: MarketDataService,
    private orderService: OrderService,
    private watchlistService: WatchlistService
  ) {}

  ngOnInit(): void {
    this.allStocks = this.marketDataService.getAllStocks();
    this.filteredStocks = this.allStocks;

    const symbol = this.route.snapshot.paramMap.get('symbol');
    if (symbol) {
      this.selectStock(symbol);
    }

    this.orderService.getOrders().subscribe(orders => {
      this.recentOrders = orders.slice(0, 5);
    });
  }

  selectStock(symbol: string): void {
    const stock = this.marketDataService.getStockDetail(symbol);
    if (stock) {
      this.selectedStock = stock;
      this.price = stock.currentPrice;
      this.isInWatchlist = this.watchlistService.isInWatchlist(symbol);
      this.priceHistory = this.marketDataService.getPriceHistory(symbol, '1M');
    }
  }

  onSearch(): void {
    let stocks = this.searchQuery
      ? this.marketDataService.searchStocks(this.searchQuery)
      : this.allStocks;

    if (this.selectedSector) {
      stocks = stocks.filter(s => s.sector === this.selectedSector);
    }

    this.filteredStocks = stocks;
  }

  toggleWatchlist(): void {
    if (!this.selectedStock) return;
    if (this.isInWatchlist) {
      this.watchlistService.removeFromWatchlist(this.selectedStock.symbol);
    } else {
      this.watchlistService.addToWatchlist(this.selectedStock.symbol);
    }
    this.isInWatchlist = !this.isInWatchlist;
  }

  placeOrder(): void {
    if (!this.selectedStock || !this.quantity) return;
    // Placeholder - would call actual API
    alert(`${this.orderSide} order placed for ${this.quantity} shares of ${this.selectedStock.symbol}`);
    this.quantity = 0;
  }
}
