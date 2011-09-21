package com.lithium3141.OpenWarp.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import com.lithium3141.OpenWarp.OWCommand;
import com.lithium3141.OpenWarp.Warp;

public class OWWarpCommand extends OWCommand {

	public OWWarpCommand(JavaPlugin plugin) {
		super(plugin);
		
		this.setName("Warp");
        this.setArgRange(1, 1);
        this.setCommandUsage("/warp {NAME}");
        this.addCommandExample("/warp public");
        this.setPermission("openwarp.warp.use", "Teleport to a warp", PermissionDefault.TRUE);
        this.addKey("warp", 1, 1);
	}

	@Override
	public void runCommand(CommandSender sender, List<String> args) {
	    if(!this.checkPlayerSender(sender)) return;
	    
	    // Locate the warp
	    String warpName = args.get(0);
	    Warp target = this.getPlugin().getWarp(sender, warpName);
	    if(target == null) {
            sender.sendMessage(ChatColor.RED + "No warp found matching name: " + warpName);
            return;
        }
	    
	    // Verify actual permission to access the warp
	    String permString = "openwarp.warp.access.*";
	    if(target.isPublic()) {
	        permString ="openwarp.warp.access.public." + warpName;
	    } else {
	        permString ="openwarp.warp.access.private." + target.getOwner() + "." + warpName;
	    }
	    if(!this.getPlugin().getPermissionsHandler().hasPermission(sender, permString, !target.isPublic())) {
	        sender.sendMessage(ChatColor.RED + "You don't have permission to move to warp: " + warpName);
	        return;
	    }
	    
	    // Move to warp
	    Player player = (Player)sender;
	    if(target.getLocation().getWorld() == null) {
	        sender.sendMessage(ChatColor.RED + "The target location's world is null - this is a bug!");
	    }
	    
        if(!player.teleport(target.getLocation())) {
            player.sendMessage(ChatColor.RED + "Error teleporting to warp: " + warpName);
        }
	}

}