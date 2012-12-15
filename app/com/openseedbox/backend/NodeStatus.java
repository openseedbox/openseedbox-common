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
	private long freeSpaceBytes;
	private long totalSpaceBytes;		
	private long usedSpaceBytes;
	private String baseDirectory;
	private boolean baseDirectoryWritable;
	private String backendName;
	private String backendVersion;
	private boolean backendRunning;
	private boolean backendInstalled;

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

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public void setFreeSpaceBytes(long freeSpaceBytes) {
		this.freeSpaceBytes = freeSpaceBytes;
	}

	public void setTotalSpaceBytes(long totalSpaceBytes) {
		this.totalSpaceBytes = totalSpaceBytes;
	}

	public void setUsedSpaceBytes(long usedSpaceBytes) {
		this.usedSpaceBytes = usedSpaceBytes;
	}

	public void setBaseDirectory(String baseDirectory) {
		this.baseDirectory = baseDirectory;
	}

	public void setBaseDirectoryWritable(boolean baseDirectoryWritable) {
		this.baseDirectoryWritable = baseDirectoryWritable;
	}

	public void setBackendName(String backendName) {
		this.backendName = backendName;
	}

	public void setBackendVersion(String backendVersion) {
		this.backendVersion = backendVersion;
	}

	public void setBackendRunning(boolean backendRunning) {
		this.backendRunning = backendRunning;
	}

	public void setBackendInstalled(boolean backendInstalled) {
		this.backendInstalled = backendInstalled;
	}
}
