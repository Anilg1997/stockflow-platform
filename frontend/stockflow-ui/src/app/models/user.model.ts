export interface User {
  id: string;
  email: string;
  name: string;
  avatar?: string;
  phone?: string;
  panCard?: string;
  kycStatus: 'PENDING' | 'VERIFIED' | 'REJECTED';
  createdAt: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  name: string;
  phone?: string;
}

export interface AuthResponse {
  token: string;
  refreshToken: string;
  user: User;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}
