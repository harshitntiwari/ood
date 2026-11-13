package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;

// Open-close principle is demostrated here: we can add more payment methods without modifying PaymentService
// "Open for extension but closed for modification"

public class PaymentService {

    // this demostrates Composition (has-a relationship): PaymentService has a paymentMethod
    // PaymentService does not inherit (extend) any particular payment class.
    // the payment behaviour is delegated instead of being inherited
    private PaymentMethod paymentMethod;

    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void pay(double amount) {
        // abstraction is happening right here: PaymentService does not know which payment method is being used
        // or what is the implementation behind paymentMethod.pay()
        // this is also runtime polymorphism: which implementation of pay() will be used is decided at
        // runtime depending on the type of paymentMethod passed to PaymentService()
        // in a nutshell:  this is runtime polymorphism enabled through abstraction
        this.paymentMethod.pay(amount);
    }
}
