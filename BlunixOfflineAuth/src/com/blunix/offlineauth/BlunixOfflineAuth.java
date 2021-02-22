package com.blunix.offlineauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.blunix.offlineauth.commands.AuthCommand;
import com.blunix.offlineauth.commands.CommandChangePassword;
import com.blunix.offlineauth.commands.CommandChangeUsername;
import com.blunix.offlineauth.commands.CommandCompleter;
import com.blunix.offlineauth.commands.CommandConfirm;
import com.blunix.offlineauth.commands.CommandHelp;
import com.blunix.offlineauth.commands.CommandLogin;
import com.blunix.offlineauth.commands.CommandRecoveryEmail;
import com.blunix.offlineauth.commands.CommandRegister;
import com.blunix.offlineauth.commands.CommandReload;
import com.blunix.offlineauth.commands.CommandRunner;
import com.blunix.offlineauth.commands.CommandUnregister;
import com.blunix.offlineauth.events.LogListener;
import com.blunix.offlineauth.events.PlayerChat;
import com.blunix.offlineauth.events.PlayerJoin;
import com.blunix.offlineauth.events.PlayerRespawn;
import com.blunix.offlineauth.files.DataFile;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.ConfigManager;

public class BlunixOfflineAuth extends JavaPlugin {
	private DataFile data;
	private DataManager dataManager;
	private ConfigManager configManager;

	private Map<String, AuthCommand> subcommands = new LinkedHashMap<String, AuthCommand>();
	private Map<UUID, Location> loginPlayers = new HashMap<UUID, Location>();

	private List<Player> unregisteringPlayers = new ArrayList<Player>();

	private Location loginLocation;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		configManager = new ConfigManager(this);
		data = new DataFile(this);
		dataManager = new DataManager(this);

		getCommand("auth").setExecutor(new CommandRunner(this));
		getCommand("auth").setTabCompleter(new CommandCompleter(this));
		subcommands.put("help", new CommandHelp());
		subcommands.put("register", new CommandRegister(this));
		subcommands.put("login", new CommandLogin(this));
		subcommands.put("unregister", new CommandUnregister(this));
		subcommands.put("changepassword", new CommandChangePassword(this));
		subcommands.put("changeusername", new CommandChangeUsername(this));
		subcommands.put("confirm", new CommandConfirm(this));
//		subcommands.put("recover", new CommandRecover(this));
		subcommands.put("recoveryemail", new CommandRecoveryEmail(this));
		subcommands.put("reload", new CommandReload(this));

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerChat(this), this);
		pm.registerEvents(new PlayerRespawn(this), this);

		LogListener logListener = new LogListener();
		logListener.registerFilter();

		setLoginLocation();
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

	public Map<UUID, Location> getLoginPlayers() {
		return loginPlayers;
	}

	public Location getLoginLocation() {
		return loginLocation;
	}

	public void setLoginLocation() {
		loginLocation = configManager.getLoginLocation();
	}

	public ConfigManager getConfigManager() {
		return configManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public List<Player> getUnregisteringPlayers() {
		return unregisteringPlayers;
	}
}
