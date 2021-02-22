package com.blunix.offlineauth.commands;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.BlunixOfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandRegister extends AuthCommand {
	private DataManager dataManager;

	public CommandRegister(BlunixOfflineAuth plugin) {
		this.dataManager = plugin.getDataManager();

		setName("register");
		setHelpMessage("Registers your current username with the specified password.");
		setPermission("offlineauth.register");
		setUsageMessage("/auth register <Password> <ConfirmPassword>");
		setArgumentLength(3);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String password = args[1];
		String confirmPassword = args[2];
		if (dataManager.isRegistered(player)) {
			Messager.sendMessage(player, "&cYou are already registered in the server.");
			return;
		}
		if (password.length() < 6) {
			Messager.sendMessage(player, "&cYou must enter a password with at least 6 characters.");
			return;
		}
		if (!password.equals(confirmPassword)) {
			Messager.sendMessage(player, "&cBoth passwords must match.");
			return;
		}
		dataManager.registerPlayer(player, confirmPassword);
		
		Messager.sendMessage(player, "&aYou successfully registered to the server.");
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
	}
}
