package _temp._spring._injection.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;

/**
 * Created by fishsey on 2017/9/28.
 */
@Component
public class CachedMap
{
    HashMap<Integer, Integer> cached = new HashMap<>();
    ConcurrentHashMap<Integer, Condition> unCached = new ConcurrentHashMap<>();
    //HashMap<Integer, Condition> unCached = new HashMap<>();

}
