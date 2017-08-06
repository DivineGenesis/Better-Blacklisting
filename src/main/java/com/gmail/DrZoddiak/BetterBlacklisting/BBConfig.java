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
    private static File defaultCfg;
    private static ConfigurationLoader<CommentedConfigurationNode> cfgMgr;
    private Game game;
    
    public BBConfig(Logger logger, Game game, File defaultCfg, ConfigurationLoader<CommentedConfigurationNode> cfgMgr, 
    		Main instance) 
    {
    	this.logger = logger;
        this.game = game;
        BBConfig.defaultCfg = defaultCfg;
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
    
    public void configCheck()
    {
    	try 
    	{
			cfg = cfgMgr.load();
			if(!defaultCfg.exists())
	    	{
	    		defaultCfg.createNewFile();
	    		cfg.getNode("Banned-list").setValue(new ArrayList<String>(){{add("modid:example:meta");}});
	    	}
			if(cfg.getNode("Banned-list").isVirtual())
				cfg.getNode("Banned-list").setValue(new ArrayList<String>(){{add("modid:example:meta");}});
			banlist = cfg.getNode("Banned-list").getList(TypeToken.of(String.class));
		}
    	catch (Exception e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void savetoFile()
    {
    	try 
    	{
			cfg = getCfgMgr().load();
			if(Reference.banlist.isEmpty())
				cfg.getNode("Banned-list").setValue(new ArrayList<String>());
			cfg.getNode("Banned-list").setValue(Reference.banlist);
			getCfgMgr().save(cfg);
		}
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    }
}
