package com.gmail.DrZoddiak.BetterBlacklisting;

import java.util.List;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

public class CmdLoader 
{
    private CommandSpec add = CommandSpec.builder()
            .description(Text.of("Adds item to banned item list")).executor(this::add).arguments(GenericArguments.onlyOne(GenericArguments
            .string(Text.of("id")))).permission(Reference.ADD_ITEM).build();

    private CommandSpec addHand = CommandSpec.builder()
                .description(Text.of("Adds held item to banned item list")).executor(this::addHand).permission(Reference.ADD_ITEM).build();
        
    private CommandSpec remove = CommandSpec.builder()
            .description(Text.of("Removes item from banned item list")).executor(this::remove).arguments(GenericArguments.onlyOne(GenericArguments
            .string(Text.of("id")))).permission(Reference.REMOVE_ITEM).build();
        
    private CommandSpec removeHand = CommandSpec.builder()
                .description(Text.of("Removes held item from banned item list")).executor(this::removeHand).permission(Reference.REMOVE_ITEM).build();

    private CommandSpec list = CommandSpec.builder()
            .description(Text.of("Show banned items in a list")).executor(this::list).permission(Reference.LIST_ITEM).build();

    private CommandSpec identify = CommandSpec.builder()
                .description(Text.of("Get item ID")).executor(this::identify).permission(Reference.ID_ITEM).build();

    public CommandSpec bbl = CommandSpec.builder()
    		.description(Text.of("Base command")).executor(this::help).permission(Reference.HELP_BASE)
            .child(add, "add")
            .child(addHand, "addhand")
            .child(removeHand, "removehand")
            .child(remove, "remove")
            .child(list, "list")
            .child(identify, "identify")
            .build(); 
    
	public CommandResult add(CommandSource src, CommandContext args) throws CommandException
	{
		String itemID = args.getOne("id").get().toString();
		
		if(!itemID.contains((":")))  //itemID.indexOf((":")) == -1)
			src.sendMessage(Text.of(TextColors.RED, "Incorrect format!"));
		else
		{
			if(Reference.addList(itemID))
				src.sendMessage(Text.of(TextColors.GREEN, "Successfully added ", TextColors.WHITE, itemID, TextColors.GREEN, " to the banlist!"));
			else
				src.sendMessage(Text.of(itemID, TextColors.RED, " already exists in the banlist!"));
		}
		
		return CommandResult.success();
    }
	
	public CommandResult addHand(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				String itemID = Reference.getID(((Player) src).getItemInHand(HandTypes.MAIN_HAND).get());
				if(Reference.addList(itemID))
					src.sendMessage(Text.of(TextColors.GREEN, "Successfully added ", TextColors.WHITE, itemID, TextColors.GREEN, " to the banlist!"));
				else
					src.sendMessage(Text.of(itemID, TextColors.RED, " already exists in the banlist!"));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.RED,"Nothing found in hand!"));
			}
		}
		return CommandResult.success();
    }
	
	public CommandResult remove(CommandSource src, CommandContext args) throws CommandException
	{
		String itemID = args.getOne("id").get().toString();
		
		if(!itemID.contains((":")))  //itemID.indexOf((":")) == -1)
			src.sendMessage(Text.of(TextColors.RED, "Incorrect format!"));
		else
		{
			if(Reference.removeList(itemID))
				src.sendMessage(Text.of(TextColors.GREEN, "Successfully removed ", TextColors.WHITE, itemID, TextColors.GREEN, " to the banlist!"));
			else
				src.sendMessage(Text.of(itemID, TextColors.RED, " doesn't exist in the banlist!"));
		}
		
		return CommandResult.success();
    }
	
	public CommandResult removeHand(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			if(((Player) src).getItemInHand(HandTypes.MAIN_HAND).isPresent())
			{
				String itemID = Reference.getID(((Player) src).getItemInHand(HandTypes.MAIN_HAND).get());
				if(Reference.removeList(itemID))
					src.sendMessage(Text.of(TextColors.GREEN, "Successfully removed ", TextColors.WHITE, itemID, TextColors.GREEN, " to the banlist!"));
				else
					src.sendMessage(Text.of(itemID, TextColors.RED, " doesn't exist in the banlist!"));
			}
			else
			{
				src.sendMessage(Text.of(TextColors.RED,"Nothing found in hand!"));
			}
		}
		return CommandResult.success();
    }
	
	public CommandResult list(CommandSource src, CommandContext args) throws CommandException
	{
		if(Reference.banlist.isEmpty() ) 
        	src.sendMessage(Text.of(TextColors.RED, "Blacklist is empty!"));
        else
        {
        	String list="";
        	
        	for(int i=0; i<Reference.banlist.size();i++)
        	{
        		if(i==0)
        			list = Reference.banlist.get(i);
        		else
        			list = list + "\n"+ Reference.banlist.get(i);
        	} 
        	
        	if(!list.equals(""));

			PaginationList.builder()
					.title(Text.of(TextColors.GREEN, "BetterBlacklist")).padding(Text.of(TextColors.YELLOW, "=")).contents(Text.of(list)).sendTo(src);
        }
        return CommandResult.success();
    }
	
	public CommandResult identify(CommandSource src, CommandContext args) throws CommandException
	{
		if(src instanceof Player)
		{
			src.sendMessage(Text.of(Reference.getID(((Player) src).getItemInHand(HandTypes.MAIN_HAND).get())));
		}
		return CommandResult.success();
    }
	
	public CommandResult help(CommandSource src, CommandContext args) throws CommandException
	{
		List<Text> helpText = Lists.newArrayList(); 
		helpText.add(Text.of(TextColors.GREEN, Text.builder("/bbl add").onClick(TextActions.suggestCommand("/bbl add modID:itemID")),TextColors.GRAY," modID:itemID",TextColors.DARK_GRAY," - ",TextColors.DARK_AQUA,"Is used to add items to banned-item list"));
		helpText.add(Text.of(TextColors.GREEN, Text.builder("/bbl addhand").onClick(TextActions.suggestCommand("/bbl addhand")),TextColors.DARK_GRAY," - ",TextColors.DARK_AQUA,"Is used to add the item in hand to banned item list"));
		helpText.add(Text.of(TextColors.GREEN, Text.builder("/bbl remove").onClick(TextActions.suggestCommand("/bbl remove modID:itemID")),TextColors.GRAY," modID:itemID",TextColors.DARK_GRAY," - ",TextColors.DARK_AQUA,"Is used to remove items from banned-item list"));
		helpText.add(Text.of(TextColors.GREEN, Text.builder("/bbl removehand").onClick(TextActions.suggestCommand("/bbl removehand")),TextColors.DARK_GRAY," - ",TextColors.DARK_AQUA,"Is used to remove the item in hand from banned-item list"));
		helpText.add(Text.of(TextColors.GREEN, Text.builder("/bbl list").onClick(TextActions.runCommand("/bbl list")),TextColors.DARK_GRAY," - ",TextColors.DARK_AQUA,"Shows items that are currently on the banned-item list"));

		PaginationList.builder()
				.title(Text.of(TextColors.GREEN, " BetterBlacklisting Help")).padding(Text.of(TextColors.YELLOW, "=")).contents(helpText).sendTo(src);

		return CommandResult.success();
    }
}
