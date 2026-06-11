import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Order, PlaceOrderRequest, Trade } from '../models/order.model';

@Injectable({ providedIn: 'root' })
export class OrderService {
  private readonly API_URL = '/api/orders';

  private mockOrders: Order[] = [
    { id: 'ORD001', symbol: 'RELIANCE', quantity: 10, price: 2850.50, totalAmount: 28505, side: 'BUY', orderType: 'LIMIT', status: 'EXECUTED', filledQuantity: 10, createdAt: '2024-12-01T09:30:00', executedAt: '2024-12-01T09:30:15', brokerage: 14.25, exchange: 'NSE', validity: 'DAY' },
    { id: 'ORD002', symbol: 'TCS', quantity: 5, price: 3890.00, totalAmount: 19450, side: 'SELL', orderType: 'MARKET', status: 'EXECUTED', filledQuantity: 5, createdAt: '2024-12-02T10:15:00', executedAt: '2024-12-02T10:15:05', brokerage: 9.72, exchange: 'BSE', validity: 'DAY' },
    { id: 'ORD003', symbol: 'HDFCBANK', quantity: 15, price: 1670.00, totalAmount: 25050, side: 'BUY', orderType: 'LIMIT', status: 'PENDING', filledQuantity: 0, createdAt: '2024-12-03T11:00:00', brokerage: 12.52, exchange: 'NSE', validity: 'DAY' },
  ];

  constructor(private http: HttpClient) {}

  placeOrder(request: PlaceOrderRequest): Observable<Order> {
    return this.http.post<Order>(this.API_URL, request);
  }

  getOrders(): Observable<Order[]> {
    return of(this.mockOrders);
  }

  cancelOrder(orderId: string): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${orderId}`);
  }

  getTradeHistory(): Observable<Trade[]> {
    const trades: Trade[] = this.mockOrders
      .filter(o => o.status === 'EXECUTED')
      .map(o => ({
        id: `TRD${o.id}`,
        symbol: o.symbol,
        quantity: o.filledQuantity,
        price: o.price,
        totalAmount: o.totalAmount,
        side: o.side,
        executedAt: o.executedAt || o.createdAt,
        orderId: o.id,
        exchange: o.exchange
      }));
    return of(trades);
  }
}
