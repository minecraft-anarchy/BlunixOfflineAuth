package com.blunix.offlineauth.commands;

import org.bukkit.command.CommandSender;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.util.Messager;

public class CommandReload extends AuthCommand {
	private OfflineAuth plugin;

	public CommandReload(OfflineAuth plugin) {
		this.plugin = plugin;

		setName("reload");
		setHelpMessage("Reloads the plugin's config.");
		setPermission("offlineauth.reload");
		setUsageMessage("/auth reload");
		setArgumentLength(1);
		setUniversalCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		plugin.reloadConfig();
		plugin.setLoginLocation();
		Messager.sendMessage(sender, "&aConfig reloaded.");
	}
}
