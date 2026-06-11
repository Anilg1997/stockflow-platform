import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MutualFund } from '../../models/mutual-fund.model';

@Component({
  selector: 'app-mutual-funds',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="space-y-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">Mutual Funds</h1>
          <p class="text-gray-500 mt-1">Explore and invest in top mutual funds</p>
        </div>
      </div>

      <!-- Filters -->
      <div class="card p-4">
        <div class="flex gap-3">
          <select [(ngModel)]="selectedCategory" (change)="filterFunds()" class="input-field w-48">
            <option value="">All Categories</option>
            @for (cat of categories; track cat) {
              <option value="{{cat}}">{{cat}}</option>
            }
          </select>
          <select [(ngModel)]="selectedRisk" (change)="filterFunds()" class="input-field w-48">
            <option value="">All Risk Levels</option>
            <option value="LOW">Low Risk</option>
            <option value="MODERATE">Moderate Risk</option>
            <option value="HIGH">High Risk</option>
          </select>
        </div>
      </div>

      <!-- Fund Cards -->
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        @for (fund of filteredFunds; track fund.id) {
          <div class="card-hover p-6">
            <div class="flex items-start justify-between mb-4">
              <div>
                <h3 class="font-semibold text-gray-900">{{fund.name}}</h3>
                <span class="badge-blue mt-1 inline-block">{{fund.category}}</span>
              </div>
              <span class="badge" [class]="fund.riskLevel === 'LOW' ? 'badge-green' : fund.riskLevel === 'MODERATE' ? 'badge-blue' : 'badge-red'">
                {{fund.riskLevel}}
              </span>
            </div>

            <div class="space-y-3">
              <div class="flex justify-between">
                <span class="text-sm text-gray-500">NAV</span>
                <span class="font-semibold text-gray-900">₹{{fund.nav.toFixed(2)}}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-sm text-gray-500">Day Change</span>
                <span [class.stock-positive]="fund.dayChange >= 0" [class.stock-negative]="fund.dayChange < 0" class="font-medium">
                  {{fund.dayChange >= 0 ? '+' : ''}}{{fund.dayChangePercent.toFixed(2)}}%
                </span>
              </div>
              <div class="flex justify-between">
                <span class="text-sm text-gray-500">1Y Return</span>
                <span [class.stock-positive]="fund.oneYearReturn >= 0" [class.stock-negative]="fund.oneYearReturn < 0" class="font-medium">
                  {{fund.oneYearReturn >= 0 ? '+' : ''}}{{fund.oneYearReturn.toFixed(2)}}%
                </span>
              </div>
              <div class="flex justify-between">
                <span class="text-sm text-gray-500">Expense Ratio</span>
                <span class="font-medium text-gray-900">{{fund.expenseRatio.toFixed(2)}}%</span>
              </div>
              <div class="flex justify-between">
                <span class="text-sm text-gray-500">Min Investment</span>
                <span class="font-medium text-gray-900">₹{{fund.minimumInvestment.toLocaleString('en-IN')}}</span>
              </div>
            </div>

            <button class="btn-primary w-full mt-4 text-sm">Invest Now</button>
          </div>
        }
      </div>
    </div>
  `
})
export class MutualFundsComponent implements OnInit {
  funds: MutualFund[] = [];
  filteredFunds: MutualFund[] = [];
  categories: string[] = [];
  selectedCategory = '';
  selectedRisk = '';

  constructor() {}

  ngOnInit(): void {
    this.funds = [...Array(9)].map((_, i) => ({
      id: `MF00${i+1}`,
      name: ['Axis Bluechip Fund', 'SBI Magnum Midcap', 'HDFC Top 100', 'ICICI Pru Value Discovery',
             'Kotak Emerging Equity', 'Mirae Asset Large Cap', 'Nippon India Small Cap', 'Parag Parikh Flexi Cap', 'UTI Nifty Index'][i],
      category: ['Large Cap', 'Mid Cap', 'Large Cap', 'Value', 'Mid Cap', 'Large Cap', 'Small Cap', 'Flexi Cap', 'Index'][i],
      nav: [58.23, 156.78, 367.90, 89.45, 234.56, 67.89, 123.45, 45.67, 234.56][i],
      dayChange: [0.45, -0.78, 1.23, -0.34, 0.89, -0.12, 1.56, 0.67, -0.23][i],
      dayChangePercent: [0.78, -0.50, 0.34, -0.38, 0.38, -0.18, 1.28, 1.49, -0.10][i],
      oneYearReturn: [15.2, 22.5, 16.8, 18.9, 24.1, 14.5, 28.3, 19.7, 13.2][i],
      threeYearReturn: [42.3, 58.7, 45.6, 51.2, 62.4, 40.1, 71.5, 52.3, 38.9][i],
      fiveYearReturn: [78.5, 105.2, 82.3, 95.6, 112.8, 75.4, 135.2, 98.7, 72.1][i],
      expenseRatio: [1.05, 1.45, 1.12, 1.35, 1.55, 1.08, 1.75, 1.02, 0.35][i],
      riskLevel: (['LOW', 'MODERATE', 'LOW', 'MODERATE', 'HIGH', 'LOW', 'HIGH', 'MODERATE', 'LOW'] as const)[i],
      minimumInvestment: [500, 1000, 500, 1000, 1000, 500, 1000, 1000, 500][i],
      fundManager: ['Mr. Sharma', 'Ms. Patel', 'Mr. Kumar', 'Dr. Singh', 'Ms. Reddy', 'Mr. Gupta', 'Mr. Joshi', 'Ms. Iyer', 'Mr. Rao'][i],
      aum: [25000, 18500, 32000, 15500, 12800, 22000, 9500, 28000, 35000][i] * 10000000
    }));
    this.filteredFunds = this.funds;
    this.categories = [...new Set(this.funds.map(f => f.category))];
  }

  filterFunds(): void {
    this.filteredFunds = this.funds.filter(f =>
      (!this.selectedCategory || f.category === this.selectedCategory) &&
      (!this.selectedRisk || f.riskLevel === this.selectedRisk)
    );
  }
}
