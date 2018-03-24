package ochacker.HelloProject;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
	// if the player is banned, don't let them join
	// otherwise, send them a greeting
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.isBanned()) {
			player.kickPlayer("You have been banned");
			return;
		}
		
		player.sendMessage("Welcome.");
	}
}
