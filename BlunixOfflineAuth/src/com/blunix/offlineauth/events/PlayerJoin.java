package com.blunix.offlineauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blunix.offlineauth.OfflineAuth;

public class PlayerJoin implements Listener {
	private OfflineAuth plugin;

	public PlayerJoin(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPlayedBefore()) {
			
		
		} else {
			
		}
	}
}
