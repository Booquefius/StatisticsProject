package casper.statisticsproject.objects;

import casper.statisticsproject.utils.Utils;

import java.util.HashMap;
import java.util.List;

public class BlackjackPlayer {
    HashMap<Integer, List<PlayingCard>> storage;
    String name;
    long balance;

    public BlackjackPlayer(String name){
        this.name = name;
        this.balance = 1000;
        this.storage = new HashMap<>();
        storage.put(0, List.of(Utils.getRandomCard(), Utils.getRandomCard()));
    }

    public long getBalance() {
        return balance;
    }
    public void addBalance(long balance){
        this.balance = getBalance() + balance;
    }

    public HashMap<Integer, List<PlayingCard>> getStorage() {
        return storage;
    }

    public void setStorage(int set, int place, PlayingCard storage) {
        this.storage.get(set).set(place, storage);
    }

    public PlayingCard getFirstCard(){
        return this.storage.get(0).get(0);
    }

    public String getName() {
        return name;
    }
}
