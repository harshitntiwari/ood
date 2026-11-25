import { PaymentStatus } from "../enums/PaymentStatus.js";
import { PaymentType } from "../enums/PaymentType.js";
import { Payment } from "../model/Payment.js";
import { PaymentStrategy } from "./PaymentStrategy.js";

export class CardPaymentStrategy implements PaymentStrategy {
    pay(amount: number): Payment {
        console.log(`Processing payment of amount: ${amount} ...`);

        const payment = new Payment(amount, PaymentType.CARD);

        const paymentSuccess = Math.random() > 0.05; // simulating 95% success

        if (paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            console.log("Payment completed successfully.");
        } else {
            payment.setStatus(PaymentStatus.FAILURE);
            console.log("Payment failed. Please try again.");
        }

        return payment;
    }
}
