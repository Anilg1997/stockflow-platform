import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { Portfolio, Holding, FundsSummary } from '../../models/portfolio.model';

@Component({
  selector: 'app-portfolio',
  standalone: true,
  imports: [RouterLink],
  template: `
    <div class="space-y-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Portfolio</h1>
          <p class="text-gray-500 mt-1">Track your investments and performance</p>
        </div>
      </div>

      @if (portfolio) {
        <!-- Portfolio Overview -->
        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
          <div class="stat-card">
            <p class="stat-label">Total Investment</p>
            <p class="stat-value">₹{{portfolio.totalInvestment.toLocaleString('en-IN')}}</p>
          </div>
          <div class="stat-card">
            <p class="stat-label">Current Value</p>
            <p class="stat-value">₹{{portfolio.currentValue.toLocaleString('en-IN')}}</p>
          </div>
          <div class="stat-card">
            <p class="stat-label">Total Returns</p>
            <p [class]="portfolio.totalPnl >= 0 ? 'stat-value stock-positive' : 'stat-value stock-negative'">
              {{portfolio.totalPnl >= 0 ? '+' : ''}}{{portfolio.totalPnlPercent.toFixed(2)}}%
            </p>
          </div>
          <div class="stat-card">
            <p class="stat-label">Day's P&L</p>
            <p [class]="portfolio.dayPnl >= 0 ? 'stat-value stock-positive' : 'stat-value stock-negative'">
              ₹{{portfolio.dayPnl.toLocaleString('en-IN')}}
            </p>
          </div>
        </div>
      }

      <!-- Funds Summary -->
      @if (funds) {
        <div class="card p-6">
          <h2 class="text-lg font-semibold text-gray-900 mb-4">Funds Summary</h2>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4">
            <div>
              <p class="stat-label">Total Balance</p>
              <p class="text-xl font-bold text-gray-900">₹{{funds.totalBalance.toLocaleString('en-IN')}}</p>
            </div>
            <div>
              <p class="stat-label">Available</p>
              <p class="text-xl font-bold text-emerald-600">₹{{funds.availableBalance.toLocaleString('en-IN')}}</p>
            </div>
            <div>
              <p class="stat-label">Used Margin</p>
              <p class="text-xl font-bold text-orange-600">₹{{funds.usedMargin.toLocaleString('en-IN')}}</p>
            </div>
            <div>
              <p class="stat-label">Pending Deposits</p>
              <p class="text-xl font-bold text-blue-600">₹{{funds.pendingDeposits.toLocaleString('en-IN')}}</p>
            </div>
          </div>
        </div>
      }

      <!-- Holdings Table -->
      <div class="card overflow-hidden">
        <div class="p-4 border-b border-gray-100">
          <h2 class="text-lg font-semibold text-gray-900">Holdings ({{portfolio?.holdings?.length || 0}})</h2>
        </div>
        @if (portfolio?.holdings) {
          <div class="overflow-x-auto">
            <table class="w-full">
              <thead>
                <tr class="bg-gray-50">
                  <th class="table-header">Stock</th>
                  <th class="table-header text-right">Qty</th>
                  <th class="table-header text-right">Buy Price</th>
                  <th class="table-header text-right">LTP</th>
                  <th class="table-header text-right">Invested</th>
                  <th class="table-header text-right">Current Value</th>
                  <th class="table-header text-right">P&L</th>
                  <th class="table-header text-right">Returns</th>
                </tr>
              </thead>
              <tbody class="divide-y divide-gray-100">
                @for (h of portfolio!.holdings; track h.symbol) {
                  <tr class="hover:bg-gray-50 transition-colors">
                    <td class="table-cell">
                      <a [routerLink]="['/trading', h.symbol]" class="font-semibold text-indigo-600 hover:text-indigo-700">{{h.symbol}}</a>
                      <p class="text-xs text-gray-500">{{h.companyName}}</p>
                    </td>
                    <td class="table-cell text-right font-medium">{{h.quantity}}</td>
                    <td class="table-cell text-right">₹{{h.buyPrice.toFixed(2)}}</td>
                    <td class="table-cell text-right font-medium">₹{{h.currentPrice.toFixed(2)}}</td>
                    <td class="table-cell text-right">₹{{h.totalInvestment.toLocaleString('en-IN')}}</td>
                    <td class="table-cell text-right font-medium">₹{{h.currentValue.toLocaleString('en-IN')}}</td>
                    <td class="table-cell text-right" [class.stock-positive]="h.pnl >= 0" [class.stock-negative]="h.pnl < 0">
                      {{h.pnl >= 0 ? '+' : ''}}₹{{h.pnl.toLocaleString('en-IN')}}
                    </td>
                    <td class="table-cell text-right" [class.stock-positive]="h.pnl >= 0" [class.stock-negative]="h.pnl < 0">
                      {{h.pnlPercent >= 0 ? '+' : ''}}{{h.pnlPercent.toFixed(2)}}%
                    </td>
                  </tr>
                }
              </tbody>
            </table>
          </div>
        }
      </div>
    </div>
  `
})
export class PortfolioComponent implements OnInit {
  portfolio: Portfolio | null = null;
  funds: FundsSummary | null = null;

  constructor(private portfolioService: PortfolioService) {}

  ngOnInit(): void {
    this.portfolioService.getPortfolio().subscribe(data => this.portfolio = data);
    this.portfolioService.getFundsSummary().subscribe(data => this.funds = data);
  }
}
