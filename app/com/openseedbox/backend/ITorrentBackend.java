package com.openseedbox.backend;

import java.io.File;

/*
 * Represents a torrent backend interface. The backend is used
 * for downloading torrents.
 */
public interface ITorrentBackend {
	
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
}
