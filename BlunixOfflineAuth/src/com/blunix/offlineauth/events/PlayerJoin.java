package com.blunix.offlineauth.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.util.ConfigManager;
import com.blunix.offlineauth.util.Messager;

public class PlayerJoin implements Listener {
	private OfflineAuth plugin;
	private ConfigManager config;

	public PlayerJoin(OfflineAuth plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfigManager();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String message = "";
		plugin.getLoginPlayers().put(player, player.getLocation());
		player.teleport(plugin.getLoginLocation());
		if (plugin.getDataManager().isRegistered(player))
			message = config.getString("login-instruction-message");
		else
			message = config.getString("register-instruction-message");

		Messager.sendMessage(player, message);
		startKickTimer(player);
	}

	private void startKickTimer(Player player) {
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			if (!plugin.getLoginPlayers().containsKey(player))
				return;

			player.kickPlayer(ChatColor.RED + "Login time exceeded.");
		}, config.getKickTime());
	}
}
