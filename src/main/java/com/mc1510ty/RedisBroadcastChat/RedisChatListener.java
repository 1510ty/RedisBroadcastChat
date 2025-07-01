package com.mc1510ty.RedisBroadcastChat;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import redis.clients.jedis.Jedis;

public class RedisChatListener implements Listener {

    private final RedisGroupChat plugin;

    public RedisChatListener(RedisGroupChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        String msg = "[" + plugin.getChatGroup() + "] " + event.getPlayer().getName() + ": " + event.getMessage();

        try (Jedis jedis = plugin.getJedisPool().getResource()) {
            jedis.publish("chat:" + plugin.getChatGroup(), msg);
        }
    }
}