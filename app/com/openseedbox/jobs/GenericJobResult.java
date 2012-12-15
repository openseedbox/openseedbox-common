package com.openseedbox.jobs;

public class GenericJobResult {

	private Exception error;
	private Object result;

	public void setError(Exception error) {
		this.error = error;
	}
	
	public Exception getError() {
		return error;
	}
	
	public boolean hasError() {
		return error != null;
	}
	
	public void setResult(Object o) {
		this.result = o;
	}
	
	public Object getResult() {
		return this.result;
	}
}
