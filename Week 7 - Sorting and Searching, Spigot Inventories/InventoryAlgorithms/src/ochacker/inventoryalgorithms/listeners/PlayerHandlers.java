package ochacker.inventoryalgorithms.listeners;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ochacker.inventoryalgorithms.InventoryAlgorithms;
import ochacker.inventoryalgorithms.inventory.InventoryUI;

public class PlayerHandlers implements Listener {
	
	@EventHandler
	public void enforceCreativeMode(PlayerJoinEvent event) {
		Player p = event.getPlayer();								// get the player who just joined
		
		if (!p.getGameMode().equals(GameMode.CREATIVE)) {			// set the player into creative mode to protect them
			p.setGameMode(GameMode.CREATIVE);
		}
		InventoryAlgorithms.getInstance().getLogger().log(Level.INFO, "Log message test");
		InventoryUI insertionSort = new InventoryUI(p, 9, "Insertion Sort Visualization");
		InventoryAlgorithms.getInventories().add(insertionSort);
		Bukkit.getScheduler().runTaskLater(InventoryAlgorithms.getInstance(), () -> {
			insertionSort.playerOpenGUI();
		}, 20L);
	}
	
	/*@EventHandler
	public void enforceKeepInventoryOpen(InventoryCloseEvent event) {
		Player p = (Player) event.getPlayer();
		if (!p.isOnline()) return;
		
		String inventoryName = event.getInventory().getName();
		
		if (inventoryName.equalsIgnoreCase("Insertion Sort Visualization")) {
			Bukkit.getScheduler().runTaskLater(InventoryAlgorithms.getInstance(), () -> {
				InventoryAlgorithms.getInventoryOf(p).playerOpenGUI();;
			}, 5L);
		}
	}*/

}
