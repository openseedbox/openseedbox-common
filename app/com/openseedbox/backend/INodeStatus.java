package com.openseedbox.backend;

import com.google.gson.annotations.SerializedName;
import com.openseedbox.gson.SerializedAccessorName;
import com.openseedbox.gson.UseAccessor;

/**
 * Describes the status of the physical node that the Backend is running on
 * @author Erin Drummond
 */
@UseAccessor
public interface INodeStatus {
	
	/**
	 * Gets the uptime. Typically the output of the linux `uptime` command.
	 * @return The uptime
	 */	
	@SerializedAccessorName("uptime") public String getUptime();
	
	/**
	 * Gets the free space on the node for downloading torrent to (in bytes).
	 * @return The free space, in bytes
	 */	
	@SerializedAccessorName("free-space-bytes") public long getFreeSpaceBytes();
	
	/**
	 * Gets the used space on the node for downloading torrent to (in bytes).
	 * @return The used space, in bytes
	 */
	@SerializedAccessorName("used-space-bytes") public long getUsedSpaceBytes();
	
	/**
	 * Gets the total space on the node for downloading torrent to (in bytes).
	 * Should be the same as getUsedSpaceBytes() + getFreeSpaceBytes()
	 * @return The total space, in bytes
	 */	
	@SerializedAccessorName("total-space-bytes") public long getTotalSpaceBytes();
	
	/**
	 * Checks to see if the node's specified backend is actually installed
	 * (eg, if the backend was Transmision, check to see if transmission-daemon is installed)
	 * @return Whether or not the backend is installed
	 */
	@SerializedAccessorName("is-backend-installed") public boolean isBackendInstalled();
	
	/**
	 * Gets the base directory that the node has set up as the openseedbox directory
	 * @return The directory
	 */
	@SerializedAccessorName("base-directory") public String getBaseDirectory();
	
	/**
	 * Checks to see if the base directory is writable by the webserver
	 * @return True if it is, False if it isnt
	 */
	@SerializedAccessorName("is-base-directory-writable") public boolean isBaseDirectoryWritable();
	
	/**
	 * Returns the name of the backend running on the node
	 * @return The name, eg "transmission"
	 */
	@SerializedAccessorName("backend-name") public String getBackendName();
	
	/**
	 * Returns the version of the backend running on the node
	 * @return The version, eg "Transmission 2.51"
	 */
	@SerializedAccessorName("backend-version") public String getBackendVersion();
	
	/**
	 * Checks to see if the backend is actually running on the node (eg, if the node
	 * is using the 'transmission' backend, check that transmission-daemon is running
	 * @return True if the backend is running, false if not
	 */
	@SerializedAccessorName("is-backend-running") public boolean isBackendRunning();
	
}
