package com.blunix.offlineauth.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandConfirm extends AuthCommand {
	private OfflineAuth plugin;
	private DataManager dataManager;

	public CommandConfirm(OfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("confirm");
		setHelpMessage("Confirms the unregister of your username from the server.");
		setPermission("offlineauth.confirm");
		setUsageMessage("/auth confirm");
		setArgumentLength(1);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (!plugin.getUnregisteringPlayers().contains(player)) {
			Messager.sendMessage(player,
					"&cYou haven't open any unregister request yet. Type &l/auth unregister <Password> to open one.");
			return;
		}
		dataManager.unregisterPlayer(player);
		player.kickPlayer(ChatColor.GREEN + "Username successfully unregistered.");
	}
}
