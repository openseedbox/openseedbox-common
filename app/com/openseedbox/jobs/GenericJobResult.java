package com.openseedbox.jobs;

public class GenericJobResult<T> {

	private Exception error;
	private T result;

	public void setError(Exception error) {
		this.error = error;
	}
	
	public Exception getError() {
		return error;
	}
	
	public boolean hasError() {
		return error != null;
	}
	
	public void setResult(T o) {
		this.result = o;
	}
	
	public T getResult() {
		return this.result;
	}
}
