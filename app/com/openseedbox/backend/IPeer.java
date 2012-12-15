package com.openseedbox.backend;

import com.openseedbox.gson.SerializedAccessorName;
import com.openseedbox.gson.UseAccessor;

/**
 * Represents a peer in a torrent swarm
 * @author Erin Drummond
 */
@UseAccessor
public interface IPeer {
	
	/**
	 * Gets the name of the torrent client the peer is using
	 * @return The name of the client
	 */
	@SerializedAccessorName("client-name")
	public String getClientName();
	
	/**
	 * @return True if we are downloading from this peer 
	 */
	@SerializedAccessorName("downloading-from")
	public boolean isDownloadingFrom();
	
	/**
	 * @return True if we are uploading to this peer 
	 */
	@SerializedAccessorName("uploading-to")
	public boolean isUploadingTo();
	
	/**
	 * @return True if our connection to this peer is encrypted
	 */
	@SerializedAccessorName("encryption-enabled")
	public boolean isEncryptionEnabled();
	
	/**
	 * @return The rate we are downloading from this peer at (in bytes/sec)
	 */
	@SerializedAccessorName("download-rate-bytes")
	public long getDownloadRateBytes();
	
	/**
	 * @return The rate we are uploading to this peer at (in bytes/sec)
	 */
	@SerializedAccessorName("upload-rate-bytes")
	public long getUploadRateBytes();	
}
