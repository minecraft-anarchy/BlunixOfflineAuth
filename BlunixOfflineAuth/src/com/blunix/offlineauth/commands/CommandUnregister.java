package com.blunix.offlineauth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandUnregister extends AuthCommand {
	private OfflineAuth plugin;
	private DataManager dataManager;

	public CommandUnregister(OfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("unregister");
		setHelpMessage("Unregisters your username from the server.");
		setPermission("offlineauth.unregister");
		setUsageMessage("/auth unregister <Password>");
		setArgumentLength(2);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String password = args[1];
		if (plugin.getLoginPlayers().containsKey(player)) {
			Messager.sendMessage(player, "&cYou need to login before you can unregister your username.");
			return;
		}
		if (!dataManager.isRegistered(player)) {
			Messager.sendMessage(player, "&cYou are not registered in the server yet.");
			return;
		}
		if (!dataManager.isCorrectPassword(player, password))
			return;
		if (!plugin.getUnregisteringPlayers().contains(player)) {
			plugin.getUnregisteringPlayers().add(player);
			Bukkit.getScheduler().runTaskLater(plugin, () -> {
				if (!plugin.getUnregisteringPlayers().contains(player))
					return;

				plugin.getUnregisteringPlayers().remove(player);
			}, 1200);
		}
		Messager.sendMessage(player, "&6Type &l/auth confirm &6to unregister your username from the server.");
	}
}
