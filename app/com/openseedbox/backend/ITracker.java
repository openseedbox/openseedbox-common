package com.openseedbox.backend;

import com.openseedbox.gson.SerializedAccessorName;
import com.openseedbox.gson.UseAccessor;

/**
 * Represents a Torrent tracker
 * @author Erin Drummond
 */
@UseAccessor
public interface ITracker {
	
	/**
	 * @return The tracker host name/url
	 */
	@SerializedAccessorName("host")
	public String getHost();
	
	/**	 
	 * @return The tracker announce url
	 */
	@SerializedAccessorName("announce-url")
	public String getAnnounceUrl();
	
	/**
	 * @return The number of leechers downloading this torrent
	 */
	@SerializedAccessorName("leecher-count")
	public int getLeecherCount();
	
	/**
	 * @return The number of seeders seeding this torrent
	 */
	@SerializedAccessorName("seeder-count")
	public int getSeederCount();
	
	/**
	 * @return The number of people who have downloaded this torrent
	 */
	@SerializedAccessorName("download-count")
	public int getDownloadCount();
	
}
