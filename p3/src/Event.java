/**
 * This class contains information about an event. The different
 * types of events are described in the Constants interface.
 * @see EventQueue
 * @see Constants
 */
public class Event implements Comparable<Event>
{
	/** The sort of event */
	private int type;

	/** The time at which the event will occur */
	private long time;

	/**
	 * Creates a new event with the given parameters.
	 * @param type	The type of event, as defined in the Constants interface.
	 * @param time	The time at which the event will occur.
	 */
	public Event(int type, long time) {
		this.type = type;
		this.time = time;
	}

	/**
	 * Gets the type of this event.
	 * @return	The type of this event.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the time at which this event will occur.
	 * @return	The time at which this event will occur.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Compares the time of two events. This method is used when sorting
	 * the list of events maintained by the EventQueue class.
	 * @param e	The event to compare this event with.
	 * @return	A negative number if this event occurs before the other event,
	 *			0 if they occur at the same time, and a positive number if the other
	 *			event occurs before this event.
	 * @see	java.lang.Comparable
	 */
	public int compareTo(Event e) {
		return (int)(time - e.time);
	}

	public String toString() {
		String typeStr = null;

		switch(type) {
			case Constants.NEW_PROCESS:
				typeStr = "NEW_PROCESS";
                break;
			case Constants.END_PROCESS:
				typeStr = "END_PROCESS";
                break;
            case Constants.SWITCH_PROCESS:
				typeStr = "SWITCH_PROCESS";
                break;
            case Constants.IO_REQUEST:
				typeStr = "IO_REQUEST";
                break;
            case Constants.END_IO:
				typeStr = "END_IO";
                break;
        }

		return "(" + typeStr + ", " + time + ")";
	}
}
