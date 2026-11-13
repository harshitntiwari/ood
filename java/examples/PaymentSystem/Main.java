package PaymentSystem;

import PaymentSystem.interfaces.PaymentMethod;
import PaymentSystem.interfaces.PaymentType;

public class Main {

    public static void main(String[] args) {
        PaymentMethod method1 = PaymentFactory.getPaymentMethod(
            PaymentType.UPI
        );
        PaymentMethod method2 = PaymentFactory.getPaymentMethod(
            PaymentType.CARD
        );
        PaymentMethod method3 = PaymentFactory.getPaymentMethod(
            PaymentType.WALLET
        );

        // using retry decorator and logging decorator - demonstrating SRP, each class has just one responsibility
        PaymentMethod method4 = new RetryPaymentDecorator(
            new LoggingDecorator(
                PaymentFactory.getPaymentMethod(PaymentType.UPI)
            )
        );

        PaymentService service1 = new PaymentService(method1);
        PaymentService service2 = new PaymentService(method2);
        PaymentService service3 = new PaymentService(method3);
        PaymentService service4 = new PaymentService(method4);

        service1.pay(10.12);
        service2.pay(100.34);
        service3.pay(1234);
        service4.pay(789.09);
    }
}
