package com.openseedbox.backend;

import com.openseedbox.gson.SerializedAccessorName;
import com.openseedbox.gson.UseAccessor;

/**
 * Represents a file in a torrent
 * @author Erin Drummond
 */
@UseAccessor
public interface IFile {
	
	/**
	 * Returns the ID that the backend uses to identify the file by
	 * @return The file ID
	 */
	@SerializedAccessorName("id")
	public String getId();	
	
	/**
	 * The name of the file, eg MyVideo.mkv
	 * @return The file name
	 */
	@SerializedAccessorName("name")
	public String getName();
	
	/**
	 * The full path of the file in the torrent directory structure,
	 * eg /videos/mkv/MyVideo.mkv
	 * @return The file full path
	 */
	@SerializedAccessorName("full-path")
	public String getFullPath();
	
	/**
	 * Users can select/deselect files from torrents. If the file
	 * is wanted, the user has selected it, otherwise they have
	 * deselected it.
	 * @return Whether or not the file is wanted
	 */
	@SerializedAccessorName("wanted")
	public boolean isWanted();
	
	/**	 
	 * @return True if the file has finished downloading, false if it hasn't
	 */
	@SerializedAccessorName("is-completed")
	public boolean isCompleted();
	
	/**
	 * Get the download progress of this file, in bytes
	 * @return The bytes downloaded
	 */
	@SerializedAccessorName("completed-bytes")
	public long getBytesCompleted();
	
	/**
	 * Get the total size of this file, in bytes
	 * @return The total size of the file
	 */
	@SerializedAccessorName("file-size-bytes")
	public long getFileSizeBytes();
	
	/**
	 * Gets the percent complete of the file
	 * @return The percent, as a number between 0 and 1
	 */
	@SerializedAccessorName("percent-complete")
	public double getPercentComplete();
	
	/**
	 * Users can set the download priority of a file. Higher numbers will be downloaded
	 * first. This method should return -1, 0 or 1 where:
	 *  -1 = Low Priority
	 *   0 = Normal Priority
	 *   1 = High Priority
	 * @return The priority of this file
	 */
	@SerializedAccessorName("priority")
	public int getPriority();
	
	/**
	 * Gets the download link to this file
	 * @return A download link that the user can paste into their browser and have work
	 */
	@SerializedAccessorName("download-link")
	public String getDownloadLink();
	
}
