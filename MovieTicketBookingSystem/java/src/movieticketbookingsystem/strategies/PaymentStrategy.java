package movieticketbookingsystem.strategies;

import movieticketbookingsystem.model.Payment;

public interface PaymentStrategy {
    Payment pay(Double amount);
}