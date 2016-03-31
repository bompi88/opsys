public interface Constants
{
	/** Event type describing the arrival of a new process */
	int NEW_PROCESS = 1;

	/** Event type describing the completion of the active process */
	int END_PROCESS = 2;

	/** Event type describing a process switch due to the completion of a RR time quant */
	int SWITCH_PROCESS = 3;

	/** Event type describing the need for the active process to perform I/O */
	int IO_REQUEST = 4;

	/** Event type describing the end of the current I/O operation */
	int END_IO = 5;

	/** The orientation SOUTH */
	int SOUTH = 0;

	/** The orientation WEST */
	int WEST = 1;

	/** The orientation EAST */
	int EAST = 3;
}
