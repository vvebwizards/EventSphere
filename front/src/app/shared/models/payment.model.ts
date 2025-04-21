export interface Payment {
  payer: string;
  amount: number;
  method: string;
}

export interface CreditCardInfo {
  cardNumber: string;
  cardholderName: string;
  expiryDate: string;
  cvv: string;
}