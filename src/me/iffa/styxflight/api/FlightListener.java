// Package Declaration
package me.iffa.styxflight.api;

// StyxFlight Imports
import me.iffa.styxflight.api.event.PlayerFlyEvent;

// Bukkit Imports
import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

/**
 * Listener for StyxFlight's events.
 * 
 * @author iffa
 * 
 */
public class FlightListener extends CustomEventListener implements Listener {
	/**
	 * Called when a player attempts to fly.
	 * 
	 * @param event
	 *            Event data
	 */
	public void onPlayerFly(PlayerFlyEvent event) {
	}

	/**
	 * Handles calling the events (like a boss).
	 */
	@Override
	public void onCustomEvent(Event event) {
		if (event instanceof PlayerFlyEvent) {
			onPlayerFly((PlayerFlyEvent) event);
		}
	}
}
