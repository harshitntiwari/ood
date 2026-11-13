package NotificationSystem.observable;

import NotificationSystem.observer.NotificationObserver;
import java.util.ArrayList;
import java.util.List;

// class that tracks the stock of a particular product (Macbook in this case) and notifies the subscribers on stock update
public class MacbookStock implements StockObservable {

    private List<NotificationObserver> subscribers = new ArrayList<>();
    private int stockCount = 0;

    @Override
    public void subscribe(NotificationObserver observer) {
        subscribers.add(observer);
    }

    @Override
    public void unsubscribe(NotificationObserver observer) {
        subscribers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
        for (NotificationObserver subscriber : subscribers) {
            // updating all the subscribers
            subscriber.update();
        }
    }

    @Override
    public void setStockCount(int newStockCount) {
        if (stockCount == 0) {
            // notify the subscribers on stock change
            notifySubscribers();
        }
        stockCount += newStockCount;
    }

    @Override
    public int getStockCount() {
        return stockCount;
    }
}
