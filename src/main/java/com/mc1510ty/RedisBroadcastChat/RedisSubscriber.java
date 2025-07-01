package com.mc1510ty.RedisBroadcastChat;

import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber extends JedisPubSub implements Runnable {


    private final RedisGroupChat plugin;
    private final String channel;
    private final JedisPool jedisPool;

    public RedisSubscriber(RedisGroupChat plugin, String group, JedisPool pool) {
        this.plugin = plugin;
        this.channel = "chat:" + group;
        this.jedisPool = pool;
    }

    @Override
    public void run() {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.subscribe(this, channel);
        }
    }

    @Override
    public void onMessage(String channel, String message) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Bukkit.broadcastMessage(message);
        });
    }
}