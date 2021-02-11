package com.blunix.offlineauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.offlineauth.commands.AuthCommand;
import com.blunix.offlineauth.commands.CommandCompleter;
import com.blunix.offlineauth.commands.CommandHelp;
import com.blunix.offlineauth.commands.CommandReload;
import com.blunix.offlineauth.commands.CommandRunner;
import com.blunix.offlineauth.commands.CommandSetLoginLocation;
import com.blunix.offlineauth.events.PlayerChat;
import com.blunix.offlineauth.events.PlayerJoin;
import com.blunix.offlineauth.files.DataFile;

public class OfflineAuth extends JavaPlugin {
	private DataFile data;

	private Map<String, AuthCommand> subcommands = new HashMap<String, AuthCommand>();

	private List<Player> loginPlayers = new ArrayList<Player>();

	private Location loginLocation;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		data = new DataFile(this);

		getCommand("offlineauth").setExecutor(new CommandRunner(this));
		getCommand("offlineauth").setTabCompleter(new CommandCompleter(this));
		subcommands.put("help", new CommandHelp());
		subcommands.put("reload", new CommandReload(this));
		subcommands.put("setloginlocation", new CommandSetLoginLocation(this));

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerChat(this), this);
	}

	@Override
	public void onDisable() {

	}

	public FileConfiguration getData() {
		return data.getConfig();
	}

	public void saveData() {
		data.saveConfig();
	}

	public Map<String, AuthCommand> getSubcommands() {
		return subcommands;
	}

	public List<Player> getLoginPlayers() {
		return loginPlayers;
	}

	public Location getLoginLocation() {
		return loginLocation;
	}

	public void setLoginLocation(Location loginLocation) {
		this.loginLocation = loginLocation;
	}
}
