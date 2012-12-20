package com.openseedbox.backend;

import com.openseedbox.gson.SerializedAccessorName;
import com.openseedbox.gson.UseAccessor;
import java.util.List;


/**
 * Represents a torrent
 * @author Erin Drummond
 */
@UseAccessor
public interface ITorrent {
	
	/**
	 * Gets the name of the torrent
	 * @return The torrent name
	 */
	@SerializedAccessorName("name") public String getName();
	
	/**
	 * This is mainly used for the UI to look different when a torrent isnt running
	 * A torrent is considered running if its not paused or queued (ie, its downloading or seeding)
	 * @return True if the torrent is running, false otherwise
	 */
	@SerializedAccessorName("is-running") public boolean isRunning();
	
	/**	 
	 * Check to see if the torrent is in seed mode (ie, we have 100% of the data)
	 * @return True if the torrent is seeding, false otherwise
	 */
	@SerializedAccessorName("is-seeding") public boolean isSeeding();
	
	/**
	 * Check to see if the torrent is in download mode (ie, we dont yet have 100% of the data)
	 * @return True if the torrent is downloading, false otherwise
	 */
	@SerializedAccessorName("is-downloading") public boolean isDownloading();
	
	/**
	 * Check to see if the torrent is paused (ie, we arent downloading anything)
	 * @return True if the torrent is paused, false otherwise
	 */
	@SerializedAccessorName("is-paused") public boolean isPaused();
	
	/**
	 * Checks to see if the torrent is complete (ie, all data downloaded)
	 * @return True if all data downloaded, false if not
	 */
	@SerializedAccessorName("is-complete") public boolean isComplete();
	
	/**
	 * Gets the percent complete of the metadata (eg, if the torrent was being downloaded from a magnet)
	 * Should return 100 if metdata has finished downloading (ie, the backend has the whole torrent)
	 * @return The percent complete of the metadata, between 0 and 100
	 */
	@SerializedAccessorName("metadata-percent-complete") public double getMetadataPercentComplete();
	
	/**	 
	 * @return True if the torrent is a magnet and its metadata is still downloading, false if not
	 */
	@SerializedAccessorName("metadata-downloading") public boolean isMetadataDownloading();
	
	/**
	 * Gets the percentage completed of this torrent
	 * @return The percent complete, between 0 and 100
	 */
	@SerializedAccessorName("percent-complete") public double getPercentComplete();
	
	/**
	 * Gets the download speed of this torrent, in bytes/sec
	 * @return The download speed, in bytes/sec
	 */
	@SerializedAccessorName("download-speed-bytes") public long getDownloadSpeedBytes();
	
	/**
	 * Gets the upload speed of this torrent, in bytes/sec
	 * @return The upload speed, in bytes/sec
	 */
	@SerializedAccessorName("upload-speed-bytes") public long getUploadSpeedBytes();
	
	/**
	 * Gets the info_hash of this torrent
	 * @return The info_hash
	 */
	@SerializedAccessorName("torrent-hash") public String getTorrentHash();
	
	/**
	 * Returns true if the torrent is in an error state on the backend (eg,
	 * run out of disk space etc)
	 * @return True if in error state, false if otherwise
	 */
	@SerializedAccessorName("error-occured") public boolean hasErrorOccured();
	
	/**
	 * Returns an error message if the torrent is in error state, or null
	 * if the torrent is fine
	 * @return An error message, or null if there is no error
	 */
	@SerializedAccessorName("error-message") public String getErrorMessage();
	
	/**
	 * Gets the total file size of the torrent, in bytes
	 * @return The total size of the torrent in bytes
	 */
	@SerializedAccessorName("total-size-bytes") public long getTotalSizeBytes();
	
	/**
	 * Gets the amount of data that has been currently downloaded, in bytes
	 * @return The amount downloaded in bytes
	 */
	@SerializedAccessorName("downloaded-bytes") public long getDownloadedBytes();
	
	/**
	 * Gets the amount of data that has been uploaded, in bytes
	 * @return The amount uploaded, in bytes
	 */
	@SerializedAccessorName("uploaded-bytes") public long getUploadedBytes();
	
	/**
	 * Gets the current status of the torrent
	 * @return a @TorrentState object representing the status
	 */
	@SerializedAccessorName("status") public TorrentState getStatus();
	
	/**
	 * Gets a list of the files in a torrent
	 * @return The list of files
	 */
	@SerializedAccessorName("files") public List<IFile> getFiles();
	
	/**
	 * Gets a list of the peers in a torrent
	 * @return The list of peers
	 */
	@SerializedAccessorName("peers") public List<IPeer> getPeers();
	
	/**
	 * Gets a list of the trackers in a torrent
	 * @return The list of trackers
	 */
	@SerializedAccessorName("trackers") public List<ITracker> getTrackers();
	
	/**
	 * Gets a download link that the user can paste into their browser and
	 * have it download the contents of the torrent, as a zip
	 * @return The zip download link
	 */
	@SerializedAccessorName("zip-download-link") public String getZipDownloadLink();
	
}
