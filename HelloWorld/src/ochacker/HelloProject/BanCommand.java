package ochacker.HelloProject;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// ban {playerName} {duration}
		// {playerName} - args[0]
		// {duration} - args[1]
		if (label.equalsIgnoreCase("kickban")) {
			
			String name = args[0];
			if (!exists(name)) {
				sender.sendMessage("The Player " + name + " does not exist");
			} else {
				// ban the player
				Player toBan = Bukkit.getPlayer(name);
				// UUID uuid = toBan.getUniqueId();
				Bukkit.getBanList(Type.NAME).addBan(name, "", null, "");
				toBan.kickPlayer("");
			}
			return true;
		}
		return false;
	}
	
	private boolean exists(String name) {
		try {
			Bukkit.getPlayer(name);
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
