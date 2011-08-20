// Package Declaration
package me.iffa.styxflight.api.event;

// Bukkit Imports
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Event data for when a player flies.
 * 
 * @author iffa
 * 
 */
public class PlayerFlyEvent extends Event implements Cancellable {
	// Variables
	private static final long serialVersionUID = 8433227981891145320L;
	protected boolean canceled = false;
	protected Player player = null;

	// Constructor
	public PlayerFlyEvent(String event, Player player) {
		super(event);
		this.player = player;
	}

	/**
	 * Gets the player associated with this event.
	 * 
	 * @return player associated with this event
	 */
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public boolean isCancelled() {
		return this.canceled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.canceled = cancel;
	}
}
