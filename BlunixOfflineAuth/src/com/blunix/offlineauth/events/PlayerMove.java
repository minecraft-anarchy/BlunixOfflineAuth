package com.blunix.offlineauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.blunix.offlineauth.OfflineAuth;

public class PlayerMove implements Listener {
	private OfflineAuth plugin;

	public PlayerMove(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!plugin.getLoginPlayers().containsKey(event.getPlayer()))
			return;
		
		event.setCancelled(true);
	}
}
