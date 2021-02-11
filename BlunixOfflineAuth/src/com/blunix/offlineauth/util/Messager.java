package com.blunix.offlineauth.util;

import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.commands.AuthCommand;

public class Messager {

	public static void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}

	public static void sendHelpMessage(CommandSender sender) {
		OfflineAuth plugin = OfflineAuth.getPlugin(OfflineAuth.class);
		String finalMessage = "&lCommands\n";

		Iterator<AuthCommand> iterator = plugin.getSubcommands().values().iterator();

		while (iterator.hasNext()) {
			AuthCommand subcommand = iterator.next();
			if (!sender.hasPermission(subcommand.getPermission()))
				continue;

			finalMessage += "&6" + subcommand.getUsageMessage() + " &3- &b" + subcommand.getHelpMessage();
			if (iterator.hasNext())
				finalMessage += "\n";

		}

		Messager.sendMessage(sender, finalMessage);
	}

	public static void sendNoPermissionMessage(CommandSender sender) {
		sendMessage(sender, "&cYou do not have permissions to use this command!");
	}
}
