package com.DivineGenesis.BetterBlacklisting;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.item.inventory.ItemStack;

public class Reference 
{
//@Plugin data
    public static final String ID      = "betterblacklisting";
    public static final String NAME    = "BetterBlacklisting";
    public static final String VERSION = "0.4.0";
    public static final String DESC    = "A BetterBlacklisting Plugin for all your blacklisting needs!";
    public static final String AUTHORS = "DrZoddiak & Burpingdog1";
    
//Permissions
    //Admin permissions
    public final static String REMOVE_ITEM       = "betterblacklist.admin.remove";
    public final static String ADD_ITEM          = "betterblacklist.admin.add";
    public final static String RELOAD_CONFIG     = "betterblacklist.admin.reload";
    public final static String BYPASS_BLACKLIST  = "betterblacklist.admin.bypass.blacklist"; 
    public final static String ID_ITEM           = "betterblacklist.admin.identify";
    //User permissions
    public final static String LIST_ITEM  = "betterblacklist.user.list.blacklist"; 
    public final static String HELP_BASE  = "betterblacklist.user.base";
    
//config data
    public static List<String> banlist = new ArrayList<String>();
    
    public static String getID(ItemStack stack)
    {
    	String ID = stack.getItem().getId();
    	DataContainer container = stack.toContainer();
    	DataQuery data = DataQuery.of('/', "UnsafeDamage");
    	int meta = Integer.parseInt(container.get(data).get().toString());
    	if(meta != 0)
    	{
    		ID = ID+":"+meta;
    	}
    	return ID;
    }
    
    public static boolean addList(String item)
    {
    	if(banlist.contains(item))
    		return false;
    	banlist.add(item);
    	BBConfig.savetoFile();
    	return true;
    }
    
    public static boolean removeList(String item)
    {
    	if(!banlist.contains(item))
    		return false;
    	banlist.remove(item);
    	BBConfig.savetoFile();
    	return true;
    }
}
