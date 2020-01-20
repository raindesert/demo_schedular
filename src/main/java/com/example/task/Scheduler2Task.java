package com.example.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;

@Component
public class Scheduler2Task {
  private HazelcastInstance instance = Hazelcast.newHazelcastInstance();
  private Map<String, Integer> cache = instance.getMap("cache");
  private int count = 1;
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    System.out.println("[Scheduled Task 2] Now:" + dateFormat.format(new Date()));
    Lock lock = instance.getLock("locktest");
    try {
      lock.lock();
      if (cache.get("count") != null) {
        System.out.println("[Scheduled Task 2] from cache count is:" + cache.get("count"));
      } else {
        cache.put("count", count);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    System.out.println("[Scheduled Task 2] actual count is:" + count++);
  }

}
