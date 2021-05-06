package casper.statisticsproject.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Map;

public class BlackjackPlayer {
    Multimap<Card, CardType> storage;
    String name;

    public BlackjackPlayer(String name){
        this.name = name;
        this.storage = ArrayListMultimap.create();
        storage.putAll(Utils.getRandomCard());
        storage.putAll(Utils.getRandomCard());
    }

    public Multimap<Card, CardType> getStorage() {
        return storage;
    }

    public void setStorage(Multimap<Card, CardType> storage) {
        this.storage = storage;
    }

    public String getName() {
        return name;
    }
}
