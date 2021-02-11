package com.blunix.offlineauth.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.util.Messager;

public class CommandSetLoginLocation extends AuthCommand {
	private OfflineAuth plugin;

	public CommandSetLoginLocation(OfflineAuth plugin) {
		this.plugin = plugin;

		setName("setloginlocation");
		setHelpMessage("Sets the location players will be teleported while login to your current location.");
		setPermission("offlineauth.setloginlocation");
		setUsageMessage("/oa setloginlocation");
		setArgumentLength(1);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		Location loginLocation = player.getLocation();
		plugin.setLoginLocation(loginLocation);
		Messager.sendMessage(player, "&aThe server's login location has been successfully updated.");
	}
}
