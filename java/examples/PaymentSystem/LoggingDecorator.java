package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

// We create this logging class because we don't want to mix Logging logic with PaymentService class.
// That would violate Single Responsibility priciple.
public class LoggingDecorator implements PaymentMethod {

    private PaymentMethod wrapped;

    LoggingDecorator(PaymentMethod paymentMethod) {
        this.wrapped = paymentMethod;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Starting payment...");
        wrapped.pay(amount);
        System.out.println("Payment completed");
    }
}
