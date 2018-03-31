package ochacker.inventoryalgorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import ochacker.inventoryalgorithms.inventory.InventoryHandler;
import ochacker.inventoryalgorithms.inventory.InventoryUI;
import ochacker.inventoryalgorithms.listeners.PlayerHandlers;

public class InventoryAlgorithms extends JavaPlugin {
	
	private static Logger log;
	
	private static InventoryAlgorithms plugin;
	
	private static List<InventoryUI> inventories;
	
	public static final InventoryAlgorithms getInstance() {
		return plugin;
	}
	
	public static final List<InventoryUI> getInventories() {
		return inventories;
	}
	
	public static final Logger getPluginLogger() {
		return log;
	}
	
	public static final InventoryUI getInventoryOf(Player player) {
		for (InventoryUI ui : inventories) {
			if (ui.player.getUniqueId().equals(player.getUniqueId())) return ui;
		}
		return null;
	}
	
	@Override
	public void onEnable() {
		log = getLogger();
		plugin = this;
		inventories = new ArrayList<InventoryUI>();
		
		registerCommands();
		registerEvents();
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	private void registerCommands() {
		
	}
	
	private void registerEvents() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new InventoryHandler(), this);
		pm.registerEvents(new PlayerHandlers(), this);
	}
}
