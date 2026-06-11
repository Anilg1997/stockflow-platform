export interface Stock {
  symbol: string;
  name: string;
  sector: string;
  currentPrice: number;
  previousClose: number;
  open: number;
  high: number;
  low: number;
  change: number;
  changePercent: number;
  volume: number;
  marketCap: number;
  pe: number;
  dividend: number;
  isInWatchlist?: boolean;
}

export interface PriceHistory {
  date: string;
  open: number;
  high: number;
  low: number;
  close: number;
  volume: number;
}

export interface MarketIndices {
  name: string;
  value: number;
  change: number;
  changePercent: number;
}

export interface MarketOverview {
  indices: MarketIndices[];
  topGainers: Stock[];
  topLosers: Stock[];
  mostActive: Stock[];
}

export interface StockQuote {
  symbol: string;
  price: number;
  change: number;
  changePercent: number;
  timestamp: string;
}
