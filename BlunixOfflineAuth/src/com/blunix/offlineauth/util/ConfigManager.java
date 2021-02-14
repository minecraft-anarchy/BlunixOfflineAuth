package com.blunix.offlineauth.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.blunix.offlineauth.OfflineAuth;

public class ConfigManager {
	private OfflineAuth plugin;

	public ConfigManager(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	public Location getLoginLocation() {
		String path = "login-location";
		Location loginLocation;
		try {
			int x = plugin.getConfig().getInt(path + ".x");
			int y = plugin.getConfig().getInt(path + ".y");
			int z = plugin.getConfig().getInt(path + ".z");
			float pitch = (float) plugin.getConfig().getDouble(path + ".pitch");
			float yaw = (float) plugin.getConfig().getDouble(path + ".yaw");
			World world = Bukkit.getWorld(plugin.getConfig().getString(path + ".world"));

			loginLocation = new Location(world, x, y, z);
			loginLocation.setPitch(pitch);
			loginLocation.setYaw(yaw);
		} catch (Exception e) {
			e.printStackTrace();
			Bukkit.getLogger().info("[OfflineAuth] Error while getting login location from config.yml");
			return Bukkit.getWorlds().iterator().next().getSpawnLocation();
		}
		return loginLocation;
	}
	
	public long getKickTime() {
		return (long) plugin.getConfig().getDouble("kick-time") * 20;
	}

	public String getString(String path) {
		return plugin.getConfig().getString(path);
	}
}
