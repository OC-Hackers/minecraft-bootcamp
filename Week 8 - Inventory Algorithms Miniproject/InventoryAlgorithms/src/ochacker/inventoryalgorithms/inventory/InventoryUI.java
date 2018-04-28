package ochacker.inventoryalgorithms.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import ochacker.inventoryalgorithms.InventoryAlgorithms;
import ochacker.inventoryalgorithms.algs.Utils;

/**
 * InventoryUI Object which is a custom inventory that handles sorting.
 *
 */
public class InventoryUI {
	
	/*
	 * Here, we define our instance variables for the player and the Inventory that is associated with the InventoryUI object.
	 * 
	 * We set them to be public, but final, so that we do not need getter and setter functions.
	 * Since they are final objects, we do not need to worry about safety, since they cannot be edited.
	 * 
	 * Generally, you want to do this for Objects with elements that should not change.
	 */
	public final Player player;			// The Player that the custom InventoryUI is associated with
	public final Inventory ui;			// The inventory object that contains the Items

	// Sorting variables
	private ItemStack key;		// The Key is going to be the ItemStack object.
	private int i = 0;			// We use i, j as the index variables in insertion sort
	private int j = 1;

	/**
	 * Default constructor. 
	 * <br>
	 * Instantiates a new InventoryUI object that defaults with size 9.
	 */
	public InventoryUI(Player player) {
		ui = Bukkit.createInventory(null, 9);
		processInventory(9);
		this.player = player;
	}

	/**
	 * Instantiates a new InventoryUI object with a specific size
	 * @param size - the size of the inventory UI
	 */
	public InventoryUI(Player player, int size) {
		ui = Bukkit.createInventory(null, size);
		processInventory(size);
		this.player = player;
	}

	/**
	 * Instantiates a new InventoryUI with the default size 9, with a specified name
	 * @deprecated
	 * @param name
	 */
	public InventoryUI(Player player, String name) {
		ui = Bukkit.createInventory(null, 9, name);
		processInventory(9);
		this.player = player;
	}

	/**
	 * Instantiates a new InventoryUI object with a specified name and size
	 * @param size
	 * @param name
	 */
	public InventoryUI(Player player, int size, String name) {
		ui = Bukkit.createInventory(null, size, name);
		processInventory(size);
		this.player = player;
	}
	
	public void playerOpenGUI() {
		player.openInventory(ui);
	}

	/**
	 * Open the GUI for the specified player
	 * @param player - the player to open the GUI for.
	 */
	public void playerOpenGUI(Player player) {
		player.openInventory(ui);
	}

	/**
	 * Instantly sort the Inventory incrementally in amount using Insertion Sort.
	 * 
	 * Insertion sort adapted from CLRS, modified for Minecraft items.
	 */
	public void instantSort() {
		/*
		 * You will notice that we run ui.getSize() - 3.
		 * We subtract 3 because we MUST compensate for the two buttons that we have at the end of the inventory.
		 * If you do not know what this is referring to, please scroll to the bottom and view the initialize() code.
		 */
		for (int j = 1; j < ui.getSize() - 3; j++) {
			ItemStack key = ui.getItem(j);

			int i = j - 1;
			
			// This loop structure is functionally the same as Insertion sort, but we modify it slightly so that
			// we calculate the right comparisons.
			// Here, we get the item at index i then compare it by its amount.
			// Our key remains the same as regular insertion sort, but it is just an ItemStack object.
			while (i >= 0 && ui.getItem(i).getAmount() > key.getAmount()) {
				ui.setItem(i + 1, ui.getItem(i));		// equivalent integer array sort snippet: arr[j + 1] = arr[j];
				player.updateInventory();				// "push" the changes to the game so the player sees the update
				i--;									// decrement the index
			}
			
			ui.setItem(i + 1, key);						// Complete the iteration by inserting Item at i + 1 to be the key.
			player.updateInventory();					// update the player's visual inventory
		}
	}

	/**
	 * Progressively sort the Inventory. Progresses each step after a few seconds.
	 * 
	 * @return - true if the sort progressed, false if otherwise
	 */
	public boolean progressSort() {
		while (this.j < ui.getSize() - 3) {
			this.key = ui.getItem(j);
			
			this.i = j - 1;
			
			new BukkitRunnable() {
				public void run() {
					if (!(i >= 0 && ui.getItem(i).getAmount() > key.getAmount())) {
						cancel();
						return;
					}
					ui.setItem(i + 1, ui.getItem(i));
					player.updateInventory();
				}
			}.runTaskLater(InventoryAlgorithms.getInstance(), 100L);
			
			ui.setItem(i + 1, key);
			player.updateInventory();
			j++;
		}
		
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean regressSort() {
		return false;
	}

	/**
	 * Reshuffle the inventory.
	 */
	public void reshuffle() {
		processInventory(ui.getSize());
	}

	/**
	 * Private helper method to process and shuffle the inventory.
	 * <br>
	 * Adds size - 3 golden apples (each of stack size i) into the ui. We use (size - 3) because we want the last two to be saved for
	 * the progress and degress buttons.
	 * @param size
	 */
	private void processInventory(int size) {
		int[] stacksizes = new int[size - 3];
		for (int i = 0; i < size - 3; i++) {						// add a golden apple eight times
			stacksizes[i] = i + 1;									// add a golden apple stack with size of i into the inventory
		}

		shuffleArray(stacksizes);
		for (int i = 0; i < size - 3; i++) {
			ui.setItem(i, new ItemStack(Material.GOLDEN_APPLE, stacksizes[i]));
		}
		
		// Create our wooden button that acts as our button to initiate the instant sort of the program.
		ItemStack woodButton = new ItemStack(Material.WOOD_BUTTON);
		ItemMeta woodMeta = woodButton.getItemMeta();
		woodMeta.setDisplayName(ChatColor.RED + "Fully Sort");
		woodButton.setItemMeta(woodMeta);
		
		// Create our stone button that acts as our button to initiate a slower sort of the program.
		ItemStack stoneButton = new ItemStack(Material.STONE_BUTTON);
		ItemMeta stoneMeta = stoneButton.getItemMeta();
		stoneMeta.setDisplayName(ChatColor.GREEN + "Incrementally (Slowly) Sort Next Step");
		stoneButton.setItemMeta(stoneMeta);
		
		ui.setItem(size - 1, woodButton);
		ui.setItem(size - 2, stoneButton);
	}

	/**
	 * Shuffle an array using the Fisher-Yates shuffle
	 * @param arr
	 */
	private void shuffleArray(int[] arr) {
		for (int i = arr.length - 1; i > 0; i--) {
			int index = Utils.randInt(0, arr.length - 1);
			// Simple swap
			int a = arr[index];
			arr[index] = arr[i];
			arr[i] = a;
		}
	}
}
