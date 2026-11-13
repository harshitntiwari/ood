package MovieTicketBookingSystem.strategies;

import MovieTicketBookingSystem.model.Payment;

public interface PaymentStrategy {
    Payment pay(Double amount);
}