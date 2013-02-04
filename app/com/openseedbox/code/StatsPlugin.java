package com.openseedbox.code;

import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import play.PlayPlugin;
import play.mvc.Http.Header;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import sun.management.ManagementFactory;

public class StatsPlugin extends PlayPlugin {

	long start_time;
	
	OperatingSystemMXBean stats = ManagementFactory.getOperatingSystemMXBean();

	@Override
	public void beforeActionInvocation(Method actionMethod) {
		start_time = System.currentTimeMillis();
		Request.current().args.put("startTime", start_time);		
		Request.current().args.put("loadAverage", stats.getSystemLoadAverage());	
		String host = "Unknown";
		try {
			host = InetAddress.getLocalHost().getHostName();			
		} catch (UnknownHostException e) {
			//ignore
		}
		Request.current().args.put("host", host);
	}
	
	@Override
	public void afterActionInvocation() {
		long time = System.currentTimeMillis() - start_time;
		Header h = new Header("X-Generated-In", "" + time);
		Response.current().headers.put(h.name, h);
	}
	
	
}
