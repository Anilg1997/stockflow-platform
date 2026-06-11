import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Stock, PriceHistory, MarketOverview, StockQuote } from '../models/stock.model';

@Injectable({ providedIn: 'root' })
export class MarketDataService {

  // Mock data for development - real API integration would replace this
  private mockStocks: Stock[] = [
    { symbol: 'RELIANCE', name: 'Reliance Industries Ltd', sector: 'Oil & Gas', currentPrice: 2850.50, previousClose: 2830.00, open: 2840.00, high: 2875.00, low: 2835.00, change: 20.50, changePercent: 0.72, volume: 12500000, marketCap: 1925000000000, pe: 28.5, dividend: 0.35 },
    { symbol: 'TCS', name: 'Tata Consultancy Services', sector: 'IT', currentPrice: 3890.00, previousClose: 3920.00, open: 3910.00, high: 3925.00, low: 3875.00, change: -30.00, changePercent: -0.77, volume: 5200000, marketCap: 1408000000000, pe: 32.1, dividend: 0.45 },
    { symbol: 'HDFCBANK', name: 'HDFC Bank Ltd', sector: 'Banking', currentPrice: 1680.00, previousClose: 1660.00, open: 1665.00, high: 1690.00, low: 1660.00, change: 20.00, changePercent: 1.20, volume: 9800000, marketCap: 935000000000, pe: 20.3, dividend: 0.22 },
    { symbol: 'INFY', name: 'Infosys Ltd', sector: 'IT', currentPrice: 1520.75, previousClose: 1530.50, open: 1528.00, high: 1535.00, low: 1515.00, change: -9.75, changePercent: -0.64, volume: 8200000, marketCap: 635000000000, pe: 26.8, dividend: 0.38 },
    { symbol: 'ICICIBANK', name: 'ICICI Bank Ltd', sector: 'Banking', currentPrice: 1120.00, previousClose: 1105.00, open: 1110.00, high: 1125.00, low: 1108.00, change: 15.00, changePercent: 1.36, volume: 15000000, marketCap: 780000000000, pe: 18.9, dividend: 0.28 },
    { symbol: 'HINDUNILVR', name: 'Hindustan Unilever Ltd', sector: 'FMCG', currentPrice: 2560.00, previousClose: 2580.00, open: 2575.00, high: 2585.00, low: 2550.00, change: -20.00, changePercent: -0.78, volume: 3100000, marketCap: 601000000000, pe: 55.2, dividend: 0.52 },
    { symbol: 'SBIN', name: 'State Bank of India', sector: 'Banking', currentPrice: 785.50, previousClose: 770.00, open: 772.00, high: 788.00, low: 770.50, change: 15.50, changePercent: 2.01, volume: 22000000, marketCap: 700000000000, pe: 12.4, dividend: 0.40 },
    { symbol: 'BHARTIARTL', name: 'Bharti Airtel Ltd', sector: 'Telecom', currentPrice: 1280.00, previousClose: 1270.00, open: 1272.00, high: 1285.00, low: 1268.00, change: 10.00, changePercent: 0.79, volume: 6800000, marketCap: 710000000000, pe: 45.6, dividend: 0.15 },
    { symbol: 'ITC', name: 'ITC Ltd', sector: 'FMCG', currentPrice: 445.00, previousClose: 440.00, open: 441.00, high: 448.00, low: 440.50, change: 5.00, changePercent: 1.14, volume: 18500000, marketCap: 555000000000, pe: 24.7, dividend: 0.65 },
    { symbol: 'WIPRO', name: 'Wipro Ltd', sector: 'IT', currentPrice: 485.00, previousClose: 490.00, open: 492.00, high: 495.00, low: 483.00, change: -5.00, changePercent: -1.02, volume: 9200000, marketCap: 265000000000, pe: 22.3, dividend: 0.30 },
    { symbol: 'HCLTECH', name: 'HCL Technologies Ltd', sector: 'IT', currentPrice: 1420.00, previousClose: 1435.00, open: 1430.00, high: 1440.00, low: 1415.00, change: -15.00, changePercent: -1.05, volume: 4100000, marketCap: 385000000000, pe: 25.1, dividend: 0.42 },
    { symbol: 'MARUTI', name: 'Maruti Suzuki India Ltd', sector: 'Automobile', currentPrice: 11250.00, previousClose: 11180.00, open: 11200.00, high: 11300.00, low: 11180.00, change: 70.00, changePercent: 0.63, volume: 1200000, marketCap: 340000000000, pe: 28.9, dividend: 0.25 },
  ];

  constructor() {}

  getMarketOverview(): Observable<MarketOverview> {
    return of(this.getMockMarketOverview());
  }

  getStockPrice(symbol: string): Observable<StockQuote> {
    const stock = this.mockStocks.find(s => s.symbol === symbol);
    return of({
      symbol: symbol,
      price: stock?.currentPrice || 0,
      change: stock?.change || 0,
      changePercent: stock?.changePercent || 0,
      timestamp: new Date().toISOString()
    });
  }

  getStockDetail(symbol: string): Stock | undefined {
    return this.mockStocks.find(s => s.symbol === symbol);
  }

  getPriceHistory(symbol: string, period: '1D' | '1W' | '1M' | '3M' | '1Y' = '1M'): PriceHistory[] {
    const data: PriceHistory[] = [];
    const days = period === '1D' ? 1 : period === '1W' ? 7 : period === '1M' ? 30 : period === '3M' ? 90 : 365;
    const stock = this.mockStocks.find(s => s.symbol === symbol);
    const basePrice = stock?.currentPrice || 1000;

    for (let i = days; i >= 0; i--) {
      const date = new Date();
      date.setDate(date.getDate() - i);
      const variation = (Math.random() - 0.5) * basePrice * 0.02;
      const close = basePrice + variation * (i / days);
      const high = close * (1 + Math.random() * 0.01);
      const low = close * (1 - Math.random() * 0.01);
      data.push({
        date: date.toISOString().split('T')[0],
        open: close - variation * 0.5,
        high,
        low,
        close,
        volume: Math.floor(Math.random() * 10000000)
      });
    }
    return data;
  }

  searchStocks(query: string): Stock[] {
    const q = query.toLowerCase();
    return this.mockStocks.filter(s =>
      s.symbol.toLowerCase().includes(q) ||
      s.name.toLowerCase().includes(q)
    );
  }

  getAllStocks(): Stock[] {
    return this.mockStocks;
  }

  private getMockMarketOverview(): MarketOverview {
    return {
      indices: [
        { name: 'NIFTY 50', value: 24250.50, change: 125.75, changePercent: 0.52 },
        { name: 'SENSEX', value: 79850.00, change: 425.30, changePercent: 0.54 },
        { name: 'BANK NIFTY', value: 51200.00, change: 380.00, changePercent: 0.75 }
      ],
      topGainers: [this.mockStocks[2], this.mockStocks[4], this.mockStocks[6]],
      topLosers: [this.mockStocks[3], this.mockStocks[5], this.mockStocks[9]],
      mostActive: [this.mockStocks[0], this.mockStocks[6], this.mockStocks[8]]
    };
  }
}
