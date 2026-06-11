import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MarketDataService } from '../../services/market-data.service';
import { Stock } from '../../models/stock.model';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="space-y-6">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">Search Results</h1>
        @if (query) {
          <p class="text-gray-500 mt-1">Showing results for "{{query}}"</p>
        }
      </div>

      @if (results.length === 0) {
        <div class="card p-12 text-center">
          <svg class="w-16 h-16 text-gray-300 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5"
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
          </svg>
          <h3 class="text-lg font-semibold text-gray-900 mb-2">No results found</h3>
          <p class="text-gray-500">Try searching for a stock symbol or company name</p>
        </div>
      }

      @if (results.length > 0) {
        <div class="card overflow-hidden">
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-gray-50">
                  <th class="table-header">Symbol</th>
                  <th class="table-header">Company Name</th>
                  <th class="table-header">Sector</th>
                  <th class="table-header text-right">LTP</th>
                  <th class="table-header text-right">% Change</th>
                  <th class="table-header text-right">Volume</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-100">
                @for (stock of results; track stock.symbol) {
                  <tr (click)="selectStock(stock.symbol)" class="hover:bg-gray-50 cursor-pointer transition-colors">
                    <td class="table-cell font-semibold text-indigo-600">{{stock.symbol}}</td>
                    <td class="table-cell">{{stock.name}}</td>
                    <td class="table-cell"><span class="badge-gray">{{stock.sector}}</span></td>
                    <td class="table-cell text-right font-medium">₹{{stock.currentPrice.toFixed(2)}}</td>
                    <td class="table-cell text-right" [class.stock-positive]="stock.changePercent >= 0" [class.stock-negative]="stock.changePercent < 0">
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
    </div>
  `
})
export class SearchComponent implements OnInit {
  query = '';
  results: Stock[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private marketDataService: MarketDataService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.query = params['q'] || '';
      if (this.query) {
        this.results = this.marketDataService.searchStocks(this.query);
      }
    });
  }

  selectStock(symbol: string): void {
    this.router.navigate(['/trading', symbol]);
  }
}
