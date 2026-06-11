-- ═══════════════════════════════════════════════════════════════
-- StockFlow Platform — PostgreSQL Schema Initialization
-- All schemas for 25+ microservices
-- ═══════════════════════════════════════════════════════════════

-- Create all schemas
CREATE SCHEMA IF NOT EXISTS auth_schema;
CREATE SCHEMA IF NOT EXISTS user_schema;
CREATE SCHEMA IF NOT EXISTS market_schema;
CREATE SCHEMA IF NOT EXISTS order_schema;
CREATE SCHEMA IF NOT EXISTS trade_schema;
CREATE SCHEMA IF NOT EXISTS holdings_schema;
CREATE SCHEMA IF NOT EXISTS mf_schema;
CREATE SCHEMA IF NOT EXISTS funds_schema;
CREATE SCHEMA IF NOT EXISTS ipo_schema;
CREATE SCHEMA IF NOT EXISTS alert_schema;
CREATE SCHEMA IF NOT EXISTS report_schema;
CREATE SCHEMA IF NOT EXISTS watchlist_schema;
CREATE SCHEMA IF NOT EXISTS brokerage_schema;
CREATE SCHEMA IF NOT EXISTS analytics_schema;
CREATE SCHEMA IF NOT EXISTS search_schema;

-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ═══════════════════════════════════════════════════════════════
-- AUTH SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE auth_schema.users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT true,
    is_email_verified BOOLEAN DEFAULT false,
    totp_secret VARCHAR(64),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE auth_schema.refresh_tokens (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES auth_schema.users(id) ON DELETE CASCADE,
    token_hash VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    revoked BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_refresh_tokens_user_id ON auth_schema.refresh_tokens(user_id);
CREATE INDEX idx_refresh_tokens_token_hash ON auth_schema.refresh_tokens(token_hash);

CREATE TABLE auth_schema.outbox_events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,
    published BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_outbox_events_unpublished ON auth_schema.outbox_events(published, created_at);

-- ═══════════════════════════════════════════════════════════════
-- USER SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE user_schema.profiles (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID UNIQUE NOT NULL,
    full_name VARCHAR(150) NOT NULL,
    pan_number VARCHAR(10),
    aadhaar_last4 VARCHAR(4),
    dob DATE,
    kyc_status VARCHAR(20) DEFAULT 'PENDING',
    kyc_reviewed_at TIMESTAMP WITH TIME ZONE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_profiles_user_id ON user_schema.profiles(user_id);
CREATE INDEX idx_profiles_kyc_status ON user_schema.profiles(kyc_status);

CREATE TABLE user_schema.bank_accounts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    bank_name VARCHAR(100) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    ifsc VARCHAR(11) NOT NULL,
    is_primary BOOLEAN DEFAULT false,
    is_verified BOOLEAN DEFAULT false,
    added_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_bank_accounts_user_id ON user_schema.bank_accounts(user_id);

CREATE TABLE user_schema.nominees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    relation VARCHAR(50) NOT NULL,
    percentage NUMERIC(5,2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_nominees_user_id ON user_schema.nominees(user_id);

-- ═══════════════════════════════════════════════════════════════
-- MARKET SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE market_schema.stocks (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    symbol VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    exchange VARCHAR(10) NOT NULL DEFAULT 'NSE',
    sector VARCHAR(50) NOT NULL,
    isin VARCHAR(12),
    face_value NUMERIC(10,2) DEFAULT 10.00,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_stocks_symbol ON market_schema.stocks(symbol);
CREATE INDEX idx_stocks_sector ON market_schema.stocks(sector);

CREATE TABLE market_schema.stock_prices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    symbol VARCHAR(20) UNIQUE NOT NULL,
    current_price NUMERIC(12,2) NOT NULL,
    open NUMERIC(12,2),
    high NUMERIC(12,2),
    low NUMERIC(12,2),
    close NUMERIC(12,2),
    volume BIGINT DEFAULT 0,
    week_high_52 NUMERIC(12,2),
    week_low_52 NUMERIC(12,2),
    change NUMERIC(12,2),
    change_percent NUMERIC(8,4),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_stock_prices_symbol ON market_schema.stock_prices(symbol);

CREATE TABLE market_schema.indices (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) UNIQUE NOT NULL,
    value NUMERIC(12,2) NOT NULL,
    change NUMERIC(12,2),
    change_percent NUMERIC(8,4),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ═══════════════════════════════════════════════════════════════
-- ORDER SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE order_schema.orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    order_type VARCHAR(20) NOT NULL,
    side VARCHAR(4) NOT NULL,
    trade_type VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(12,2),
    trigger_price NUMERIC(12,2),
    status VARCHAR(20) DEFAULT 'OPEN',
    executed_price NUMERIC(12,2),
    executed_quantity INT DEFAULT 0,
    placed_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    executed_at TIMESTAMP WITH TIME ZONE,
    cancelled_at TIMESTAMP WITH TIME ZONE,
    rejection_reason TEXT,
    version INT DEFAULT 0
);

CREATE INDEX idx_orders_user_id ON order_schema.orders(user_id);
CREATE INDEX idx_orders_symbol ON order_schema.orders(symbol);
CREATE INDEX idx_orders_status ON order_schema.orders(status);
CREATE INDEX idx_orders_placed_at ON order_schema.orders(placed_at DESC);

CREATE TABLE order_schema.outbox_events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,
    published BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_order_outbox_unpublished ON order_schema.outbox_events(published, created_at);

-- ═══════════════════════════════════════════════════════════════
-- TRADE SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE trade_schema.trades (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID NOT NULL,
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    side VARCHAR(4) NOT NULL,
    trade_type VARCHAR(10) NOT NULL,
    quantity INT NOT NULL,
    price NUMERIC(12,2) NOT NULL,
    brokerage NUMERIC(10,2) DEFAULT 0,
    stt NUMERIC(10,2) DEFAULT 0,
    exchange_charges NUMERIC(10,2) DEFAULT 0,
    gst NUMERIC(10,2) DEFAULT 0,
    sebi_charges NUMERIC(10,2) DEFAULT 0,
    stamp_duty NUMERIC(10,2) DEFAULT 0,
    total_charges NUMERIC(10,2) DEFAULT 0,
    net_amount NUMERIC(12,2) NOT NULL,
    trade_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_trades_user_id ON trade_schema.trades(user_id);
CREATE INDEX idx_trades_order_id ON trade_schema.trades(order_id);
CREATE INDEX idx_trades_symbol ON trade_schema.trades(symbol);
CREATE INDEX idx_trades_trade_date ON trade_schema.trades(trade_date DESC);

-- ═══════════════════════════════════════════════════════════════
-- HOLDINGS SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE holdings_schema.holdings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    avg_buy_price NUMERIC(12,2) NOT NULL,
    invested_amount NUMERIC(14,2) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, symbol)
);

CREATE INDEX idx_holdings_user_id ON holdings_schema.holdings(user_id);

CREATE TABLE holdings_schema.realized_pnl (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    quantity INT NOT NULL,
    buy_price NUMERIC(12,2) NOT NULL,
    sell_price NUMERIC(12,2) NOT NULL,
    pnl NUMERIC(12,2) NOT NULL,
    financial_year VARCHAR(9) NOT NULL,
    trade_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_realized_pnl_user_id ON holdings_schema.realized_pnl(user_id);
CREATE INDEX idx_realized_pnl_fy ON holdings_schema.realized_pnl(financial_year);

-- ═══════════════════════════════════════════════════════════════
-- MUTUAL FUND SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE mf_schema.mutual_funds (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    scheme_code VARCHAR(20) UNIQUE NOT NULL,
    scheme_name VARCHAR(300) NOT NULL,
    fund_house VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    sub_category VARCHAR(50),
    nav NUMERIC(12,4) NOT NULL,
    day_change NUMERIC(12,4) DEFAULT 0,
    risk_level VARCHAR(20) NOT NULL DEFAULT 'MODERATE',
    exit_load VARCHAR(100),
    expense_ratio NUMERIC(6,4),
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_mf_category ON mf_schema.mutual_funds(category);
CREATE INDEX idx_mf_fund_house ON mf_schema.mutual_funds(fund_house);

CREATE TABLE mf_schema.sips (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    scheme_code VARCHAR(20) NOT NULL,
    monthly_amount NUMERIC(12,2) NOT NULL,
    sip_date INT NOT NULL CHECK (sip_date BETWEEN 1 AND 28),
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(20) DEFAULT 'ACTIVE',
    next_execution DATE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sips_user_id ON mf_schema.sips(user_id);
CREATE INDEX idx_sips_status ON mf_schema.sips(status);
CREATE INDEX idx_sips_next_execution ON mf_schema.sips(next_execution);

CREATE TABLE mf_schema.mf_holdings (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    scheme_code VARCHAR(20) NOT NULL,
    units NUMERIC(14,4) NOT NULL DEFAULT 0,
    avg_nav NUMERIC(12,4) NOT NULL,
    invested_amount NUMERIC(14,2) NOT NULL DEFAULT 0,
    last_updated TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, scheme_code)
);

CREATE INDEX idx_mf_holdings_user_id ON mf_schema.mf_holdings(user_id);

-- ═══════════════════════════════════════════════════════════════
-- FUNDS SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE funds_schema.wallets (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID UNIQUE NOT NULL,
    available_balance NUMERIC(14,2) DEFAULT 0,
    on_hold NUMERIC(14,2) DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE funds_schema.fund_transactions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    type VARCHAR(30) NOT NULL,
    amount NUMERIC(14,2) NOT NULL,
    reference VARCHAR(100),
    description TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_fund_tx_user_id ON funds_schema.fund_transactions(user_id);
CREATE INDEX idx_fund_tx_type ON funds_schema.fund_transactions(type);
CREATE INDEX idx_fund_tx_created_at ON funds_schema.fund_transactions(created_at DESC);

CREATE TABLE funds_schema.outbox_events (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,
    published BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- ═══════════════════════════════════════════════════════════════
-- IPO SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE ipo_schema.ipos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_name VARCHAR(200) NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    price_min NUMERIC(12,2) NOT NULL,
    price_max NUMERIC(12,2) NOT NULL,
    lot_size INT NOT NULL,
    open_date DATE NOT NULL,
    close_date DATE NOT NULL,
    listing_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'UPCOMING',
    gmp NUMERIC(12,2) DEFAULT 0,
    subscription_times NUMERIC(8,2) DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_ipos_status ON ipo_schema.ipos(status);

CREATE TABLE ipo_schema.ipo_applications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    ipo_id UUID NOT NULL REFERENCES ipo_schema.ipos(id),
    lots INT NOT NULL,
    bid_price NUMERIC(12,2) NOT NULL,
    status VARCHAR(20) DEFAULT 'APPLIED',
    upi_id VARCHAR(50),
    applied_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    allotment_result VARCHAR(20)
);

CREATE INDEX idx_ipo_app_user_id ON ipo_schema.ipo_applications(user_id);
CREATE INDEX idx_ipo_app_ipo_id ON ipo_schema.ipo_applications(ipo_id);

-- ═══════════════════════════════════════════════════════════════
-- BROKERAGE SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE brokerage_schema.charge_config (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    charge_type VARCHAR(50) UNIQUE NOT NULL,
    rate NUMERIC(8,6) NOT NULL,
    min_amount NUMERIC(10,2) DEFAULT 0,
    max_amount NUMERIC(10,2),
    description TEXT,
    is_active BOOLEAN DEFAULT true,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO brokerage_schema.charge_config (charge_type, rate, min_amount, max_amount, description) VALUES
('BROKERAGE', 0.0000, 0, 20, 'Flat ₹20 or 0% (Zero brokerage on equity delivery)'),
('STT', 0.0010, 0, NULL, 'Securities Transaction Tax (0.1% on sell)'),
('EXCHANGE_CHARGES', 0.0000325, 0, NULL, 'NSE/BSE exchange transaction charges'),
('GST', 0.1800, 0, NULL, '18% GST on brokerage + exchange charges'),
('SEBI_CHARGES', 0.0000010, 0, NULL, 'SEBI turnover fees (₹10 per crore)'),
('STAMP_DUTY', 0.0000300, 0, NULL, 'Stamp duty on buy transactions');

-- ═══════════════════════════════════════════════════════════════
-- ANALYTICS SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE analytics_schema.portfolio_performance (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    date DATE NOT NULL,
    total_invested NUMERIC(14,2) NOT NULL,
    total_current_value NUMERIC(14,2) NOT NULL,
    daily_pnl NUMERIC(12,2) DEFAULT 0,
    total_pnl NUMERIC(12,2) DEFAULT 0,
    xirr NUMERIC(6,4),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, date)
);

CREATE INDEX idx_perf_user_id ON analytics_schema.portfolio_performance(user_id);
CREATE INDEX idx_perf_date ON analytics_schema.portfolio_performance(date DESC);

-- ═══════════════════════════════════════════════════════════════
-- SEARCH SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE search_schema.search_history (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID,
    query VARCHAR(200) NOT NULL,
    type VARCHAR(30) DEFAULT 'STOCK',
    result_count INT DEFAULT 0,
    searched_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_search_history_user ON search_schema.search_history(user_id);
CREATE INDEX idx_search_history_query ON search_schema.search_history(query);

-- ═══════════════════════════════════════════════════════════════
-- ALERT SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE alert_schema.price_alerts (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    target_price NUMERIC(12,2) NOT NULL,
    condition VARCHAR(10) NOT NULL,
    is_triggered BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    triggered_at TIMESTAMP WITH TIME ZONE
);

CREATE INDEX idx_alerts_user_id ON alert_schema.price_alerts(user_id);
CREATE INDEX idx_alerts_symbol ON alert_schema.price_alerts(symbol);
CREATE INDEX idx_alerts_active ON alert_schema.price_alerts(is_triggered, symbol);

-- ═══════════════════════════════════════════════════════════════
-- WATCHLIST SCHEMA
-- ═══════════════════════════════════════════════════════════════

CREATE TABLE watchlist_schema.watchlists (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    is_default BOOLEAN DEFAULT false,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_watchlists_user_id ON watchlist_schema.watchlists(user_id);

CREATE TABLE watchlist_schema.watchlist_items (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    watchlist_id UUID NOT NULL REFERENCES watchlist_schema.watchlists(id) ON DELETE CASCADE,
    symbol VARCHAR(20) NOT NULL,
    added_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(watchlist_id, symbol)
);

CREATE INDEX idx_watchlist_items_watchlist_id ON watchlist_schema.watchlist_items(watchlist_id);

-- ═══════════════════════════════════════════════════════════════
-- SEED DATA — NSE/BSE Stocks (200+ stocks)
-- ═══════════════════════════════════════════════════════════════

INSERT INTO market_schema.stocks (symbol, name, exchange, sector, isin, face_value) VALUES
('RELIANCE', 'Reliance Industries Ltd', 'NSE', 'Energy', 'INE002A01018', 10),
('TCS', 'Tata Consultancy Services Ltd', 'NSE', 'IT', 'INE467B01029', 1),
('INFY', 'Infosys Ltd', 'NSE', 'IT', 'INE009A01021', 5),
('HDFCBANK', 'HDFC Bank Ltd', 'NSE', 'Banking', 'INE040A01026', 2),
('ICICIBANK', 'ICICI Bank Ltd', 'NSE', 'Banking', 'INE090A01021', 2),
('WIPRO', 'Wipro Ltd', 'NSE', 'IT', 'INE075A01035', 2),
('SBIN', 'State Bank of India', 'NSE', 'Banking', 'INE062A01020', 1),
('ITC', 'ITC Ltd', 'NSE', 'FMCG', 'INE154A01025', 1),
('BHARTIARTL', 'Bharti Airtel Ltd', 'NSE', 'Telecom', 'INE398D01024', 5),
('KOTAKBANK', 'Kotak Mahindra Bank Ltd', 'NSE', 'Banking', 'INE237A01028', 5),
('LT', 'Larsen & Toubro Ltd', 'NSE', 'Infrastructure', 'INE018A01030', 2),
('AXISBANK', 'Axis Bank Ltd', 'NSE', 'Banking', 'INE238A01034', 2),
('ASIANPAINT', 'Asian Paints Ltd', 'NSE', 'Consumer', 'INE021A01022', 1),
('MARUTI', 'Maruti Suzuki India Ltd', 'NSE', 'Auto', 'INE585B01010', 5),
('SUNPHARMA', 'Sun Pharmaceutical Industries Ltd', 'NSE', 'Pharma', 'INE044A01036', 1),
('TATAMOTORS', 'Tata Motors Ltd', 'NSE', 'Auto', 'INE155A01022', 2),
('BAJFINANCE', 'Bajaj Finance Ltd', 'NSE', 'Finance', 'INE296A01024', 2),
('HCLTECH', 'HCL Technologies Ltd', 'NSE', 'IT', 'INE860A01027', 2),
('TITAN', 'Titan Company Ltd', 'NSE', 'Consumer', 'INE280A01028', 1),
('ADANIENT', 'Adani Enterprises Ltd', 'NSE', 'Conglomerate', 'INE423A01024', 1),
('SBI Life Insurance', 'SBI Life Insurance Co Ltd', 'NSE', 'Insurance', 'INE123W01016', 2),
('ONGC', 'Oil and Natural Gas Corporation Ltd', 'NSE', 'Energy', 'INE213A01029', 5),
('NTPC', 'NTPC Ltd', 'NSE', 'Energy', 'INE733E01010', 10),
('POWERGRID', 'Power Grid Corporation of India Ltd', 'NSE', 'Energy', 'INE752E01010', 10),
('TATASTEEL', 'Tata Steel Ltd', 'NSE', 'Metal', 'INE081A01020', 10),
('JSWSTEEL', 'JSW Steel Ltd', 'NSE', 'Metal', 'INE019A01038', 2),
('HINDALCO', 'Hindalco Industries Ltd', 'NSE', 'Metal', 'INE053A01015', 1),
('BAJAJFINSV', 'Bajaj Finserv Ltd', 'NSE', 'Finance', 'INE296A01016', 2),
('ULTRACEMCO', 'UltraTech Cement Ltd', 'NSE', 'Cement', 'INE481G01011', 10),
('NESTLEIND', 'Nestle India Ltd', 'NSE', 'FMCG', 'INE239A01016', 10),
('TECHM', 'Tech Mahindra Ltd', 'NSE', 'IT', 'INE669C01036', 5),
('DRREDDY', "Dr. Reddy's Laboratories Ltd", 'NSE', 'Pharma', 'INE089A01031', 5),
('CIPLA', 'Cipla Ltd', 'NSE', 'Pharma', 'INE059B01031', 2),
('EICHERMOT', 'Eicher Motors Ltd', 'NSE', 'Auto', 'INE066A01021', 10),
('COALINDIA', 'Coal India Ltd', 'NSE', 'Mining', 'INE522F01014', 10),
('GRASIM', 'Grasim Industries Ltd', 'NSE', 'Cement', 'INE047A01021', 10),
('DIVISLAB', "Divi's Laboratories Ltd", 'NSE', 'Pharma', 'INE361B01024', 2),
('BRITANNIA', 'Britannia Industries Ltd', 'NSE', 'FMCG', 'INE216A01030', 2),
('APOLLOHOSP', 'Apollo Hospitals Enterprise Ltd', 'NSE', 'Healthcare', 'INE086B01013', 5),
('HEROMOTOCO', 'Hero MotoCorp Ltd', 'NSE', 'Auto', 'INE158A01026', 2),
('BAJAJ-AUTO', 'Bajaj Auto Ltd', 'NSE', 'Auto', 'INE917I01010', 10),
('TATACONSUM', 'Tata Consumer Products Ltd', 'NSE', 'FMCG', 'INE192A01025', 1),
('INDUSINDBK', 'IndusInd Bank Ltd', 'NSE', 'Banking', 'INE096A01012', 10),
('HINDUNILVR', 'Hindustan Unilever Ltd', 'NSE', 'FMCG', 'INE030A01027', 1),
('SBILIFE', 'SBI Life Insurance Co Ltd', 'NSE', 'Insurance', 'INE123W01016', 2),
('BPCL', 'Bharat Petroleum Corporation Ltd', 'NSE', 'Energy', 'INE029A01011', 10),
('DIVISLAB', "Divi's Laboratories Ltd", 'NSE', 'Pharma', 'INE361B01024', 2),
('ADANIPORTS', 'Adani Ports and Special Economic Zone Ltd', 'NSE', 'Infrastructure', 'INE742F01042', 2),
('COFORGE', 'Coforge Ltd', 'NSE', 'IT', 'INE591G01017', 5),
('PERSISTENT', 'Persistent Systems Ltd', 'NSE', 'IT', 'INE262T01021', 5),
('MPHASIS', 'Mphasis Ltd', 'NSE', 'IT', 'INE256B01015', 1),
('LTIM', 'LTIMindtree Ltd', 'NSE', 'IT', 'INE214T01019', 1),
('WELCORP', 'Welspun Corp Ltd', 'NSE', 'Metal', 'INE191B01025', 5),
('INDHOTEL', 'The Indian Hotels Company Ltd', 'NSE', 'Hospitality', 'INE053A01029', 1),
('MANAPPURAM', 'Manappuram Finance Ltd', 'NSE', 'Finance', 'INE522D01032', 2),
('GODREJCP', 'Godrej Consumer Products Ltd', 'NSE', 'FMCG', 'INE532E01010', 1),
('NAVINFLUOR', 'Navin Fluorine International Ltd', 'NSE', 'Chemicals', 'INE04X001011', 2),
('DEEPAKNTR', 'Deepak Nitrite Ltd', 'NSE', 'Chemicals', 'INE288A01029', 2),
('IRCTC', 'Indian Railway Catering and Tourism Corporation Ltd', 'NSE', 'Services', 'INE335Y01019', 2),
('DMART', 'Avenue Supermarts Ltd', 'NSE', 'Retail', 'INE192R01029', 10),
('VOLTAS', 'Voltas Ltd', 'NSE', 'Consumer', 'INE226A01021', 1),
('DIXON', 'Dixon Technologies (India) Ltd', 'NSE', 'Electronics', 'INE935N01040', 2),
('SONACOMS', 'Sona BLW Precision Forgings Ltd', 'NSE', 'Auto', 'INE077X01017', 2),
('TATAPOWER', 'Tata Power Company Ltd', 'NSE', 'Energy', 'INE245A01021', 1),
('UBL', 'United Breweries Ltd', 'NSE', 'FMCG', 'INE685F01025', 2),
('COLPAL', 'Colgate-Palmolive (India) Ltd', 'NSE', 'FMCG', 'INE259A01022', 1),
('PIDILITIND', 'Pidilite Industries Ltd', 'NSE', 'Chemicals', 'INE318A01026', 1),
('PVRINOX', 'PVR INOX Ltd', 'NSE', 'Entertainment', 'INE179F01032', 10),
('STARHEALTH', 'Star Health and Allied Insurance Company Ltd', 'NSE', 'Insurance', 'INE575P01011', 10),
('NYKAA', 'FSN E-Commerce Ventures Ltd', 'NSE', 'E-Commerce', 'INE388Y01029', 2),
('POLICYBZR', 'PB Fintech Ltd', 'NSE', 'Fintech', 'INE418M01012', 2),
('ZOMATO', 'Zomato Ltd', 'NSE', 'Food Tech', 'INE619O01013', 1),
('PAYTM', 'One97 Communications Ltd', 'NSE', 'Fintech', 'INE982J01020', 1),
('DELHIVERY', 'Delhivery Ltd', 'NSE', 'Logistics', 'INE148O01029', 1),
('CLEAN', 'Clean Science and Technology Ltd', 'NSE', 'Chemicals', 'INE502W01014', 2),
('IIFL', 'IIFL Finance Ltd', 'NSE', 'Finance', 'INE530B01024', 2),
('MUTHOOTFIN', 'Muthoot Finance Ltd', 'NSE', 'Finance', 'INE414L01017', 10),
('CHOLAFIN', 'Cholamandalam Investment and Finance Company Ltd', 'NSE', 'Finance', 'INE121A01024', 2),
('RECLTD', 'REC Ltd', 'NSE', 'Finance', 'INE020B01018', 10),
('PFC', 'Power Finance Corporation Ltd', 'NSE', 'Finance', 'INE134E01011', 10),
('SAIL', 'Steel Authority of India Ltd', 'NSE', 'Metal', 'INE114A01011', 10),
('NMDC', 'NMDC Ltd', 'NSE', 'Mining', 'INE584A01023', 1),
('VEDL', 'Vedanta Ltd', 'NSE', 'Metal', 'INE205A01025', 1),
('HINDZINC', 'Hindustan Zinc Ltd', 'NSE', 'Metal', 'INE267A01025', 2),
('BHEL', 'Bharat Heavy Electricals Ltd', 'NSE', 'Capital Goods', 'INE257A01026', 2),
('BEL', 'Bharat Electronics Ltd', 'NSE', 'Defence', 'INE263A01024', 1),
('HAL', 'Hindustan Aeronautics Ltd', 'NSE', 'Defence', 'INE066F01015', 5),
('CUMMINSIND', 'Cummins India Ltd', 'NSE', 'Industrial', 'INE298A01020', 10),
('THERMAX', 'Thermax Ltd', 'NSE', 'Industrial', 'INE277A01015', 2),
('SIEMENS', 'Siemens Ltd', 'NSE', 'Industrial', 'INE007A01028', 2),
('ABB', 'ABB India Ltd', 'NSE', 'Industrial', 'INE277A01020', 2),
('BOSCHLTD', 'Bosch Ltd', 'NSE', 'Auto', 'INE325A01017', 10),
('MOTHERSON', 'Motherson Sumi Systems Ltd', 'NSE', 'Auto', 'INE775A01035', 1),
('M&M', 'Mahindra & Mahindra Ltd', 'NSE', 'Auto', 'INE101A01026', 5),
('TATACHEM', 'Tata Chemicals Ltd', 'NSE', 'Chemicals', 'INE092A01019', 10),
('ATUL', 'Atul Ltd', 'NSE', 'Chemicals', 'INE100A01018', 5),
('NAVINPHOR', 'Navin Fluorine International Ltd', 'NSE', 'Chemicals', 'INE04X001011', 2),
('LALPATHLAB', 'Dr. Lal PathLabs Ltd', 'NSE', 'Healthcare', 'INE600K01017', 2),
('METROPOLIS', 'Metropolis Healthcare Ltd', 'NSE', 'Healthcare', 'INE112L01018', 2),
('ZYDUSLIFE', 'Zydus Lifesciences Ltd', 'NSE', 'Pharma', 'INE010B01027', 1),
('ALKBEM', 'Alkem Laboratories Ltd', 'NSE', 'Pharma', 'INE950E01025', 2),
('AUROPHARMA', 'Aurobindo Pharma Ltd', 'NSE', 'Pharma', 'INE406B01022', 1),
('TORNTPHARM', 'Torrent Pharmaceuticals Ltd', 'NSE', 'Pharma', 'INE763C01016', 2),
('GLENMARK', 'Glenmark Pharmaceuticals Ltd', 'NSE', 'Pharma', 'INE935A01035', 1),
('IPCALAB', 'IPCA Laboratories Ltd', 'NSE', 'Pharma', 'INE562A01023', 2),
('Laurus Labs', 'Laurus Labs Ltd', 'NSE', 'Pharma', 'INE947D01028', 2),
('BIOCON', 'Biocon Ltd', 'NSE', 'Pharma', 'INE376G01013', 5),
('CONCOR', 'Container Corporation of India Ltd', 'NSE', 'Logistics', 'INE111A01025', 5),
('TVSMOTOR', 'TVS Motor Company Ltd', 'NSE', 'Auto', 'INE494B01023', 1),
('ASHOKLEY', 'Ashok Leyland Ltd', 'NSE', 'Auto', 'INE153A01029', 1),
('MAHINDRA', 'M&M Financial Services Ltd', 'NSE', 'Finance', 'INE774D01024', 2),
('FEDERALBNK', 'Federal Bank Ltd', 'NSE', 'Banking', 'INE457A01016', 2),
('IDFCFIRSTB', 'IDFC First Bank Ltd', 'NSE', 'Banking', 'INE095T01012', 10),
('BANDHANBNK', 'Bandhan Bank Ltd', 'NSE', 'Banking', 'INE545L01036', 2),
('PNB', 'Punjab National Bank', 'NSE', 'Banking', 'INE160A01014', 2),
('CANBK', 'Bank of Baroda', 'NSE', 'Banking', 'INE028A01039', 2),
('UNIONBANK', 'Union Bank of India', 'NSE', 'Banking', 'INE692A01016', 10),
('INDIANB', 'Indian Bank', 'NSE', 'Banking', 'INE562A01011', 10),
('BANKINDIA', 'Bank of India', 'NSE', 'Banking', 'INE084A01016', 10),
('CENTRALBK', 'Central Bank of India', 'NSE', 'Banking', 'INE483A01014', 10),
('UCO', 'UCO Bank', 'NSE', 'Banking', 'INE090A01012', 10),
('PSB', 'Punjab & Sind Bank', 'NSE', 'Banking', 'INE028A01013', 10),
('MAHABANK', 'Bank of Maharashtra', 'NSE', 'Banking', 'INE457A01014', 10),
('J&KBANK', 'Jammu & Kashmir Bank', 'NSE', 'Banking', 'INE168A01041', 2),
('KARURVYSYA', 'Karur Vysya Bank Ltd', 'NSE', 'Banking', 'INE036D01028', 2),
('CITYUNION', 'City Union Bank Ltd', 'NSE', 'Banking', 'INE048D01033', 1),
('DCBBANK', 'DCB Bank Ltd', 'NSE', 'Banking', 'INE503A01015', 10),
('EQUITAS', 'Equitas Small Finance Bank Ltd', 'NSE', 'Banking', 'INE063P01014', 2),
('KVB', 'Karur Vysya Bank Ltd', 'NSE', 'Banking', 'INE036D01028', 2),
('FUSION', 'Fusion Micro Finance Ltd', 'NSE', 'Finance', 'INE573N01015', 2),
('CDSL', 'Central Depository Services (India) Ltd', 'NSE', 'Finance', 'INE736A01011', 2),
('CAMS', 'Computer Age Management Services Ltd', 'NSE', 'Finance', 'INE596I01014', 10),
('ANGELONE', 'Angel One Ltd', 'NSE', 'Finance', 'INE007A01034', 10),
('BSE', 'BSE Ltd', 'NSE', 'Finance', 'INE118H01014', 2),
('MCX', 'Multi Commodity Exchange of India Ltd', 'NSE', 'Finance', 'INE745B01024', 10),
('JUBLFOOD', 'Jubilant FoodWorks Ltd', 'NSE', 'FMCG', 'INE797F01012', 2),
('TATACOFFEE', 'Tata Coffee Ltd', 'NSE', 'FMCG', 'INE493A01023', 1),
('EMAMILTD', 'Emami Ltd', 'NSE', 'FMCG', 'INE548L01031', 2),
('MARICO', 'Marico Ltd', 'NSE', 'FMCG', 'INE196A01026', 1),
('DABUR', 'Dabur India Ltd', 'NSE', 'FMCG', 'INE016A01026', 1),
('BERGEPAINT', 'Berger Paints India Ltd', 'NSE', 'Consumer', 'INE463A01014', 1),
('INDIGO', 'InterGlobe Aviation Ltd', 'NSE', 'Airlines', 'INE649C01014', 10),
('IRB', 'IRB Infrastructure Developers Ltd', 'NSE', 'Infrastructure', 'INE356I01014', 2),
('ADANIGREEN', 'Adani Green Energy Ltd', 'NSE', 'Energy', 'INE362Z01016', 10),
('ADANIENSOL', 'Adani Energy Solutions Ltd', 'NSE', 'Energy', 'INE423A01032', 10),
('LTIM', 'LTIMindtree Ltd', 'NSE', 'IT', 'INE214T01019', 1),
('ROUTE', 'Route Mobile Ltd', 'NSE', 'IT', 'INE450U01016', 2),
('HAPPSTMNDS', 'Happiest Minds Technologies Ltd', 'NSE', 'IT', 'INE419U01014', 2),
('SONATSOFTW', 'Sonata Software Ltd', 'NSE', 'IT', 'INE269B01021', 2),
('OFSS', 'Oracle Financial Services Software Ltd', 'NSE', 'IT', 'INE881D01027', 10),
('TANLA', 'Tanla Platforms Ltd', 'NSE', 'IT', 'INE483C01035', 2),
('ZENSAR', 'Zensar Technologies Ltd', 'NSE', 'IT', 'INE450B01017', 2),
('KPITTECH', 'KPIT Technologies Ltd', 'NSE', 'IT', 'INE04I201020', 2),
('BSOFT', 'Birlasoft Ltd', 'NSE', 'IT', 'INE836A01035', 2),
('HEG', 'HEG Ltd', 'NSE', 'Industrial', 'INE545A01024', 10),
('GRAPHITE', 'Graphite India Ltd', 'NSE', 'Industrial', 'INE387C01029', 2),
('RATNAMANI', 'Ratnamani Metals & Tubes Ltd', 'NSE', 'Metal', 'INE729C01039', 2),
('APLAPOLLO', 'APL Apollo Tubes Ltd', 'NSE', 'Metal', 'INE372N01015', 2),
('NATIONALUM', 'National Aluminium Company Ltd', 'NSE', 'Metal', 'INE139A01034', 5),
('HINDCOPPER', 'Hindustan Copper Ltd', 'NSE', 'Metal', 'INE531E01024', 2),
('NLCINDIA', 'NLC India Ltd', 'NSE', 'Energy', 'INE589A01014', 10),
('NHPC', 'NHPC Ltd', 'NSE', 'Energy', 'INE848A01016', 10),
('SJVN', 'SJVN Ltd', 'NSE', 'Energy', 'INE002L01015', 10),
('TATAELXSI', 'Tata Elxsi Ltd', 'NSE', 'IT', 'INE670A01013', 2),
('MAPMYINDIA', 'C.E. Info Systems Ltd', 'NSE', 'IT', 'INE0BR201019', 2),
('KPIT', 'KPIT Technologies Ltd', 'NSE', 'IT', 'INE04I201020', 2),
('PERSISTENT', 'Persistent Systems Ltd', 'NSE', 'IT', 'INE262T01021', 5),
('COFORGE', 'Coforge Ltd', 'NSE', 'IT', 'INE591G01017', 5),
('Mphasis', 'Mphasis Ltd', 'NSE', 'IT', 'INE256B01015', 1),
('CYIENT', 'Cyient Ltd', 'NSE', 'IT', 'INE321B01018', 5),
('HEMIPROP', 'Hemisphere Properties India Ltd', 'NSE', 'Real Estate', 'INE0AJ201016', 10),
('OBEROIRLTY', 'Oberoi Realty Ltd', 'NSE', 'Real Estate', 'INE093I01010', 2),
('GODREJPROP', 'Godrej Properties Ltd', 'NSE', 'Real Estate', 'INE660C01035', 5),
('PRESTIGE', 'Prestige Estates Projects Ltd', 'NSE', 'Real Estate', 'INE811K01018', 2),
('BRIGADE', 'Brigade Enterprises Ltd', 'NSE', 'Real Estate', 'INE289I01019', 2),
('SOBHA', 'Sobha Ltd', 'NSE', 'Real Estate', 'INE663O01015', 10),
('PHOENIXLTD', 'The Phoenix Mills Ltd', 'NSE', 'Real Estate', 'INE211D01039', 2),
('LODHA', 'Macrotech Developers Ltd', 'NSE', 'Real Estate', 'INE674I01010', 2)
ON CONFLICT (symbol) DO NOTHING;

-- Seed initial stock prices
INSERT INTO market_schema.stock_prices (id, symbol, current_price, open, high, low, close, volume, week_high_52, week_low_52, change, change_percent)
SELECT uuid_generate_v4(), s.symbol,
    CASE s.symbol
        WHEN 'RELIANCE' THEN 2450.50
        WHEN 'TCS' THEN 3890.75
        WHEN 'INFY' THEN 1520.30
        WHEN 'HDFCBANK' THEN 1680.40
        WHEN 'ICICIBANK' THEN 1120.60
        WHEN 'WIPRO' THEN 445.80
        WHEN 'SBIN' THEN 625.90
        WHEN 'ITC' THEN 465.20
        WHEN 'BHARTIARTL' THEN 1285.30
        WHEN 'KOTAKBANK' THEN 1785.40
        ELSE ROUND((random() * 2000 + 100)::numeric, 2)
    END,
    ROUND((random() * 100 + 2000)::numeric, 2),
    ROUND((random() * 50 + 2100)::numeric, 2),
    ROUND((random() * 50 + 1900)::numeric, 2),
    ROUND((random() * 100 + 2000)::numeric, 2),
    (random() * 10000000 + 500000)::bigint,
    ROUND((random() * 500 + 2500)::numeric, 2),
    ROUND((random() * 500 + 1000)::numeric, 2),
    ROUND((random() * 50 - 25)::numeric, 2),
    ROUND((random() * 5 - 2.5)::numeric, 4)
FROM market_schema.stocks s
ON CONFLICT DO NOTHING;

-- Seed market indices
INSERT INTO market_schema.indices (id, name, value, change, change_percent) VALUES
(uuid_generate_v4(), 'NIFTY 50', 22456.80, 125.30, 0.56),
(uuid_generate_v4(), 'SENSEX', 73852.45, 410.25, 0.56),
(uuid_generate_v4(), 'NIFTY BANK', 48125.60, -85.40, -0.18);

-- Seed IPOs
INSERT INTO ipo_schema.ipos (id, company_name, symbol, price_min, price_max, lot_size, open_date, close_date, listing_date, status, gmp, subscription_times) VALUES
(uuid_generate_v4(), 'Tata Technologies Ltd', 'TATATECH', 475.00, 500.00, 30, '2024-11-22', '2024-11-26', '2024-12-05', 'LISTED', 350.00, 73.50),
(uuid_generate_v4(), 'IIFL Securities Ltd', 'IIFLSEC', 250.00, 275.00, 40, '2024-12-10', '2024-12-12', '2024-12-18', 'UPCOMING', 45.00, 0),
(uuid_generate_v4(), 'Zomato Ltd IPO', 'ZOMATO', 72.00, 76.00, 195, '2024-07-14', '2024-07-16', '2024-07-23', 'LISTED', 21.00, 38.00),
(uuid_generate_v4(), 'Clean Science & Technology', 'CLEANSCIENCE', 880.00, 900.00, 16, '2024-07-07', '2024-07-09', '2024-07-19', 'LISTED', 280.00, 23.60),
(uuid_generate_v4(), 'Mobikwik Systems Ltd', 'MOBIKWIK', 265.00, 279.00, 53, '2024-12-15', '2024-12-17', '2024-12-23', 'UPCOMING', 125.00, 0),
(uuid_generate_v4(), 'HDB Financial Services Ltd', 'HDBFIN', 750.00, 800.00, 18, '2024-12-20', '2024-12-22', '2024-12-30', 'UPCOMING', 95.00, 0);

-- Seed mutual funds
INSERT INTO mf_schema.mutual_funds (id, scheme_code, scheme_name, fund_house, category, sub_category, nav, day_change, risk_level, exit_load, expense_ratio) VALUES
(uuid_generate_v4(), '120716', 'Axis Bluechip Fund - Direct Growth', 'Axis Mutual Fund', 'Equity', 'Large Cap', 58.4215, 0.3245, 'MODERATE', '1% within 12 months', 0.52),
(uuid_generate_v4(), '118989', 'HDFC Mid-Cap Opportunities Fund - Direct Growth', 'HDFC Mutual Fund', 'Equity', 'Mid Cap', 92.1542, -0.1520, 'HIGH', '1% within 36 months', 0.78),
(uuid_generate_v4(), '120503', 'Mirae Asset Emerging Bluechip Fund - Direct Growth', 'Mirae Asset Mutual Fund', 'Equity', 'Large & Mid Cap', 89.3210, 0.5420, 'HIGH', '1% within 36 months', 0.65),
(uuid_generate_v4(), '119067', 'Parag Parikh Flexi Cap Fund - Direct Growth', 'PPFAS Mutual Fund', 'Equity', 'Flexi Cap', 62.7845, 0.1230, 'MODERATE', 'Nil', 0.63),
(uuid_generate_v4(), '118585', 'SBI Small Cap Fund - Direct Growth', 'SBI Mutual Fund', 'Equity', 'Small Cap', 118.5420, -0.8540, 'VERY HIGH', '1% within 36 months', 0.72),
(uuid_generate_v4(), '120375', 'UTI Nifty Index Fund - Direct Growth', 'UTI Mutual Fund', 'Equity', 'Index Fund', 245.3210, 1.2540, 'MODERATE', 'Nil', 0.10),
(uuid_generate_v4(), '118585', 'Nippon India Small Cap Fund - Direct Growth', 'Nippon India Mutual Fund', 'Equity', 'Small Cap', 145.8740, -1.2350, 'VERY HIGH', '1% within 36 months', 0.68),
(uuid_generate_v4(), '120716', 'Quant Tax Plan - Direct Growth', 'Quant Mutual Fund', 'ELSS', 'Tax Saving', 185.2450, 0.9850, 'HIGH', '3 years lock-in', 0.56),
(uuid_generate_v4(), '119067', 'HDFC Corporate Bond Fund - Direct Growth', 'HDFC Mutual Fund', 'Debt', 'Corporate Bond', 28.6540, 0.0120, 'LOW', 'Nil', 0.32),
(uuid_generate_v4(), '120503', 'ICICI Prudential Equity & Debt Fund - Direct Growth', 'ICICI Prudential', 'Hybrid', 'Aggressive Hybrid', 142.5800, 0.4520, 'MODERATE', '1% within 12 months', 0.82),
(uuid_generate_v4(), '118989', 'Aditya Birla Sun Life Tax Relief 96 Fund - Direct Growth', 'Aditya Birla SL', 'ELSS', 'Tax Saving', 78.9520, 0.3210, 'HIGH', '3 years lock-in', 0.62),
(uuid_generate_v4(), '120716', 'Motilal Oswal Nasdaq 100 ETF Fund - Direct Growth', 'Motilal Oswal', 'International', 'US Equity', 24.8520, 0.1450, 'HIGH', '1% within 12 months', 0.55),
(uuid_generate_v4(), '118585', 'Kotak Emerging Equity Fund - Direct Growth', 'Kotak Mutual Fund', 'Equity', 'Mid Cap', 68.4250, -0.2850, 'HIGH', '1% within 36 months', 0.45),
(uuid_generate_v4(), '119067', 'SBI Conservative Hybrid Fund - Direct Growth', 'SBI Mutual Fund', 'Hybrid', 'Conservative Hybrid', 52.3640, 0.0850, 'MODERATE', '1% within 12 months', 0.42),
(uuid_generate_v4(), '120503', 'Axis Growth Opportunities Fund - Direct Growth', 'Axis Mutual Fund', 'Equity', 'Large & Mid Cap', 28.7540, 0.1520, 'HIGH', '1% within 36 months', 0.58);
