package com.DivineGenesis.BetterBlacklisting;  

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import com.google.inject.Inject;
import static com.DivineGenesis.BetterBlacklisting.Reference.*;

import java.io.File;

@Plugin(name = NAME, id = ID, version = VERSION, description = DESC	, authors = AUTHORS)
public class Main
{
	private static BBConfig config;
	
	@Inject
    public Main(Logger logger, Game game, @DefaultConfig(sharedRoot = false) File defaultCfg, 
    		@DefaultConfig(sharedRoot = false) ConfigurationLoader<CommentedConfigurationNode> cfgMgr) 
    {
    	config = new BBConfig(logger, game, defaultCfg, cfgMgr, this);
    }
	
	@Listener
	public void onPreInit(GamePreInitializationEvent event)
	{
		config.getLogger().info(String.format("%s - Version:%s - Initializing...",NAME,VERSION));
		config.configCheck();
	}

	@Listener
	public void onInit(GameInitializationEvent event)
	{
		Sponge.getCommandManager().register(this, new CmdLoader().bbl, "BetterBlacklisting","bbl");
		Sponge.getEventManager().registerListeners(this, new EventListener());
		config.getLogger().info(String.format("Initialized! - Your glorified stop sign has been delivered!"));
	}
	
	@Listener
	public void onReload(GameReloadEvent event)
	{
		config.configCheck();
	}

	@Listener
	public void gameStop(GameStoppingServerEvent event)
	{
		config.getLogger().info(String.format("%s - Server stopping? I guess we can too. - Saving...",NAME));
	}
}
