package com.blunix.offlineauth.commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandLogin extends AuthCommand {
	private OfflineAuth plugin;
	private DataManager dataManager;

	public CommandLogin(OfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("login");
		setHelpMessage("Logins you to the server using your password.");
		setPermission("offlineauth");
		setUsageMessage("/auth login <Password>");
		setArgumentLength(2);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String password = args[1];
		if (!plugin.getLoginPlayers().containsKey(player)) {
			Messager.sendMessage(player, "&cYou are already logged in to the server.");
			return;
		}
		if (!dataManager.isRegistered(player)) {
			Messager.sendMessage(player, "&cYou haven't registered to the server yet.");
			return;
		}
		if (!dataManager.isCorrectPassword(player, password))
			return;
		
		player.teleport(plugin.getLoginPlayers().get(player));
		plugin.getLoginPlayers().remove(player);
		
		Messager.sendMessage(player, "&aYou successfully logged in to the server.");
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
	}
}
