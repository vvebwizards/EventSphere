// src/app/models/partner.ts
export interface Partner {
    id?: number;
    name: string;
    email: string;
    phoneNumber: string;
    address: string;
    website: string;
    status: 'ACTIVE' | 'INACTIVE';
  }
  