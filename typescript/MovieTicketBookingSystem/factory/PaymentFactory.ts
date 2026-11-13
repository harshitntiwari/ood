import { PaymentType } from "../enums/PaymentType.js";
import { CardPaymentStrategy } from "../strategies/CardPaymentStrategy.js";
import { PaymentStrategy } from "../strategies/PaymentStrategy.js";
import { UPIPaymentStrategy } from "../strategies/UPIPaymentStrategy.js";

export class PaymentFactory {
    static getPaymentStrategy(paymentType: PaymentType): PaymentStrategy {
        switch (paymentType) {
            case PaymentType.UPI:
                return new UPIPaymentStrategy();
            case PaymentType.CARD:
                return new CardPaymentStrategy();
            default: {
                const exhaustiveCheck: never = paymentType;
                throw new Error(`Invalid payment type: ${exhaustiveCheck}`);
            }
        }
    }
}
