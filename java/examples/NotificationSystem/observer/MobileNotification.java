package NotificationSystem.observer;

import NotificationSystem.observable.StockObservable;

public class MobileNotification implements NotificationObserver {

    private StockObservable stockObservable;
    private String userName;

    public MobileNotification(
        String userName,
        StockObservable stockObservable
    ) {
        this.stockObservable = stockObservable;
        this.userName = userName;
    }

    @Override
    public void update() {
        // do some business logic
        int stockCount = stockObservable.getStockCount();

        // send alert
        sendAlert("product is in stock, hurry up!");
    }

    private void sendAlert(String msg) {
        // logic to send alert
        System.out.println("Message sent to " + userName + " on mobile!");
    }
}
