package com.openseedbox.jobs;

import play.jobs.Job;

public abstract class GenericJob extends Job {

	protected abstract Object doGenericJob() throws Exception;
	
	protected void onException(Exception ex) {
		//do nothing by default
	}

	@Override
	public Object doJobWithResult() throws Exception {
		return runJob();
	}
	
	protected GenericJobResult runJob() throws Exception {
		GenericJobResult res = new GenericJobResult();
		try {
			res.setResult(doGenericJob());
		} catch (Exception ex) {
			this.onException(ex);
			res.setError(ex);
		}
		return res;
	}
		

}
