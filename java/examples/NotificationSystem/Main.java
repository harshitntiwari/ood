package NotificationSystem;

import NotificationSystem.observable.MacbookStock;
import NotificationSystem.observable.StockObservable;
import NotificationSystem.observer.MobileNotification;
import NotificationSystem.observer.NotificationObserver;

public class Main {

    public static void main(String args[]) {
        StockObservable macbookObservable = new MacbookStock();

        NotificationObserver user1 = new MobileNotification(
            "user1",
            macbookObservable
        );
        NotificationObserver user2 = new MobileNotification(
            "user2",
            macbookObservable
        );

        macbookObservable.subscribe(user1);
        macbookObservable.subscribe(user2);

        macbookObservable.setStockCount(10);
    }
}
