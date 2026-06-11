import { Component, OnInit } from '@angular/core';
import { IPO } from '../../models/ipo.model';

@Component({
  selector: 'app-ipos',
  standalone: true,
  template: `
    <div class="space-y-6">
      <div class="flex items-center justify-between">
        <div>
          <h1 class="text-2xl font-bold text-gray-900">IPOs</h1>
          <p class="text-gray-500 mt-1">Apply to upcoming and ongoing IPOs</p>
        </div>
      </div>

      <!-- Filter Tabs -->
      <div class="flex gap-2">
        @for (tab of tabs; track tab.key) {
          <button (click)="filterByTab(tab.key)" class="px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200"
            [class]="selectedTab === tab.key ? 'bg-indigo-600 text-white' : 'bg-white text-gray-600 hover:bg-gray-100 border border-gray-200'">
            {{tab.label}}
          </button>
        }
      </div>

      <!-- IPO Cards -->
      <div class="space-y-4">
        @for (ipo of filteredIPOs; track ipo.id) {
          <div class="card-hover p-6">
            <div class="flex items-start justify-between">
              <div class="flex items-start gap-4">
                <div class="w-12 h-12 bg-gradient-to-br from-indigo-500 to-purple-600 rounded-xl flex items-center justify-center">
                  <span class="text-white font-bold text-lg">{{ipo.companyName.charAt(0)}}</span>
                </div>
                <div>
                  <h3 class="text-lg font-semibold text-gray-900">{{ipo.companyName}}</h3>
                  <div class="flex items-center gap-3 mt-1">
                    <span class="badge" [class]="ipo.status === 'OPEN' ? 'badge-green' : ipo.status === 'UPCOMING' ? 'badge-blue' : ipo.status === 'CLOSED' ? 'badge-gray' : 'badge'">
                      {{ipo.status}}
                    </span>
                    <span class="text-sm text-gray-500">₹{{ipo.issuePrice}} per share</span>
                  </div>
                </div>
              </div>
              <div class="text-right">
                <p class="text-sm text-gray-500">Lot Size</p>
                <p class="font-semibold text-gray-900">{{ipo.lotSize}} shares</p>
              </div>
            </div>

            <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mt-4 pt-4 border-t border-gray-100">
              <div>
                <p class="text-xs text-gray-500">Price Range</p>
                <p class="font-semibold text-gray-900">₹{{ipo.priceRange.min}} - ₹{{ipo.priceRange.max}}</p>
              </div>
              <div>
                <p class="text-xs text-gray-500">Open Date</p>
                <p class="font-semibold text-gray-900">{{ipo.openDate}}</p>
              </div>
              <div>
                <p class="text-xs text-gray-500">Close Date</p>
                <p class="font-semibold text-gray-900">{{ipo.closeDate}}</p>
              </div>
              <div>
                <p class="text-xs text-gray-500">Listing Date</p>
                <p class="font-semibold text-gray-900">{{ipo.listingDate}}</p>
              </div>
            </div>

            <!-- Subscription Status -->
            <div class="mt-4 p-3 bg-gray-50 rounded-lg">
              <p class="text-sm font-medium text-gray-700 mb-2">Subscription Status</p>
              <div class="grid grid-cols-4 gap-4 text-center">
                <div>
                  <p class="text-xs text-gray-500">Retail</p>
                  <p class="font-semibold stock-positive">{{ipo.subscription.retail.toFixed(2)}}x</p>
                </div>
                <div>
                  <p class="text-xs text-gray-500">QIB</p>
                  <p class="font-semibold stock-positive">{{ipo.subscription.qib.toFixed(2)}}x</p>
                </div>
                <div>
                  <p class="text-xs text-gray-500">NII</p>
                  <p class="font-semibold stock-positive">{{ipo.subscription.nii.toFixed(2)}}x</p>
                </div>
                <div>
                  <p class="text-xs text-gray-500">Total</p>
                  <p class="font-semibold stock-positive">{{ipo.subscription.total.toFixed(2)}}x</p>
                </div>
              </div>
            </div>

            @if (ipo.status === 'OPEN') {
              <button class="btn-primary w-full mt-4">Apply Now</button>
            }
          </div>
        }
      </div>
    </div>
  `
})
export class IposComponent implements OnInit {
  selectedTab = 'all';
  ipos: IPO[] = [];
  filteredIPOs: IPO[] = [];

  tabs = [
    { key: 'all', label: 'All IPOs' },
    { key: 'OPEN', label: 'Open' },
    { key: 'UPCOMING', label: 'Upcoming' },
    { key: 'CLOSED', label: 'Closed' },
    { key: 'LISTED', label: 'Listed' }
  ];

  ngOnInit(): void {
    this.ipos = [...Array(6)].map((_, i) => ({
      id: `IPO${100+i}`,
      companyName: ['TechSolutions Ltd', 'GreenEnergy Corp', 'HealthFirst Pharma', 'EduPrime Tech', 'FinVentures Ltd', 'AutoDrive EV'][i],
      issuePrice: [450, 320, 580, 275, 410, 525][i],
      lotSize: [30, 45, 25, 50, 35, 28][i],
      minAmount: [13500, 14400, 14500, 13750, 14350, 14700][i],
      maxAmount: [500000, 400000, 600000, 350000, 450000, 550000][i],
      openDate: ['15-Jan-2025', '10-Jan-2025', '20-Jan-2025', '05-Feb-2025', '25-Jan-2025', '01-Feb-2025'][i],
      closeDate: ['19-Jan-2025', '14-Jan-2025', '24-Jan-2025', '09-Feb-2025', '29-Jan-2025', '05-Feb-2025'][i],
      allotmentDate: ['25-Jan-2025', '20-Jan-2025', '30-Jan-2025', '15-Feb-2025', '05-Feb-2025', '12-Feb-2025'][i],
      listingDate: ['28-Jan-2025', '22-Jan-2025', '02-Feb-2025', '18-Feb-2025', '08-Feb-2025', '15-Feb-2025'][i],
      status: (['OPEN', 'OPEN', 'UPCOMING', 'UPCOMING', 'CLOSED', 'LISTED'] as const)[i],
      totalShares: [5000000, 8000000, 3500000, 6000000, 4500000, 7000000][i],
      issueSize: [2250, 2560, 2030, 1650, 1845, 3675][i] * 100000,
      priceRange: { min: [430, 300, 550, 260, 390, 500][i], max: [470, 340, 610, 290, 430, 550][i] },
      subscription: {
        retail: [3.45, 2.80, 4.12, 1.95, 5.23, 8.76][i],
        qib: [2.15, 1.95, 3.45, 1.50, 4.10, 6.85][i],
        nii: [4.80, 3.20, 5.60, 2.30, 6.75, 9.42][i],
        total: [3.25, 2.55, 4.05, 1.85, 5.15, 8.12][i]
      },
      leadManagers: ['Kotak Mahindra', 'ICICI Securities', 'HDFC Bank', 'Axis Capital', 'SBI Caps', 'Goldman Sachs'][i].split(','),
      registrar: 'Link Intime India'
    }));
    this.filteredIPOs = this.ipos;
  }

  filterByTab(tab: string): void {
    if (tab === 'all') {
      this.filteredIPOs = this.ipos;
    } else {
      this.filteredIPOs = this.ipos.filter(ipo => ipo.status === tab);
    }
  }
}
