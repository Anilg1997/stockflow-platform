export interface Watchlist {
  id: string;
  name: string;
  stocks: WatchlistStock[];
  createdAt: string;
  updatedAt: string;
}

export interface WatchlistStock {
  symbol: string;
  name: string;
  price: number;
  change: number;
  changePercent: number;
  addedAt: string;
  alert?: PriceAlert;
}

export interface PriceAlert {
  id: string;
  symbol: string;
  targetPrice: number;
  condition: 'ABOVE' | 'BELOW';
  isTriggered: boolean;
  createdAt: string;
}
