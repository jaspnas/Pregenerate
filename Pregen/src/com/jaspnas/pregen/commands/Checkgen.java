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

import com.jaspnas.pregen.Main;

public class Checkgen implements CommandExecutor, TabCompleter {

	private Main plugin;

	public Checkgen(Main plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(args.length == 2)) {
			return false;
		}

		int size = Integer.parseInt(args[0]);

		int xcord = 0 - (size / 16);
		int zcord = 0 - (size / 16);
		// int worldnr = Integer.parseInt(args[1]);
		String worldname = args[1];

		sender.sendMessage(Bukkit.getWorlds().toString());

		sender.sendMessage("Starting at:" + xcord * 16 + ", " + zcord * 16 + "in world 1");

		if (!(Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(worldname)))) {
			return false;
		}

		Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
			public void run() {

				int xx = xcord;
				int zz = zcord;

				// move x
				while (xx * 16 <= size) {

					// move z
					while (zz * 16 <= size) {

						int chunkx = xx;
						int chunkz = zz;

						if (Bukkit.getWorld(worldname).isChunkGenerated(chunkx, chunkz) == false) {
							sender.sendMessage(ChatColor.GOLD + "All chunks were not generated (" + chunkx * 16 + ", "
									+ chunkz * 16 + ")!");
							return;
						}

						sender.sendMessage("checked chunk at " + chunkx * 16 + ", " + chunkz * 16 + ".");
						zz = zz + 1;

					}

					xx = xx + 1;
					zz = 0 - (size / 16);

				}

				sender.sendMessage(ChatColor.GREEN + "All chunks exist");

			}

		});

		return true;

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("checkgen")) {

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
