package com.blunix.offlineauth.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.util.Messager;

public class DataManager {
	private OfflineAuth plugin;

	public DataManager(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	public void registerPlayer(Player player, String password) {
		plugin.getData().set("registered-players." + player.getName() + ".password", password);
		plugin.saveData();
	}

	public void unregisterPlayer(Player player) {
		plugin.getData().set("registered-players." + player.getName(), null);
		plugin.saveData();
		plugin.getUnregisteringPlayers().remove(player);
	}

	public void setRecoveryEmail(Player player, String email) {
		plugin.getData().set("registered-players." + player.getName() + ".recovery-email", email);
		plugin.saveData();
	}

	public boolean isCorrectPassword(Player player, String inputPassword) {
		return isCorrectPassword(player, player.getName(), inputPassword);
	}

	public boolean isCorrectPassword(Player player, String playerName, String inputPassword) {
		if (!inputPassword.equals(getPlayerPassword(playerName))) {
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
		return section.getString(player.getName() + ".password");
	}

	public String getPlayerPassword(String playerName) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return null;
		}
		return section.getString(playerName + ".password");
	}
	
	public String getPlayerRecoveryEmail(String playerName) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return null;
		}
		return section.getString(playerName + ".recovery-email");
	}

	public boolean isRegistered(Player player) {
		return isRegistered(player.getName());
	}

	public boolean isRegistered(String playerName) {
		ConfigurationSection section = plugin.getData().getConfigurationSection("registered-players");
		if (section == null) {
			Bukkit.getLogger().info("[OfflineAuth] There was an error reading registered-players in data.yml");
			return false;
		}
		if (!section.contains(playerName))
			return false;

		return true;
	}
}
