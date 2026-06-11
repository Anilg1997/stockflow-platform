import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MarketDataService } from '../../services/market-data.service';
import { PortfolioService } from '../../services/portfolio.service';
import { Stock, MarketOverview } from '../../models/stock.model';
import { Portfolio } from '../../models/portfolio.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="space-y-6">
      <!-- Page Header -->
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Dashboard</h1>
          <p class="text-gray-500 mt-1">Welcome back! Here's your market overview.</p>
        </div>
        <div class="flex gap-3">
          <button class="btn-secondary text-sm">Add Funds</button>
          <button class="btn-primary text-sm">Trade Now</button>
        </div>
      </div>

      <!-- Market Indices -->
      @if (marketOverview) {
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
          @for (index of marketOverview.indices; track index.name) {
            <div class="stat-card">
              <p class="stat-label">{{index.name}}</p>
              <p class="stat-value">{{index.value.toLocaleString('en-IN')}}</p>
              <div class="flex items-center gap-1 mt-1">
                <span [class]="index.change >= 0 ? 'stock-positive' : 'stock-negative'"
                      class="text-sm font-semibold">
                  {{index.change >= 0 ? '+' : ''}}{{index.change.toFixed(2)}}
                  ({{index.changePercent >= 0 ? '+' : ''}}{{index.changePercent.toFixed(2)}}%)
                </span>
                @if (index.change >= 0) {
                  <svg class="w-4 h-4 text-emerald-600" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M5.293 9.707a1 1 0 010-1.414l4-4a1 1 0 011.414 0l4 4a1 1 0 01-1.414 1.414L11 7.414V15a1 1 0 11-2 0V7.414L6.707 9.707a1 1 0 01-1.414 0z" clip-rule="evenodd"/>
                  </svg>
                } @else {
                  <svg class="w-4 h-4 text-red-600" fill="currentColor" viewBox="0 0 20 20">
                    <path fill-rule="evenodd" d="M14.707 10.293a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 111.414-1.414L9 12.586V5a1 1 0 012 0v7.586l2.293-2.293a1 1 0 011.414 0z" clip-rule="evenodd"/>
                  </svg>
                }
              </div>
            </div>
          }
        </div>
      }

      <!-- Portfolio Summary -->
      @if (portfolio) {
        <div class="card p-6">
          <div class="flex items-center justify-between mb-4">
            <h2 class="text-lg font-semibold text-gray-900">Portfolio Summary</h2>
            <a routerLink="/portfolio" class="text-sm text-indigo-600 hover:text-indigo-700 font-medium">View Details →</a>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div>
              <p class="stat-label">Total Investment</p>
              <p class="text-lg font-bold text-gray-900">₹{{portfolio.totalInvestment.toLocaleString('en-IN')}}</p>
            </div>
            <div>
              <p class="stat-label">Current Value</p>
              <p class="text-lg font-bold text-gray-900">₹{{portfolio.currentValue.toLocaleString('en-IN')}}</p>
            </div>
            <div>
              <p class="stat-label">Total P&L</p>
              <p [class]="portfolio.totalPnl >= 0 ? 'stock-positive' : 'stock-negative'" class="text-lg font-bold">
                {{portfolio.totalPnl >= 0 ? '+' : ''}}₹{{portfolio.totalPnl.toLocaleString('en-IN')}}
                ({{portfolio.totalPnlPercent >= 0 ? '+' : ''}}{{portfolio.totalPnlPercent.toFixed(2)}}%)
              </p>
            </div>
            <div>
              <p class="stat-label">Day P&L</p>
              <p [class]="portfolio.dayPnl >= 0 ? 'stock-positive' : 'stock-negative'" class="text-lg font-bold">
                {{portfolio.dayPnl >= 0 ? '+' : ''}}₹{{portfolio.dayPnl.toLocaleString('en-IN')}}
              </p>
            </div>
          </div>
        </div>
      }

      <!-- Top Gainers / Losers -->
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
        @if (marketOverview) {
          <div class="card p-6">
            <h2 class="text-lg font-semibold text-gray-900 mb-4">Top Gainers</h2>
            <div class="space-y-3">
              @for (stock of marketOverview.topGainers; track stock.symbol) {
                <a [routerLink]="['/trading', stock.symbol]" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors">
                  <div>
                    <p class="font-semibold text-gray-900">{{stock.symbol}}</p>
                    <p class="text-sm text-gray-500">{{stock.name}}</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">₹{{stock.currentPrice.toFixed(2)}}</p>
                    <p class="text-sm font-medium stock-positive">
                      +{{stock.changePercent.toFixed(2)}}%
                    </p>
                  </div>
                </a>
              }
            </div>
          </div>

          <div class="card p-6">
            <h2 class="text-lg font-semibold text-gray-900 mb-4">Top Losers</h2>
            <div class="space-y-3">
              @for (stock of marketOverview.topLosers; track stock.symbol) {
                <a [routerLink]="['/trading', stock.symbol]" class="flex items-center justify-between p-3 rounded-lg hover:bg-gray-50 transition-colors">
                  <div>
                    <p class="font-semibold text-gray-900">{{stock.symbol}}</p>
                    <p class="text-sm text-gray-500">{{stock.name}}</p>
                  </div>
                  <div class="text-right">
                    <p class="font-semibold text-gray-900">₹{{stock.currentPrice.toFixed(2)}}</p>
                    <p class="text-sm font-medium stock-negative">
                      {{stock.changePercent.toFixed(2)}}%
                    </p>
                  </div>
                </a>
              }
            </div>
          </div>
        }
      </div>
    </div>
  `
})
export class DashboardComponent implements OnInit {
  marketOverview: MarketOverview | null = null;
  portfolio: Portfolio | null = null;

  constructor(
    private marketDataService: MarketDataService,
    private portfolioService: PortfolioService
  ) {}

  ngOnInit(): void {
    this.marketDataService.getMarketOverview().subscribe(data => {
      this.marketOverview = data;
    });

    this.portfolioService.getPortfolio().subscribe(data => {
      this.portfolio = data;
    });
  }
}
