package com.DivineGenesis.BetterBlacklisting;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent;
import org.spongepowered.api.event.item.inventory.*;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.data.type.*;
import org.spongepowered.api.text.format.TextColors;


public class EventListener 
{
	@Listener
	public void pickup(ChangeInventoryEvent.Pickup event, @First Player player)
	{
		if(!player.hasPermission(Reference.BYPASS_BLACKLIST))
		{
			String itemID = Reference.getID(event.getTargetEntity().getItemType().getTemplate().createStack());
			if(Reference.banlist.contains(itemID))
			{
				event.setCancelled(true);
				player.sendMessage(Text.of(itemID, TextColors.RED, " is banned!"));
				event.getTargetEntity().remove();
			}
		}
	}
	
	@Listener
	public void onUsePriMain(InteractItemEvent.Primary.MainHand event, @First Player player)
	{
		if(!player.hasPermission(Reference.BYPASS_BLACKLIST))
			if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				String itemID = Reference.getID(player.getItemInHand(HandTypes.MAIN_HAND).get());
				if(Reference.banlist.contains(itemID))
				{
					player.setItemInHand(HandTypes.MAIN_HAND, null);
					player.sendMessage(Text.of(itemID, TextColors.RED, " is banned!"));
				}
			}
	}
	
	@Listener
	public void onUseSecMain(InteractItemEvent.Secondary.MainHand event, @First Player player)
	{
		if(!player.hasPermission(Reference.BYPASS_BLACKLIST))
			if(player.getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				String itemID = Reference.getID(player.getItemInHand(HandTypes.MAIN_HAND).get());
				if(Reference.banlist.contains(itemID))
				{
					player.setItemInHand(HandTypes.MAIN_HAND, null);
					player.sendMessage(Text.of(itemID, TextColors.RED, " is banned!"));
				}
			}
	}

	@Listener
	public void onUseSecOff(InteractItemEvent.Primary.OffHand event, @First Player player)
	{
		if(!player.hasPermission(Reference.BYPASS_BLACKLIST))
			if(player.getItemInHand(HandTypes.OFF_HAND).isPresent())
			{
				String itemID = Reference.getID(player.getItemInHand(HandTypes.OFF_HAND).get());
				if(Reference.banlist.contains(itemID))
				{
					player.setItemInHand(HandTypes.OFF_HAND, null);
					player.sendMessage(Text.of(itemID, TextColors.RED, " is banned!"));
				}
			}
	}
	
	@Listener
	public void onUseSecOff(InteractItemEvent.Secondary.OffHand event, @First Player player)
	{
		if(!player.hasPermission(Reference.BYPASS_BLACKLIST))
			if(player.getItemInHand(HandTypes.OFF_HAND).isPresent())
			{
				String itemID = Reference.getID(player.getItemInHand(HandTypes.OFF_HAND).get());
				if(Reference.banlist.contains(itemID))
				{
					player.setItemInHand(HandTypes.OFF_HAND, null);
					player.sendMessage(Text.of(itemID, TextColors.RED, " is banned!"));
				}
			}
	}  
} 