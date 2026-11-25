package movieticketbookingsystem.factory;

import movieticketbookingsystem.enums.PaymentType;
import movieticketbookingsystem.strategies.CardPaymentStrategy;
import movieticketbookingsystem.strategies.PaymentStrategy;
import movieticketbookingsystem.strategies.UPIPaymentStrategy;

public class PaymentFactory {
    public static PaymentStrategy getPaymentStrategy(PaymentType paymentType) {
        switch(paymentType) {
            case UPI -> {
                return new UPIPaymentStrategy();
            }
            case CARD -> {
                return new CardPaymentStrategy();
            }
            default -> throw new IllegalArgumentException("Invalid payment type");
        }
    }
}