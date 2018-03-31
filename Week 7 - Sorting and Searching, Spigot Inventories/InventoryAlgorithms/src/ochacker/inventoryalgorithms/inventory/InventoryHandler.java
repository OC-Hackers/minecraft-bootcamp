package ochacker.inventoryalgorithms.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import ochacker.inventoryalgorithms.InventoryAlgorithms;

public class InventoryHandler implements Listener {

	@EventHandler
	public void cancelInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {					// check if the entity who fired the command is an instance of the Player interface
			return;															// terminate the event handling if it isn't a player
		}

		ItemStack clickedItem = event.getCurrentItem();

		// What if the clicked item is null? Null pointer, so return.
		if (clickedItem == null || clickedItem.equals(null))
			return;

		String inventoryName = event.getClickedInventory().getName();

		if (!inventoryName.equalsIgnoreCase("Insertion Sort Visualization")) {
			return;
		}

		Player p = (Player) event.getWhoClicked();

		// The following if statement will check to see if the item is a button. We only want to continue if it is a button 
		// The isSimilar() function checks if an itemstack's item is the same as another's... it disregards stack SIZE 
		
		if (clickedItem.getType().equals(Material.STONE_BUTTON)) {
			InventoryAlgorithms.getInventoryOf(p).progressSort();
		}
		
		if (clickedItem.getType().equals(Material.WOOD_BUTTON)) {
			InventoryAlgorithms.getInventoryOf(p).instantSort();
		}
		
		event.setCancelled(true);											// reset the click so the item does not move
	}

	// IMPORTANT: notice the lack of a @EventHandler annotation
	// This means this function WILL NOT BE LISTENED TO by the Spigot event handler
	// this serves as a helper function that takes in the event object
	/**
	 * Helper function to handle a valid click in an inventory
	 * @param event - the InventoryClickEvent in question
	 */
	/*	public void handleNormalClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();

		ItemStack clickedItem = event.getCurrentItem();

		// What if the clicked item is null? Null pointer, so return.
		if (clickedItem == null)
			return;

		// What if the clicked item doesn't have itemmeta? Null pointer, so return.
		if (!clickedItem.hasItemMeta())
			return;

		 The following if statement will check to see if the item is a button. We only want to continue if it is a button 
		 The isSimilar() function checks if an itemstack's item is the same as another's... it disregards stack SIZE 
		if (clickedItem.isSimilar(new ItemStack(Material.STONE_BUTTON)) || clickedItem.isSimilar(new ItemStack(Material.WOOD_BUTTON))) {
			InventoryAlgorithms.getInventories().get(p).instantSort();
		}
	}*/
}
