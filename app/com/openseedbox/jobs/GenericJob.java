package com.openseedbox.jobs;

import play.jobs.Job;

public abstract class GenericJob extends Job {

	public abstract Object doGenericJob();

	@Override
	public Object doJobWithResult() {
		GenericJobResult res = new GenericJobResult();
		try {
			res.setResult(doGenericJob());
		} catch (Exception ex) {
			res.setError(ex);
		}
		return res;
	}

}
