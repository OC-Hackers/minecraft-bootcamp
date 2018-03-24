package ochacker.HelloProject;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class HelloProject extends JavaPlugin {
	// Logger log is the built in Java LOGGER that allows us to send messages to the console
	Logger log = this.getLogger();
	
	// onEnable
	@Override
	public void onEnable() {
		log.log(Level.INFO, "Hello, world!");
		
		this.getCommand("hello").setExecutor(new HelloCommand());
		// this.getCommand("kickban").setExecutor(new BanCommand());
		
		// registering the event
		this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
	}
	
	// onDisable
	@Override
	public void onDisable() {
		
	}
}
