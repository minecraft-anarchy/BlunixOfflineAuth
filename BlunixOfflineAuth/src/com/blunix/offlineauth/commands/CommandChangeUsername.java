package com.blunix.offlineauth.commands;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.Messager;
import com.google.common.base.Charsets;

public class CommandChangeUsername extends AuthCommand {
	private DataManager dataManager;

	public CommandChangeUsername(OfflineAuth plugin) {
		this.dataManager = plugin.getDataManager();

		setName("changeusername");
		setHelpMessage("Transfers all your previous player data to your current username.");
		setPermission("offlineauth.changeusername");
		setUsageMessage("/auth changeusername <OldUsername> <Password>");
		setArgumentLength(3);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String oldUsername = args[1];
		String password = args[2];
		if (oldUsername.equals(player.getName())) {
			Messager.sendMessage(player, "&cYou can't transfer data from your current username");
			return;
		}
		if (!dataManager.isRegistered(oldUsername)) {
			Messager.sendMessage(player, "&c&l" + oldUsername + " &cisn't registered in the server.");
			return;
		}
		if (!dataManager.isCorrectPassword(player, oldUsername, password))
			return;
		
		transferPlayerData(oldUsername, player.getName());
		player.kickPlayer(ChatColor.GREEN + "Your data has been successfully transfered!");
	}

	private void transferPlayerData(String oldUsername, String newUsername) {
		UUID oldUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + oldUsername).getBytes(Charsets.UTF_8));
		UUID newUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + newUsername).getBytes(Charsets.UTF_8));

		for (World world : Bukkit.getWorlds()) {
			File oldData = new File(world.getWorldFolder() + "/playerdata/" + oldUUID.toString() + ".dat");
			File newData = new File(world.getWorldFolder() + "/playerdata/" + newUUID.toString() + ".dat");
			if (newData.exists())
				newData.delete();

			oldData.renameTo(newData);

			oldData = new File(world.getWorldFolder() + "/playerdata/" + oldUUID.toString() + ".dat_old");
			newData = new File(world.getWorldFolder() + "/playerdata/" + oldUUID.toString() + ".dat_old");
			if (newData.exists())
				newData.delete();

			oldData.renameTo(newData);
		}
	}
}
