export interface MutualFund {
  id: string;
  name: string;
  category: string;
  nav: number;
  dayChange: number;
  dayChangePercent: number;
  oneYearReturn: number;
  threeYearReturn: number;
  fiveYearReturn: number;
  expenseRatio: number;
  riskLevel: 'LOW' | 'MODERATE' | 'HIGH';
  minimumInvestment: number;
  fundManager: string;
  aum: number;
}

export interface MFInvestment {
  id: string;
  fundId: string;
  fundName: string;
  units: number;
  buyNav: number;
  currentNav: number;
  investedAmount: number;
  currentValue: number;
  returns: number;
  returnsPercent: number;
  investedDate: string;
}

export interface SIPPlan {
  id: string;
  fundId: string;
  fundName: string;
  amount: number;
  frequency: 'DAILY' | 'WEEKLY' | 'MONTHLY' | 'QUARTERLY';
  nextDate: string;
  status: 'ACTIVE' | 'PAUSED' | 'CANCELLED';
  totalInvested: number;
  units: number;
  startDate: string;
}
