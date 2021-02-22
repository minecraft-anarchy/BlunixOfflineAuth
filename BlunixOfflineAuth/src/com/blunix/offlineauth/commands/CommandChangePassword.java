package com.blunix.offlineauth.commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.BlunixOfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandChangePassword extends AuthCommand {
	private BlunixOfflineAuth plugin;
	private DataManager dataManager;

	public CommandChangePassword(BlunixOfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("changepassword");
		setHelpMessage("Changes your current password for the one you specify.");
		setPermission("offlineauth.changepassword");
		setUsageMessage("/auth changepassword <OldPassword> <NewPassword>");
		setArgumentLength(3);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String oldPassword = args[1];
		String newPassword = args[2];
		if (plugin.getLoginPlayers().containsKey(player.getUniqueId())) {
			Messager.sendMessage(player, "&cYou need to login before you can change your password.");
			return;
		}
		if (!dataManager.isRegistered(player)) {
			Messager.sendMessage(player, "&cYou are not registered in the server yet.");
			return;
		}
		if (!dataManager.isCorrectPassword(player, oldPassword))
			return;
		if (newPassword.length() < 6) {
			Messager.sendMessage(player, "&cYou must enter a password with at least 6 characters.");
			return;
		}
		if (oldPassword.equals(newPassword)) {
			Messager.sendMessage(player, "&cYou can't set the same password again.");
			return;
		}
		dataManager.registerPlayer(player, newPassword);
		
		Messager.sendMessage(player, "&aYou successfully changed your password.");
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
	}
}
