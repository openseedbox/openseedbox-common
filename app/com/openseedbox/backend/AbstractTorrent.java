package com.openseedbox.backend;

import org.apache.commons.lang3.StringUtils;

/**
 * An Abstract implementation of the ITorrent interface with common implementations for
 * the more obvious methods that calculate data based on other methods
 * @author Erin Drummond
 */
public abstract class AbstractTorrent implements ITorrent {

	public boolean isRunning() {
		return ((this.getStatus() != TorrentState.ERROR)
				  && (this.getStatus() != TorrentState.PAUSED));
	}

	public boolean isMetadataDownloading() {
		return getMetadataPercentComplete() != 1.0;
	}

	public boolean hasErrorOccured() {
		return !StringUtils.isBlank(getErrorMessage());
	}
	
	public boolean isSeeding() {
		if (isComplete() && getUploadSpeedBytes() > 0) {
			return true;
		}
		return getStatus() == TorrentState.SEEDING;
	}

	public boolean isDownloading() {
		return getStatus() == TorrentState.DOWNLOADING;
	}

	public boolean isPaused() {
		return getStatus() == TorrentState.PAUSED;
	}

	public boolean isComplete() {
		return getPercentComplete() == 1.0;
	}
	
	public double getRatio() {		
		if (getUploadedBytes() == 0) {
			return 0;
		}
		return ((double) getUploadedBytes()) / ((double) getDownloadedBytes());
	}
	
}
