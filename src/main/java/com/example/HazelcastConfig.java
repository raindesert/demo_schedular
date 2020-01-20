package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;

@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelCastConfig(){
        Config config = new Config();
        config.setInstanceName("hazelcast-instance").addMapConfig(
                new MapConfig().setName("configuration").setMaxSizeConfig(new MaxSizeConfig(200,
                        MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE)).setEvictionPolicy(EvictionPolicy.LFU)
                .setTimeToLiveSeconds(-1));
        return config;
    }
}