package com.mc1510ty.RedisBroadcastChat;

import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.*;

public class RedisGroupChat extends JavaPlugin {

    private JedisPool jedisPool;
    private RedisSubscriber subscriber;
    private String chatGroup;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.chatGroup = getConfig().getString("chat-group");
        String host = getConfig().getString("redis.host");
        int port = getConfig().getInt("redis.port");
        String password = getConfig().getString("redis.password");

        HostAndPort redisHost = new HostAndPort(host, port);

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .password(password.isEmpty() ? null : password)
                .build();

        jedisPool = new JedisPool(redisHost, config);

        // Redis購読スレッド開始
        subscriber = new RedisSubscriber(this, chatGroup, jedisPool);
        new Thread(subscriber).start();

        getServer().getPluginManager().registerEvents(new RedisChatListener(this), this);
        getLogger().info("Redis Group Chat enabled for group: " + chatGroup);
    }


    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public String getChatGroup() {
        return chatGroup;
    }

    @Override
    public void onDisable() {
        if (subscriber != null) subscriber.unsubscribe();
        if (jedisPool != null) jedisPool.close();
    }
}