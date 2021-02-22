package com.blunix.offlineauth.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.blunix.offlineauth.BlunixOfflineAuth;
import com.blunix.offlineauth.util.ConfigManager;
import com.blunix.offlineauth.util.Messager;

public class PlayerJoin implements Listener {
	private BlunixOfflineAuth plugin;
	private ConfigManager config;

	public PlayerJoin(BlunixOfflineAuth plugin) {
		this.plugin = plugin;
		this.config = plugin.getConfigManager();
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!plugin.getDataManager().isRegistered(player))
			return;
		if (!plugin.getLoginPlayers().containsKey(player.getUniqueId()))
			plugin.getLoginPlayers().put(player.getUniqueId(), player.getLocation());
		player.teleport(plugin.getLoginLocation());
		Messager.sendMessage(player, config.getString("login-instruction-message"));
		startKickTimer(player);
	}

	private void startKickTimer(Player player) {
		long kickTime = config.getKickTime();
		Bukkit.getScheduler().runTaskLater(plugin, () -> {
			if (!plugin.getLoginPlayers().containsKey(player.getUniqueId()))
				return;

			player.kickPlayer(ChatColor.RED + "You did not use /auth within " + kickTime / 20
					+ " seconds. Please re-login and authenticate in time.");
		}, kickTime);
	}
}
