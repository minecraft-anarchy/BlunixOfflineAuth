package com.blunix.offlineauth.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import com.blunix.offlineauth.OfflineAuth;

public class DataManager {
	private OfflineAuth plugin;

	public DataManager(OfflineAuth plugin) {
		this.plugin = plugin;
	}

	public void saveLoginLocation() {
		if (plugin.getLoginLocation() == null)
			return;

		Location loginLocation = plugin.getLoginLocation();
		int x = loginLocation.getBlockX();
		int y = loginLocation.getBlockY();
		int z = loginLocation.getBlockZ();
		float pitch = loginLocation.getPitch();
		float yaw = loginLocation.getYaw();
		String worldName = loginLocation.getWorld().getName();

		FileConfiguration data = plugin.getData();
		String path = "login-location";
		data.set(path + ".x", x);
		data.set(path + ".y", y);
		data.set(path + ".z", z);
		data.set(path + ".pitch", pitch);
		data.set(path + ".yaw", yaw);
		data.set(path + ".world", worldName);

		plugin.saveData();
	}

	public void loadLoginLocation() {
		if (!plugin.getData().contains("login-location"))
			return;

		FileConfiguration data = plugin.getData();
		String path = "login-location";
		int x = data.getInt(path + ".x");
		int y = data.getInt(path + ".y");
		int z = data.getInt(path + ".z");
		float pitch = (float) data.getDouble(path + ".pitch");
		float yaw = (float) data.getDouble(path + ".yaw");
		World world = Bukkit.getWorld(data.getString(path + ".world"));

		Location loginLocation = new Location(world, x, y, z);
		loginLocation.setPitch(pitch);
		loginLocation.setYaw(yaw);
		plugin.setLoginLocation(loginLocation);
	}
}
