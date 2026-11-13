import { Payment } from "../model/Payment.js";

export interface PaymentStrategy {
    pay(amount: number): Payment;
}
