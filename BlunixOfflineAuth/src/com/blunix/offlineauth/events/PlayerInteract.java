package com.blunix.offlineauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.blunix.offlineauth.OfflineAuth;

public class PlayerInteract implements Listener {
	private OfflineAuth plugin;

	public PlayerInteract(OfflineAuth plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (!plugin.getLoginPlayers().containsKey(event.getPlayer()))
			return;
		
		event.setCancelled(true);
	}
}
