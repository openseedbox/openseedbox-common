package com.openseedbox.backend;

import java.io.File;
import java.util.List;
import java.util.Map;

/*
 * Represents a torrent backend interface. The backend is used
 * for downloading torrents.
 */
public interface ITorrentBackend {
	
	/**
	 * Checks to see if the specified backend is installed on the server
	 * @return  True if its installed, false if it isnt
	 */
	public boolean isInstalled();
	
	/**
	 * Gets the name of this backend
	 * @return The name
	 */
	public String getName();
	
	/**
	 * Gets the version of this backend being run
	 * @return The version
	 */
	public String getVersion();
	
	/**
	 * Starts the torrent backend. Note: if the backend is already running,
	 * this method should do nothing and not throw a 'already running' error.
	 */
	public void start();
	
	/**
	 * Stops the torrent backend. Note: if the backend is already stopped,
	 * this method should do nothing and not throw a 'already running' error.
	 */
	public void stop();
	
	/**
	 * Essentially calls stop() and then start()
	 */
	public void restart();
	
	/**
	 * @return True if the backend is running 
	 */
	public boolean isRunning();
	
	/**
	 * Adds a torrent to the backend based on a torrent file
	 * @param file The torrent file stored locally
	 * @return An ITorrent instance representing the added torrent
	 */
	public ITorrent addTorrent(File file);
	
	/**
	 * Adds a torrent to the backend based on a url or magnet link
	 * @param urlOrMagnet The url or magnet link
	 * @return An ITorrent instance representing the added torrent
	 */
	public ITorrent addTorrent(String urlOrMagnet);
	
	/**
	 * Removes a torrent in the backend based on its info_hash
	 * @param hash The torrent info_hash	 
	 */
	public void removeTorrent(String hash);
	
	/**
	 * Removes several torrents in the backend based on a list of info_hashes
	 * @param hashes The list of info_hashes
	 */
	public void removeTorrent(List<String> hashes);	
	
	/**
	 * Starts a torrent in the backend based on its info_hash
	 * @param hash The torrent info_hash	 
	 */
	public void startTorrent(String hash);
	
	/**
	 * Starts several torrents in the backend based on a list of info_hashes
	 * @param hashes The list of info_hashes
	 */
	public void startTorrent(List<String> hashes);
	
	/**
	 * Stops a torrent in the backend based on its info_hash
	 * @param hash The torrent info_hash
	 */
	public void stopTorrent(String hash);
	
	/**
	 * Stops several torrents in the backend based on a list of info_hashes
	 * @param hashes The list of info_hashes
	 */
	public void stopTorrent(List<String> hashes);
	
	/**
	 * Gets the status of the specified torrent
	 * @param hash The torrent info_hash
	 * @return An ITorrent instance representing the specified torrent
	 */
	public ITorrent torrentStatus(String hash);
	
	/**
	 * Gets the status several torrents based on a list of info_hashes
	 * @param hashes The list of info_hashes
	 * @return A List of ITorrent objects representing the specified torrents
	 */
	public List<ITorrent> torrentStatus(List<String> hashes);	
	
	/**
	 * Gets peer information for the specified torrent
	 * @param hash The torrent hash
	 * @return A list of IPeer objects, each one represents a peer
	 */
	public List<IPeer> getTorrentPeers(String hash);
	
	/**
	 * Gets peer information for the specified torrents
	 * @param hashes The hashes of the torrents you want peer information for
	 * @return A Map, the key is a torrent hash and the value is the list of peers for that torrent
	 */
	public Map<String, List<IPeer>> getTorrentPeers(List<String> hashes);
	
	/**
	 * Gets tracker information for the specified torrent
	 * @param hash The torrent hash
	 * @return A list of ITracker objects, each one represents a tracker
	 */
	public List<ITracker> getTorrentTrackers(String hash);
	
	/**
	 * Gets tracker information for the specified torrents
	 * @param hashes The hashes of the torrents you want tracker information for
	 * @return A Map, the key is a torrent hash and the value is the list of trackers for that torrent
	 */
	public Map<String, List<ITracker>> getTorrentTrackers(List<String> hashes);	
	
	
	/**
	 * Gets file information for the specified torrent
	 * @param hash The torrent hash
	 * @return A list of IFile objects, each one represents a file in the torrent
	 */
	public List<IFile> getTorrentFiles(String hash);
	
	/**
	 * Gets file information for the specified torrents
	 * @param hashes The hashes of the torrents you want tracker information for
	 * @return A Map, the key is a torrent hash and the value is the list of files for that torrent
	 */
	public Map<String, List<IFile>> getTorrentFiles(List<String> hashes);
	
	/**
	 * Modify per-torrent settings
	 * @param hash The hash of the torrent to modify
	 * @param seedRatio The maximum seed ratio before the torrent will pause itself, eg "2.0"
	 * @param uploadLimitBytes The maximum upload speed for the torrent (in bytes/sec, -1 for unlimited)
	 * @param downloadLimitBytes The maximum download speed for the torrent (in bytes/sec, -1 for unlimited)
	 */
	public void modifyTorrent(String hash, double seedRatio, long uploadLimitBytes, long downloadLimitBytes);
	
	/**
	 * Lists all the torrents in the backend
	 * @return A list of all the torrents in the backend
	 */
	public List<ITorrent> listTorrents();
	
	/**
	 * Lists only the torrents that were recently active
	 * (not all backends will support this, but it can save some backend strain while polling)
	 * If the backend you are implementing doesn't support it, just return listTorrents()
	 * @return A list of all the recently active torrents
	 */
	public List<ITorrent> listRecentlyActiveTorrents();
	
	/**
	 * Return session statistics for the backend
	 * @return an ISessionStatistics object representing the statistics
	 */
	public ISessionStatistics getSessionStatistics();
	
	
}
