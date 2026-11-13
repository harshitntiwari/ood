package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

public class CreditCardPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println(
            "Credit card Payment of " + amount + " is successful"
        );
    }
}
