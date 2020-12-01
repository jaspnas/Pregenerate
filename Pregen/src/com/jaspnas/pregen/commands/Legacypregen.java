package com.jaspnas.pregen.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Legacypregen implements CommandExecutor {

	private Main plugin;

	public Legacypregen(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			if (!(sender.hasPermission("pregen.pregen.use"))) {
				sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
				return true;
			}
			if (args.length == 0) {
				return false;
			}
			if (args.length >= 3) {
				return false;
			}
			if (Globals.canpregen == false) {
				sender.sendMessage(ChatColor.GOLD + "Please run /prepserver first.");
				return true;
			}

			Globals.ispregenerating = true;

			String timeoutsec = plugin.getConfig().getString("lpg-timeout");

			int timeoutmilli = (Integer.parseInt(timeoutsec) * 1000);

			sender.sendMessage("Timeout is: " + timeoutsec + " (" + timeoutmilli + "ms)");

			int size = Integer.parseInt(args[0]);

			Player p = (Player) sender;

			p.setGameMode(GameMode.CREATIVE);
			p.setAllowFlight(true);
			p.setFlying(true);

			Location loc = p.getLocation();

			loc.setX(0 - size - 16);
			loc.setY(200);
			loc.setZ(0 - size - 16);

			p.teleport(loc);

			Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				public void run() {

					try {
						Thread.sleep(timeoutmilli);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// move the player on the X axis.
					while (p.getLocation().getX() < size + 262 && Globals.canpregen == true) {

						while (p.getLocation().getZ() < size + 16 && Globals.canpregen == true) {

							Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
								public void run() {
									loc.setX(p.getLocation().getX());
									loc.setY(200);
									loc.setZ(p.getLocation().getZ() + 256);
									p.teleport(loc);
								}
							});

							try {
								Thread.sleep(timeoutmilli);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

						Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
							public void run() {
								loc.setX(p.getLocation().getX() + 256);
								loc.setY(200);
								loc.setZ(0 - size - 16);

								p.teleport(loc);
							}
						});

						try {
							Thread.sleep(timeoutmilli);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					if (Globals.canpregen == true) {
						sender.sendMessage(ChatColor.GREEN + "Pregeneration successful");
						Globals.ispregenerating = false;
					} else if (Globals.canpregen == false) {
						sender.sendMessage(ChatColor.RED + "Pregeneration canceled");
						Globals.ispregenerating = false;
						Globals.canpregen = true;
					} else {
						sender.sendMessage(ChatColor.RED + "Unexpected error. Shutting down server.");
						Globals.ispregenerating = false;
						Bukkit.getServer().shutdown();
					}
				}
			});

			return true;
		}

		else {
			sender.sendMessage(ChatColor.RED + "You can't run this as console.");
		}
		return false;
	}

}
