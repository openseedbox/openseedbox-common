package com.openseedbox.backend;

/**
 * Describes the status of the physical node that the Backend is running on
 * @author Erin Drummond
 */
public interface INodeStatus {
	
	/**
	 * Gets the uptime. Typically the output of the linux `uptime` command.
	 * @return The uptime
	 */
	public String getUptime();
	
	/**
	 * Gets the free space on the node for downloading torrent to (in bytes).
	 * @return The free space, in bytes
	 */
	public long getFreeSpaceBytes();
	
	/**
	 * Gets the used space on the node for downloading torrent to (in bytes).
	 * @return The used space, in bytes
	 */
	public long getUsedSpaceBytes();
	
	/**
	 * Gets the total space on the node for downloading torrent to (in bytes).
	 * Should be the same as getUsedSpaceBytes() + getFreeSpaceBytes()
	 * @return The total space, in bytes
	 */	
	public long getTotalSpaceBytes();
	
	/**
	 * Checks to see if the node's specified backend is actually installed
	 * (eg, if the backend was Transmision, check to see if transmission-daemon is installed)
	 * @return Whether or not the backend is installed
	 */
	public boolean isBackendInstalled();
	
	/**
	 * Gets the base directory that the node has set up as the openseedbox directory
	 * @return The directory
	 */
	public String getBaseDirectory();
	
	/**
	 * Checks to see if the base directory is writable by the webserver
	 * @return True if it is, False if it isnt
	 */
	public boolean isBaseDirectoryWritable();
	
	/**
	 * Returns the name of the backend running on the node
	 * @return The name, eg "transmission"
	 */
	public String getBackendName();
	
	/**
	 * Returns the version of the backend running on the node
	 * @return The version, eg "Transmission 2.51"
	 */
	public String getBackendVersion();
	
	/**
	 * Checks to see if the backend is actually running on the node (eg, if the node
	 * is using the 'transmission' backend, check that transmission-daemon is running
	 * @return True if the backend is running, false if not
	 */
	public boolean isBackendRunning();
	
}
