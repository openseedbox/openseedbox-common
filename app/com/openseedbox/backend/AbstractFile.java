package com.openseedbox.backend;

import com.openseedbox.code.Util;

public abstract class AbstractFile implements IFile {
	
	public double getPercentComplete() {
		return ((double) getBytesCompleted() / getFileSizeBytes());	
	}
	
	public boolean isCompleted() {
		return (getBytesCompleted() == getFileSizeBytes());
	}
	
	public String getNiceFileSize() {
		return Util.getBestRate(getFileSizeBytes());
	}
	
	public String getNicePercentComplete() {
		return Util.formatPercentage(getPercentComplete() * 100) + "%";
	}	
	
}
