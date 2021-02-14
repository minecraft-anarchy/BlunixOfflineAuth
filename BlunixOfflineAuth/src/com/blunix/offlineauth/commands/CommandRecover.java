package com.blunix.offlineauth.commands;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.blunix.offlineauth.OfflineAuth;
import com.blunix.offlineauth.files.DataManager;
import com.blunix.offlineauth.util.ConfigManager;
import com.blunix.offlineauth.util.Messager;

public class CommandRecover extends AuthCommand {
	private OfflineAuth plugin;
	private DataManager dataManager;

	public CommandRecover(OfflineAuth plugin) {
		this.plugin = plugin;
		this.dataManager = plugin.getDataManager();

		setName("recover");
		setHelpMessage(
				"Sends an email to your recovery email with a temporary password you can use in case you forgot yours.");
		setPermission("offlineauth.recover");
		setUsageMessage("/auth recover <Username>");
		setArgumentLength(2);
		setPlayerCommand(true);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		String username = args[1];
		if (!plugin.getLoginPlayers().containsKey(player)) {
			Messager.sendMessage(player, "&cYou are already logged in to the server.");
			return;
		}
		if (!dataManager.isRegistered(username)) {
			Messager.sendMessage(player, "&c&l" + username + " &cisn't registered in the server yet.");
			return;
		}
		String emailTo = dataManager.getPlayerRecoveryEmail(username);
		if (emailTo == null) {
			Messager.sendMessage(player, "&cThis username does not have any recovery email registered.\n"
					+ "Please register a recovery email typing &l/auth recoveryemail <Email> &cbefore running this command.");
			return;
		}
		Random random = new Random();
		String temporaryPassword = random.nextInt() + "";
		dataManager.registerPlayer(player, temporaryPassword);
		sendRecoveryEmail(emailTo, temporaryPassword);
		Messager.sendMessage(player,
				"&aCheck your recovery email and type &l/auth login <Password> &awith the password you received to login to the server.");

	}

	private void sendRecoveryEmail(String emailTo, String temporaryPassword) {
		ConfigManager config = new ConfigManager(plugin);
		String host = config.getString("email-host");
		String user = config.getString("email-sender");
		String password = config.getString("email-sender-password");
		String port = config.getString("email-port");

		String emailSubject = config.getString("email-subject");
		String emailContent = config.getString("email-content").replace("{PASSWORD}", temporaryPassword);

		// Get the session object
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.mailtrap.io");
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
			}
		});

		// Create the message
		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(user));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			message.setSubject(emailSubject);
			message.setText(emailContent);

			// Send the message
			Transport.send(message);

			Bukkit.getLogger()
					.info("A recovery email was sent to " + emailTo + " with temporary password: " + temporaryPassword);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		// ConfigManager config = new ConfigManager(plugin);
//		String emailFrom = config.getString("recovery-email-sender");
//		String host = config.getString("email-host");
//		String emailSubject = config.getString("email-subject");
//		String emailContent = config.getString("email-content").replace("{PASSWORD}", temporaryPassword);
//
//		// Creating a session
//		Properties properties = System.getProperties();
//		properties.setProperty("mail.smtp.host", host);
//		Session session = Session.getDefaultInstance(properties);
//
//		// Creating the mail
//		try {
//			MimeMessage message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(emailFrom));
//			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
//			message.setSubject(emailSubject);
//			message.setText(emailContent);
//
//			// Sending email
//			Transport.send(message);
//			Bukkit.getLogger()
//					.info("A recovery email was sent to " + emailTo + " with temporary password: " + temporaryPassword);
//
//		} catch (MessagingException mex) {
//			mex.printStackTrace();
//		}
	}
}
