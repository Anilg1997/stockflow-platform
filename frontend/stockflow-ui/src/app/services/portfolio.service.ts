import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Portfolio, Holding, FundsSummary } from '../models/portfolio.model';

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  private readonly API_URL = '/api';

  private mockHoldings: Holding[] = [
    { symbol: 'RELIANCE', companyName: 'Reliance Industries Ltd', quantity: 50, buyPrice: 2650.00, currentPrice: 2850.50, totalInvestment: 132500, currentValue: 142525, pnl: 10025, pnlPercent: 7.57, dayChange: 20.50, dayChangePercent: 0.72, sector: 'Oil & Gas' },
    { symbol: 'TCS', companyName: 'Tata Consultancy Services', quantity: 20, buyPrice: 3750.00, currentPrice: 3890.00, totalInvestment: 75000, currentValue: 77800, pnl: 2800, pnlPercent: 3.73, dayChange: -30.00, dayChangePercent: -0.77, sector: 'IT' },
    { symbol: 'HDFCBANK', companyName: 'HDFC Bank Ltd', quantity: 30, buyPrice: 1550.00, currentPrice: 1680.00, totalInvestment: 46500, currentValue: 50400, pnl: 3900, pnlPercent: 8.39, dayChange: 20.00, dayChangePercent: 1.20, sector: 'Banking' },
    { symbol: 'INFY', companyName: 'Infosys Ltd', quantity: 25, buyPrice: 1480.00, currentPrice: 1520.75, totalInvestment: 37000, currentValue: 38018.75, pnl: 1018.75, pnlPercent: 2.75, dayChange: -9.75, dayChangePercent: -0.64, sector: 'IT' },
    { symbol: 'SBIN', companyName: 'State Bank of India', quantity: 100, buyPrice: 720.00, currentPrice: 785.50, totalInvestment: 72000, currentValue: 78550, pnl: 6550, pnlPercent: 9.10, dayChange: 15.50, dayChangePercent: 2.01, sector: 'Banking' },
  ];

  constructor(private http: HttpClient) {}

  getPortfolio(): Observable<Portfolio> {
    const totalInvestment = this.mockHoldings.reduce((s, h) => s + h.totalInvestment, 0);
    const currentValue = this.mockHoldings.reduce((s, h) => s + h.currentValue, 0);
    const totalPnl = currentValue - totalInvestment;
    return of({
      totalInvestment,
      currentValue,
      totalPnl,
      totalPnlPercent: (totalPnl / totalInvestment) * 100,
      dayPnl: this.mockHoldings.reduce((s, h) => s + (h.dayChange * h.quantity), 0),
      holdings: this.mockHoldings
    });
  }

  getFundsSummary(): Observable<FundsSummary> {
    return of({
      totalBalance: 250000,
      availableBalance: 180000,
      usedMargin: 70000,
      pendingDeposits: 50000,
      pendingWithdrawals: 25000
    });
  }
}
