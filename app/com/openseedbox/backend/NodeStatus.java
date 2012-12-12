package com.openseedbox.backend;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the status of a node.
 * Is here because openseedbox-server has a /status call and openseedbox-client consumes it,
 * and the object is the same
 * @author Erin Drummond
 */
public class NodeStatus implements INodeStatus {

	private String uptime;
	@SerializedName("free-space-bytes") private long freeSpaceBytes;
	@SerializedName("total-space-bytes") private long totalSpaceBytes;		
	@SerializedName("used-space-bytes") private long usedSpaceBytes;
	@SerializedName("base-directory") private String baseDirectory;
	@SerializedName("is-base-directory-writable") private boolean baseDirectoryWritable;
	@SerializedName("backend-name") private String backendName;
	@SerializedName("backend-version") private String backendVersion;
	@SerializedName("is-backend-running") private boolean backendRunning;
	@SerializedName("is-backend-installed") private boolean backendInstalled;

	public NodeStatus(String uptime, long freeSpace, long totalSpace,
			  String baseDir, boolean baseDirWritable, ITorrentBackend backend) {
		this.uptime = uptime;
		this.freeSpaceBytes = freeSpace;
		this.totalSpaceBytes = totalSpace;	
		this.usedSpaceBytes = totalSpace - freeSpace;
		this.baseDirectory = baseDir;
		this.baseDirectoryWritable = baseDirWritable;
		if (backend != null) {
			this.backendName = backend.getName();
			this.backendVersion = backend.getVersion();
			this.backendRunning = backend.isRunning();
			this.backendInstalled = backend.isInstalled();
		} else {
			this.backendRunning = false;
		}
	}

	public String getUptime() {
		return uptime;
	}

	public long getFreeSpaceBytes() {
		return freeSpaceBytes;
	}

	public long getUsedSpaceBytes() {
		return usedSpaceBytes;
	}

	public long getTotalSpaceBytes() {
		return totalSpaceBytes;
	}

	public boolean isBackendInstalled() {
		return backendInstalled;
	}

	public String getBaseDirectory() {
		return baseDirectory;
	}

	public boolean isBaseDirectoryWritable() {
		return baseDirectoryWritable;
	}

	public String getBackendName() {
		return backendName;
	}

	public String getBackendVersion() {
		return backendVersion;
	}

	public boolean isBackendRunning() {
		return backendRunning;
	}

}
