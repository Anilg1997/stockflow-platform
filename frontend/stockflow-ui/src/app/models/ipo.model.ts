export interface IPO {
  id: string;
  companyName: string;
  logo?: string;
  issuePrice: number;
  lotSize: number;
  minAmount: number;
  maxAmount: number;
  openDate: string;
  closeDate: string;
  allotmentDate: string;
  listingDate: string;
  status: 'OPEN' | 'CLOSED' | 'UPCOMING' | 'LISTED';
  totalShares: number;
  issueSize: number;
  priceRange: {
    min: number;
    max: number;
  };
  subscription: {
    retail: number;
    qib: number;
    nii: number;
    total: number;
  };
  leadManagers: string[];
  registrar: string;
}

export interface IPOApplication {
  id: string;
  ipoId: string;
  companyName: string;
  sharesApplied: number;
  amount: number;
  applicationDate: string;
  status: 'PENDING' | 'ALLOTTED' | 'NOT_ALLOTTED' | 'CANCELLED';
  allotmentQuantity?: number;
  listingPrice?: number;
  profitLoss?: number;
}
