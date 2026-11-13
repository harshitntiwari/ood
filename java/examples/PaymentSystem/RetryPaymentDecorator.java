package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

public class RetryPaymentDecorator implements PaymentMethod {

    private PaymentMethod wrapped;
    private int retries;

    RetryPaymentDecorator(PaymentMethod paymentMethod) {
        this.wrapped = paymentMethod;
        this.retries = 3;
    }

    @Override
    public void pay(double amount) {
        int retries = this.retries;
        while (retries > 0) {
            try {
                wrapped.pay(amount);
                return;
            } catch (Exception e) {
                retries--;
                if (retries == 0) throw e;
            }
        }
    }
}
