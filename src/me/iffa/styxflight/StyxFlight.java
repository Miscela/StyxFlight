// Package declaration
package me.iffa.styxflight;

// Java Imports
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

// Bukkit Imports
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of StyxFlight, a flying plugin for (Craft)Bukkit.
 * 
 * @author iffa
 * 
 */
public class StyxFlight extends JavaPlugin {
	// Prefix and version variables.
	public static String prefix = "[StyxFlight]";
	public static String version = "0.1";

	// Other important variables.
	public static Logger log = Logger.getLogger("Minecraft");
	public static Map<Player, Boolean> flyingEnabled = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> hoveringEnabled = new HashMap<Player, Boolean>();

	// StyxFlightPlayerListener.
	private final StyxFlightPListener pListener = new StyxFlightPListener(this);
	private final StyxFlightEListener eListener = new StyxFlightEListener(this);

	/**
	 * Called when the plugin is enabled.
	 */
	public void onEnable() {
		// Registering events.
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_MOVE, pListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_TOGGLE_SNEAK, pListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, pListener,
				Event.Priority.Normal, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, eListener,
				Event.Priority.Normal, this);
		log.info(prefix + " Enabled version " + version);
	}

	/**
	 * Called when the plugin is disabled.
	 */
	public void onDisable() {
		log.info(prefix + " Disabled version " + version);
	}

	/**
	 * Called when a player uses the 'sf'-command.
	 */
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		// Changes the flyingEnabled value of the player
		if (sender instanceof Player && args.length == 0) {
			// If the args were empty and the sender is a player.
			Player player = (Player) sender;
			if (flyingEnabled.get(player) == true) {
				flyingEnabled.put(player, false);
				player.sendMessage(ChatColor.GREEN + prefix
						+ " You have disabled flying!");
				return true;
			}
			flyingEnabled.put(player, true);
			player.sendMessage(ChatColor.GREEN + prefix
					+ " You have enabled flying!");
			return true;
		} else if (args.length == 1) {
			if (sender instanceof Player
					&& getServer().getPlayer(args[0]) != null) {
				// If the args were a player and the sender is a player.
				Player player = (Player) sender;
				Player targetPlayer = getServer().getPlayer(args[0]);
				if (flyingEnabled.get(targetPlayer) == true) {
					flyingEnabled.put(targetPlayer, false);
					player.sendMessage(ChatColor.GREEN + prefix
							+ " You have disabled flying for '"
							+ targetPlayer.getDisplayName() + "'");
					targetPlayer.sendMessage(ChatColor.GREEN + prefix + " '"
							+ player.getDisplayName()
							+ "' disabled flying for you!");
					return true;
				}
				flyingEnabled.put(targetPlayer, true);
				player.sendMessage(ChatColor.GREEN + prefix
						+ " You have enabled flying for '"
						+ targetPlayer.getDisplayName() + "'");
				targetPlayer
						.sendMessage(ChatColor.GREEN + prefix + " '"
								+ player.getDisplayName()
								+ "' enabled flying for you!");
				return true;
			} else if (!(sender instanceof Player)
					&& getServer().getPlayer(args[0]) != null) {
				// If the args were a player and the sender is not a player
				// (console?).
				Player targetPlayer = getServer().getPlayer(args[0]);
				if (flyingEnabled.get(targetPlayer) == true) {
					flyingEnabled.put(targetPlayer, false);
					sender.sendMessage(prefix
							+ " You have disabled flying for '"
							+ targetPlayer.getDisplayName() + "'");
					targetPlayer.sendMessage(ChatColor.GREEN + prefix
							+ " An unknown person disabled flying for you!");
					return true;
				}
				flyingEnabled.put(targetPlayer, true);
				sender.sendMessage(prefix + " You have enabled flying for '"
						+ targetPlayer.getDisplayName() + "'");
				targetPlayer.sendMessage(ChatColor.GREEN + prefix
						+ " An unknown person enabled flying for you!");
				return true;
			}
		}
		return true;
	}
}
