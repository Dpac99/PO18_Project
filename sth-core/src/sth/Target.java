package sth;

public interface Target {
    public void registerObserver(Observer o);
    public void removeObserver(Observer o);
    public void notifyObservers(String notif);
}