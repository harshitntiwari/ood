package MovieTicketBookingSystem.model;

import MovieTicketBookingSystem.enums.PaymentStatus;
import MovieTicketBookingSystem.enums.PaymentType;

public class Payment {
    private Double amount;
    private PaymentStatus status;
    private PaymentType type;

    public Payment(Double amount, PaymentType type) {
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
        this.type = type;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}