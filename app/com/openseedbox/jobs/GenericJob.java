package com.openseedbox.jobs;

import play.jobs.Job;

public abstract class GenericJob<T> extends Job<GenericJobResult<T>> {

	protected abstract T doGenericJob() throws Exception;
	
	protected void onException(Exception ex) {
		//do nothing by default
	}

	@Override
	public GenericJobResult<T> doJobWithResult() throws Exception {
		return runJob();
	}
	
	protected GenericJobResult<T> runJob() throws Exception {
		GenericJobResult<T> res = new GenericJobResult<>();
		try {
			res.setResult(doGenericJob());
		} catch (Exception ex) {
			this.onException(ex);
			res.setError(ex);
		}
		return res;
	}
		

}
