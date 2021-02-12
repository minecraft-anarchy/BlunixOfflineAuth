package com.blunix.offlineauth.commands;

import java.util.regex.Pattern;

import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;

public class CommandRecoveryEmail extends AuthCommand {
	private OfflineAuth plugin;
	private DataManager dataManager;

	public CommandRecoveryEmail(OfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("recoveryemail");
		setHelpMessage("Sets you recovery e-mail in case you forget your password.");
		setPermission("offlineauth.recoveryemail");
		setUsageMessage("/auth recoveryemail <E-mail>");
		setArgumentLength(2);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String email = args[1];
		if (plugin.getLoginPlayers().containsKey(player)) {
			Messager.sendMessage(player, "&cYou need to login to the server before setting your recovery e-mail.");
			return;
		}
		if (!dataManager.isRegistered(player)) {
			Messager.sendMessage(player, "&cYou are not registered in the server yet");
			return;
		}
		if (!isValidEmail(email)) {
			Messager.sendMessage(player, "&cYou must enter a valid e-mail.");
			return;
		}
		dataManager.setRecoveryEmail(player, email);
		
		Messager.sendMessage(player, "&aYour recovery E-mail has been set to &l" + email);
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
	}

	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		
		return pat.matcher(email).matches();
	}
}
