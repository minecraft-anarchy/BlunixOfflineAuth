package com.blunix.offlineauth.commands;

import org.bukkit.command.CommandSender;

import com.blunix.offlineauth.util.Messager;

public class CommandHelp extends AuthCommand {
	public CommandHelp() {
		setName("help");
		setHelpMessage("Displays this list.");
		setPermission("offlineauth.help");
		setUsageMessage("/oa help");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Messager.sendHelpMessage(sender);
	}
}
