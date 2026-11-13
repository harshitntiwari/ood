package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;
import PaymentSystem.interfaces.PaymentType;

// Here Factory pattern is demonstrated.
// Factory class's only responsibility is to create and return objects. It does not store any state of any objects
public class PaymentFactory {

    // Static method, so that we can call it directly like: PaymentFactory.getPaymentMethod()
    // because PaymentFactory is just a utility, so we need not create it's object.
    // each call to getPaymentMethod() will create and return a new object
    public static PaymentMethod getPaymentMethod(PaymentType method) {
        switch (method) {
            case UPI:
                return new UPIPayment();
            case CARD:
                return new CreditCardPayment();
            case WALLET:
                return new WalletPayment();
            default:
                throw new IllegalArgumentException("Invalid payment method");
        }
    }

    // one can argue that this implementation of factory violates open closed principle (OCP) - addition of more
    // payment methods will lead to modification of the getPaymentMethod() method.
    // This is acceptable as factories are creation points
    // OCP primarily applies to business logic (PaymentService) and not factories
}
