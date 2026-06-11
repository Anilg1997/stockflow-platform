export interface Order {
  id: string;
  symbol: string;
  quantity: number;
  price: number;
  totalAmount: number;
  side: 'BUY' | 'SELL';
  orderType: 'MARKET' | 'LIMIT' | 'STOP_LOSS' | 'STOP_LOSS_LIMIT';
  status: 'PENDING' | 'EXECUTED' | 'CANCELLED' | 'REJECTED' | 'PARTIALLY_FILLED';
  filledQuantity: number;
  triggerPrice?: number;
  createdAt: string;
  executedAt?: string;
  brokerage: number;
  exchange: 'NSE' | 'BSE';
  validity: 'DAY' | 'IOC';
}

export interface PlaceOrderRequest {
  symbol: string;
  quantity: number;
  price: number;
  side: 'BUY' | 'SELL';
  orderType: 'MARKET' | 'LIMIT' | 'STOP_LOSS';
  triggerPrice?: number;
  exchange?: 'NSE' | 'BSE';
  validity?: 'DAY' | 'IOC';
}

export interface Trade {
  id: string;
  symbol: string;
  quantity: number;
  price: number;
  totalAmount: number;
  side: 'BUY' | 'SELL';
  executedAt: string;
  orderId: string;
  exchange: string;
}
