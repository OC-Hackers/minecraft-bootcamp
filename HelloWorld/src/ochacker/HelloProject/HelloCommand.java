package ochacker.HelloProject;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelloCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// CommandSender sender is the Entity or Player who issued the command
		// Command cmd is the actual command object
		// String label is the name of the command issued
		// String[] args is the arguments
		// /hello {param1} {param2} 
		
		if (label.equalsIgnoreCase("hello")) {
			sender.sendMessage("Hello!");
			return true;
		}
		return false;
	}
}
