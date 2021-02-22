package com.blunix.offlineauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.blunix.offlineauth.BlunixOfflineAuth;

public class PlayerRespawn implements Listener {
	private BlunixOfflineAuth plugin;

	public PlayerRespawn(BlunixOfflineAuth plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getLoginPlayers().containsKey(player.getUniqueId()))
			return;
		
		event.setRespawnLocation(plugin.getLoginLocation());
	}
}
