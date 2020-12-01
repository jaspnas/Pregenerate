package com.jaspnas.pregen;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.jaspnas.pregen.commands.Cancel;
import com.jaspnas.pregen.commands.Checkchunk;
import com.jaspnas.pregen.commands.Checkgen;
import com.jaspnas.pregen.commands.Genchunk;
import com.jaspnas.pregen.commands.Legacypregen;
import com.jaspnas.pregen.commands.Pregen;
import com.jaspnas.pregen.commands.Prepserver;
import com.jaspnas.pregen.commands.Testcommand;
import com.jaspnas.pregen.listeners.Join;
import com.jaspnas.pregen.listeners.Shutdown;

public class Main extends JavaPlugin {

	ConsoleCommandSender console = getServer().getConsoleSender();
	@SuppressWarnings("unused")
	private static Plugin plugin;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		getCommand("legacypregen").setExecutor(new Legacypregen(this));
		getCommand("checkgen").setExecutor(new Checkgen(this));
		getCommand("pregen").setExecutor(new Pregen(this));
		getCommand("genchunk").setExecutor(new Genchunk(this));
		getCommand("checkchunk").setExecutor(new Checkchunk(this));
		getCommand("cancel").setExecutor(new Cancel(this));
		getCommand("testcommand").setExecutor(new Testcommand(this));
		getCommand("prepserver").setExecutor(new Prepserver(this));

		getServer().getPluginManager().registerEvents(new Shutdown(this), this);
		getServer().getPluginManager().registerEvents(new Join(this), this);
	}

	@Override
	public void onDisable() {
	}

}
