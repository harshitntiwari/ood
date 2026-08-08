package MovieTicketBookingSystem.factory;

import MovieTicketBookingSystem.enums.PaymentType;
import MovieTicketBookingSystem.strategies.CardPaymentStrategy;
import MovieTicketBookingSystem.strategies.PaymentStrategy;
import MovieTicketBookingSystem.strategies.UPIPaymentStrategy;

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