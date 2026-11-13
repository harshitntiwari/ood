package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

public class UPIPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("UPI Payment of " + amount + " is successful");
    }
}
