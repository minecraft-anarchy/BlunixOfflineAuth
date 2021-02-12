package com.blunix.offlineauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.blunix.offlineauth.OfflineAuth;

public class PlayerChat implements Listener {
	private OfflineAuth plugin;

	public PlayerChat(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getLoginPlayers().containsKey(player))
			return;
		
		event.setCancelled(true);
	}
}
