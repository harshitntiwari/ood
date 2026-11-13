package MovieTicketBookingSystem.strategies;

import MovieTicketBookingSystem.enums.PaymentStatus;
import MovieTicketBookingSystem.enums.PaymentType;
import MovieTicketBookingSystem.model.Payment;

public class UPIPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment pay(Double amount) {
        System.out.println("Processing payment of amount: " + amount + " ...");

        Payment payment = new Payment(amount, PaymentType.UPI);

        boolean paymentSuccess = Math.random() > 0.05; // simulating 95% success;

        if(paymentSuccess) {
            payment.setStatus(PaymentStatus.SUCCESS);
            System.out.println("Payment completed successfully.");
        } else {
            payment.setStatus(PaymentStatus.FAILURE);
            System.out.println("Payment failed. Please try again.");
        }

        return payment;
    }
}