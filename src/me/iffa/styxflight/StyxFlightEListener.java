// Package Declaration
package me.iffa.styxflight;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

/**
 * EntityListener that does all the work related to blocking fall damage when
 * flying.
 * 
 * @author iffa
 * 
 */
public class StyxFlightEListener extends EntityListener {
	// Constructor
	public StyxFlightEListener(StyxFlight instance) {
	}

	/**
	 * Called when an entity is damaged.
	 */
	public void onEntityDamage(EntityDamageEvent event) {
		// Cancels the fall damage if the entity is a player and (s)he has
		// flying enabled.
		if (event.getEntity() instanceof Player
				&& event.getCause() == DamageCause.FALL) {
			Player player = (Player) event.getEntity();
			if (StyxFlight.flyingEnabled.get(player)) {
				event.setCancelled(true);
			}
		}
	}
}
