 package com.gmail.DrZoddiak.BetterBlacklisting;

import static com.gmail.DrZoddiak.BetterBlacklisting.Reference.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

public class BBConfig 
{
    public Main instance;
    private Logger logger; 
    private static ConfigurationNode cfg;
    private File defaultCfg;
    private static ConfigurationLoader<CommentedConfigurationNode> cfgMgr;
    private Game game;
    
    public BBConfig(Logger logger, Game game, File defaultCfg, ConfigurationLoader<CommentedConfigurationNode> cfgMgr, 
    		Main instance) 
    {
    	this.logger = logger;
        this.game = game;
        this.defaultCfg = defaultCfg;
        BBConfig.cfgMgr = cfgMgr;
        this.instance = instance;
    }
    
    public static ConfigurationLoader<CommentedConfigurationNode> getCfgMgr() 
    {
        return cfgMgr;
    }

    public Logger getLogger() 
    {
        return logger;
    }

    public ConfigurationNode getCfg() 
    {
        return cfg;
    }

    public Game getGame() 
    {
        return game;
    }
    
    public static void savetoFile()
    {
    	try 
    	{
			cfg = getCfgMgr().load();
			cfg.getNode("Banned-list").setValue(Reference.banlist);
			getCfgMgr().save(cfg);
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
    
    public void configCheck()
    {
    	getLogger().info("Checking config...");
        try 
        {
        	cfg = getCfgMgr().load();
            if (!defaultCfg.exists()) 
            {
                getLogger().info("Config not yet created... Don't worry, we got that covered!");
                getLogger().info("Creating config...");
                defaultCfg.createNewFile();
                cfg = getCfgMgr().load();
                cfg.getNode("Banned-list").setValue(new ArrayList<String>(){{add("modid:example:1");}});
                getLogger().info("Config created.");
                getCfgMgr().save(cfg);
            }
            
				getLogger().info("Saving config data into variables!");
				
				if(cfg.getNode("Banned-list").isVirtual())
					cfg.getNode("Banned-list").setValue(new ArrayList<String>(){{add("modid:example:1");}});
				banlist = cfg.getNode("Banned-list").getList(TypeToken.of(String.class));
				getCfgMgr().save(cfg);
            	getLogger().info("Yay! data was saved :D");
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        }
    }

}