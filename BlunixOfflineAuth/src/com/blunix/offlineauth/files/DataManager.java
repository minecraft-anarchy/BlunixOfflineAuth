package com.blunix.offlineauth.files;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.BlunixOfflineAuth;
import com.blunix.offlineauth.util.Messager;

public class DataManager {
	private BlunixOfflineAuth plugin;

	public DataManager(BlunixOfflineAuth plugin) {
		this.plugin = plugin;
	}

	public void registerPlayer(Player player, String password) {
		plugin.getData().set("registered-players." + player.getUniqueId() + ".password", password);
		plugin.saveData();
	}

	public void unregisterPlayer(Player player) {
		plugin.getData().set("registered-players." + player.getUniqueId(), null);
		plugin.saveData();
		plugin.getUnregisteringPlayers().remove(player);
	}

	public void setRecoveryEmail(Player player, String email) {
		plugin.getData().set("registered-players." + player.getUniqueId() + ".recovery-email", email);
		plugin.saveData();
	}

	public boolean isCorrectPassword(Player player, String inputPassword) {
		return isCorrectPassword(player, player.getUniqueId(), inputPassword);
	}

	public boolean isCorrectPassword(Player player, UUID uuid, String inputPassword) {
		if (!inputPassword.equals(getPlayerPassword(uuid.toString()))) {
			Messager.sendMessage(player, "&cIncorrect password.");
			String ipAddress = player.getAddress().getAddress().getHostAddress();
			Bukkit.getLogger().info("Incorrect password from: \"" + player.getName() + "\" \"" + ipAddress + "\"");

			return false;
		}
		return true;
	}

	public String getPlayerPassword(Player player) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return null;
		}
		return section.getString(player.getUniqueId().toString() + ".password");
	}

	public String getPlayerPassword(String uuid) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return null;
		}
		return section.getString(uuid + ".password");
	}
	
	public String getPlayerRecoveryEmail(String uuid) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return null;
		}
		return section.getString(uuid + ".recovery-email");
	}

	public boolean isRegistered(Player player) {
		return isRegistered(player.getUniqueId());
	}

	public boolean isRegistered(UUID uuid) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return false;
		}
		if (!section.contains(uuid.toString()))
			return false;

		return true;
	}
}
