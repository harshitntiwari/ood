package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

public class WalletPayment implements PaymentMethod {

    @Override
    public void pay(double amount) {
        System.out.println("Wallet Payment of " + amount + " is successful");
    }
}
