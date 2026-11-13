package NotificationSystem.observable;

import NotificationSystem.observer.NotificationObserver;

public interface StockObservable {
    public void subscribe(NotificationObserver observer);
    public void unsubscribe(NotificationObserver observer);
    public void notifySubscribers();
    public void setStockCount(int newStockCount);
    public int getStockCount();
}
