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
	
	/**
	 * Event Handler that detects when a player joins and forces them into creative mode (for safety)
	 * <br>
	 * We open the player's inventory a second after they join to avoid synchronization problems.
	 * @param event - PlayerJoinEvent
	 */
	@EventHandler
	public void enforceCreativeMode(PlayerJoinEvent event) {
		Player p = event.getPlayer();								// get the player who just joined
		
		if (!p.getGameMode().equals(GameMode.CREATIVE)) {			// set the player into creative mode to protect them
			p.setGameMode(GameMode.CREATIVE);
		}
		InventoryAlgorithms.getInstance().getLogger().log(Level.INFO, "Log message test");		// log message
		
		InventoryUI insertionSort = new InventoryUI(p, 9, "Insertion Sort Visualization");		// initialize the custom inventory
		
		InventoryAlgorithms.getInventories().add(insertionSort);	// Add the player's specific inventory to the global database
		
		/*
		 * This following segment of code is an Asynchronous task.
		 * Asynchronous tasks are events that run on the Java compiler in sequence.
		 * Sometimes these are multi-threaded (using multiple CPU cores) events.
		 * 
		 * In our case, we simply want to open the player's inventory ONE SECOND (*20L) after they join.
		 * 
		 * The fancy () -> notation is called an anonymous expression, or a lambda expression.
		 * This allows us to create functions without needing to define a new class WITHIN an existing function. 
		 * In other words, we are putting a function inside another function as a parameter. 
		 * 
		 * In this case, the function is an instance of a Runnable - a Java object that indicates an asynchronous action.
		 */
		Bukkit.getScheduler().runTaskLater(InventoryAlgorithms.getInstance(), () -> {
			insertionSort.playerOpenGUI();
		}, 20L);
	}
	
	// This is a deprecated function that keeps the player's inventory open.
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
