package com.jaspnas.pregen.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.jaspnas.pregen.Globals;
import com.jaspnas.pregen.Main;

public class Pregen implements CommandExecutor, TabCompleter {

	private Main plugin;

	public Pregen(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender.hasPermission("pregen.pregen.use")) {

			if (args.length <= 1) {
				return false;
			}

			if (Globals.canpregen == false) {
				sender.sendMessage("Please run /prepserver first");
				return true;
			}

			Globals.ispregenerating = true;

			int size = Integer.parseInt(args[0]);
			int xx = 0 - (size / 16);
			int zz = 0 - (size / 16);
			String worldname = args[1];
			String timeoutstr = plugin.getConfig().getString("bpg-timeout");
			int timeout = Integer.parseInt(timeoutstr);

			if (!(Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(worldname)))) {
				sender.sendMessage(ChatColor.RED + "World does not exist!");
				return false;
			}

			sender.sendMessage("timeout is " + timeout);

			sender.sendMessage("Pregeneration starting at: " + xx * 16 + ", " + zz * 16);

			Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
				public void run() {

					int xcord = xx;
					int zcord = zz;

					while (xcord * 16 <= size && Globals.canpregen == true) {
						while (zcord * 16 <= size && Globals.canpregen == true) {
							int chunkx = xcord;
							int chunkz = zcord;
							Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
								public void run() {
									Bukkit.getWorld(worldname).loadChunk(chunkx, chunkz, true);
								}
							});
							while (Bukkit.getWorld(worldname).isChunkGenerated(chunkx, chunkz) == false) {
								try {
									Thread.sleep(timeout);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							Bukkit.getServer().getScheduler().runTask(plugin, new Runnable() {
								public void run() {
									Bukkit.getWorld(worldname).unloadChunk(chunkx, chunkz);
								}
							});

							try {
								Thread.sleep(timeout);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							zcord = zcord + 1;

						}

						xcord = xcord + 1;
						zcord = zz;

						double perx = xcord;
						double persize = size + 16;
						double percentage = ((perx + persize / 16) / ((persize / 16) * 2)) * 100;

						sender.sendMessage("Now on x=" + xcord * 16 + " (" + Math.round(percentage) + "%)");
					}
					if (Globals.canpregen == true) {
						sender.sendMessage(ChatColor.GREEN + "All chunks generated! (final chunk " + xcord * 16 + ", "
								+ zcord * (-16) + ").");
						Globals.ispregenerating = false;
					} else if (Globals.canpregen == false) {
						sender.sendMessage(ChatColor.RED + "Pregeneration failed (cancelled).");
						Globals.ispregenerating = false;
						Globals.canpregen = true;
					} else {
						sender.sendMessage(ChatColor.RED + "Pregeneration failed for an unknown reason!");
						Globals.ispregenerating = false;
					}
				}

			});
			return true;

		} else {
			sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
			return true;
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("pregen")) {

			List<String> autoCompletes = new ArrayList<>();

			if (args.length == 0) {
				return null;
			}

			if (args.length == 2) {

				for (World world : Bukkit.getServer().getWorlds()) {

					autoCompletes.add(world.getName());
				}

			}

			return autoCompletes;
		}

		return null;

	}
}
