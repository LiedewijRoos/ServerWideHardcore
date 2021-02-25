package design.xeet.serverhardcore;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import design.xeet.serverhardcore.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class DeathTrigger implements Listener {
	private final CorePlugin plugin;
	public DeathTrigger(CorePlugin corePlugin) {
		this.plugin = corePlugin; //Unnecessary in current iteration. 
	}


	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		/**
		 * Fired on a Player death, kicks players with a shame message about who died.
		 * Grabs the world folder, creates a RESETFLAG file that is detected on server startup
		 * before postworld, that makes the CorePlugin deletes the world folders.
		 * It then restarts the server, only works with a ./start.sh or some other restart script 
		 * in spigot.yml
		 */
		String kickMessage = event.getEntity().getDisplayName() + " died, the server is resetting.";
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			
			player.kickPlayer(kickMessage);
		}
        World world= Bukkit.getWorld("world");
        File worldPath = world.getWorldFolder();
        File resetFlag = new File(worldPath,"//RESETFLAG");
        try {
			if (resetFlag.createNewFile()) {
			    System.out.println("RESET FLAG " + resetFlag.getName());
			  } else {
			    System.out.println("ALREADY FLAGGED ERROR"); 
			  }
		} catch (IOException e) {
			System.out.println("OOPSIE DOOPSIE OWO ONO FUCKY WUUCKY >.<");
		}
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "restart");
	}
}
