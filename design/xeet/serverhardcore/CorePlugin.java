package design.xeet.serverhardcore;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.plugin.java.JavaPlugin;

public class CorePlugin extends JavaPlugin{
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	/**
    	 * Grabs default world folders, checks if reset flag exists in the world folder
    	 * if it exists, delete the worlds.
    	 * Register the death monitor
    	 * 
    	 * Currently only supports default worlds.
    	 */
    	System.out.println("Hardcore Loaded.");
		File worldPath = new File(getDataFolder(),"../../world");
		File netherPath = new File(getDataFolder(),"../../world_nether");
		File endPath = new File(getDataFolder(),"../../world_the_end");
        File resetFlag = new File(worldPath,"//RESETFLAG");
        if (resetFlag.exists()) {
        	System.out.println("Reset Flag Detect");
        	deleteWorld(worldPath);
        	deleteWorld(netherPath);
        	deleteWorld(endPath);
        }
        getServer().getPluginManager().registerEvents(new DeathTrigger(this), this);
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
    private void newWorlds() {
    	/**
    	 * Unused, previous version generated the worlds but can't do it on startup.
    	 */
		WorldCreator wco = new WorldCreator("world");
		wco.seed(new Random().nextLong());
		wco.environment(World.Environment.NORMAL);
		wco.type(WorldType.NORMAL);
		wco.createWorld();
		WorldCreator wcn = new WorldCreator("world_nether");
		wcn.seed(new Random().nextLong());
		wcn.environment(World.Environment.NETHER);
		wcn.type(WorldType.NORMAL);
		wcn.createWorld();
		WorldCreator wcte = new WorldCreator("world_the_end");
		wcte.seed(new Random().nextLong());
		wcte.environment(World.Environment.THE_END);
		wcte.type(WorldType.NORMAL);
		wcte.createWorld();
	}
	private boolean deleteWorld(File worldPath) {
		/**
		 * Checks if a world exists (end or nether might not have been generated yet)
		 * Recursive if the file is directory
		 * 
		 * Thanks to @ThunderWaffleMC on the Bukkit forums for this snippet
		 * https://bukkit.org/threads/unload-delete-copy-worlds.182814/
		 */
		if (worldPath.exists()) {
			File worldFiles[] = worldPath.listFiles();
			for(int i=0; i < worldFiles.length; i++) {
				try {
					System.out.println("Deleted" + worldFiles[i].getName());
		              if(worldFiles[i].isDirectory()) {
		                  deleteWorld(worldFiles[i]);
		              } else {
		            	  worldFiles[i].delete();
		              }
				}
				catch(Exception e){
					System.out.println(e);
				}
	          }
	      }
        return(worldPath.delete());
	}
}
