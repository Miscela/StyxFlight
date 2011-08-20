// Package declaration
package me.iffa.styxflight;

// StyxFlight Imports
import me.iffa.styxflight.api.event.PlayerFlyEvent;

//Bukkit Imports
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

/**
 * PlayerListener that does all the work related to flying.
 * 
 * @author iffa
 * 
 */
public class StyxFlightPListener extends PlayerListener {
	public StyxFlightPListener(StyxFlight instance) {
	}

	/**
	 * Called when a player attempts to move.
	 */
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!StyxFlight.flyingEnabled.containsKey(event.getPlayer())) {
			StyxFlight.flyingEnabled.put(event.getPlayer(), false);
		}
		if (!StyxFlight.hoveringEnabled.containsKey(event.getPlayer())) {
			StyxFlight.hoveringEnabled.put(event.getPlayer(), false);
		}
		if (event.isCancelled()
				|| StyxFlight.flyingEnabled.get(event.getPlayer()) == false)
			return;
		Player ps = event.getPlayer();
		Location loc = event.getTo();
		if (ps.getLocation().getBlockX() == loc.getBlockX()
				&& ps.getLocation().getBlockY() == loc.getBlockY()
				&& ps.getLocation().getBlockZ() == loc.getBlockZ()) {
			return;
		}
		// The event was not cancelled and the player had flying enabled, so we
		// make him fly
		Player player = event.getPlayer();
		Location playerLocation = player.getLocation();
		Vector playerDirection = playerLocation.getDirection();
		// Notify listeners.
		PlayerFlyEvent e = new PlayerFlyEvent("PlayerFlyEvent",
				event.getPlayer());
		Bukkit.getServer().getPluginManager().callEvent(e);
		if (e.isCancelled()) {
			return;
		}
		// Flying is continued only if the player is not hovering and the
		// movement was real movement.
		if (!StyxFlight.hoveringEnabled.get(player)) {
			double speed = 2;
			playerDirection.multiply(speed);
			player.setVelocity(playerDirection);
			return;
		}
	}

	/**
	 * Called when a player attempts to sneak (or stop sneaking).
	 */
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		// Returns if the event is canceled or the player is not flying.
		if (event.isCancelled()
				|| StyxFlight.flyingEnabled.get(event.getPlayer()) == false)
			return;
		// The event was not cancelled and the player had flying enabled, so we
		// do something here (maybe freeze the player?)
		if (event.isSneaking()) {
			if (StyxFlight.hoveringEnabled.get(event.getPlayer()) == false) {
				StyxFlight.hoveringEnabled.put(event.getPlayer(), true);
			}

		} else {
			if (StyxFlight.hoveringEnabled.get(event.getPlayer()) == true) {
				StyxFlight.hoveringEnabled.put(event.getPlayer(), false);
			}
		}
	}

	/**
	 * Called when a player interacts with a block.
	 */
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
	}

	/**
	 * Called when a player joins the server.
	 */
	public void onPlayerJoin(PlayerJoinEvent event) {
		// The player didn't have any information in the Map so we put it to the
		// default false.
		if (!StyxFlight.flyingEnabled.containsKey(event.getPlayer())) {
			StyxFlight.flyingEnabled.put(event.getPlayer(), false);
		}
		if (!StyxFlight.hoveringEnabled.containsKey(event.getPlayer())) {
			StyxFlight.hoveringEnabled.put(event.getPlayer(), false);
		}
	}
}