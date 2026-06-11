import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { FundsSummary } from '../models/portfolio.model';

export interface FundTransaction {
  id: string;
  type: 'DEPOSIT' | 'WITHDRAWAL';
  amount: number;
  status: 'PENDING' | 'COMPLETED' | 'FAILED';
  method: string;
  createdAt: string;
  completedAt?: string;
  referenceId?: string;
}

@Injectable({ providedIn: 'root' })
export class FundsService {
  private readonly API_URL = '/api/funds';

  private transactions: FundTransaction[] = [
    { id: 'FT001', type: 'DEPOSIT', amount: 100000, status: 'COMPLETED', method: 'UPI', createdAt: '2024-11-28T10:00:00', completedAt: '2024-11-28T10:05:00', referenceId: 'UPI123456' },
    { id: 'FT002', type: 'WITHDRAWAL', amount: 25000, status: 'COMPLETED', method: 'NEFT', createdAt: '2024-11-29T14:30:00', completedAt: '2024-11-30T09:00:00', referenceId: 'NEFT789012' },
    { id: 'FT003', type: 'DEPOSIT', amount: 50000, status: 'PENDING', method: 'UPI', createdAt: '2024-12-03T16:00:00', referenceId: 'UPI345678' },
  ];

  constructor(private http: HttpClient) {}

  deposit(amount: number, method: string = 'UPI'): Observable<FundTransaction> {
    return this.http.post<FundTransaction>(`${this.API_URL}/deposit`, { amount, method });
  }

  withdraw(amount: number, method: string = 'NEFT'): Observable<FundTransaction> {
    return this.http.post<FundTransaction>(`${this.API_URL}/withdraw`, { amount, method });
  }

  getTransactions(): Observable<FundTransaction[]> {
    return of(this.transactions);
  }
}
