package com.example.task;

import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Component
public class SchedulerTask {
  private HazelcastInstance instance = Hazelcast.newHazelcastInstance();
  private Map<String, Integer> cache = instance.getMap("cache");
  private int count = 1;

  @Scheduled(cron = "*/5 * * * * ?")
  private void process() {
    Lock lock = instance.getLock("locktest");
    try {
      lock.lock();
      if (cache.get("count") != null) {
        System.out.println("[Scheduled Task 1] from cache count is:" + cache.get("count"));
      } else {
        cache.put("count", count);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    System.out.println("[Scheduled Task 1] actual count is:" + count++);

  }

}
