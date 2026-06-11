export interface Holding {
  symbol: string;
  companyName: string;
  quantity: number;
  buyPrice: number;
  currentPrice: number;
  totalInvestment: number;
  currentValue: number;
  pnl: number;
  pnlPercent: number;
  dayChange: number;
  dayChangePercent: number;
  sector: string;
}

export interface Portfolio {
  totalInvestment: number;
  currentValue: number;
  totalPnl: number;
  totalPnlPercent: number;
  dayPnl: number;
  holdings: Holding[];
}

export interface FundsSummary {
  totalBalance: number;
  availableBalance: number;
  usedMargin: number;
  pendingDeposits: number;
  pendingWithdrawals: number;
}
