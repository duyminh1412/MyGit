package vn.com.mbbank.cbs.aml.integration;


import java.io.File;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author minhbd written on Mar 23, 2016
 *
 */
public class JobTask implements Runnable{
	
	private static final Logger logger = Logger.getLogger(JobTask.class.getName());
	private File file;
	private String name; 
	public JobTask(File fileI) {
		this.file = fileI;
		name = fileI.getName();
		logger.log(Level.INFO,"New thread: " + name);
	}
	public void run() {
		try {
			logger.log(Level.INFO,"start with Bat File: "+file);
			String fullPath = file.getPath();
			logger.log(Level.INFO,"full path for bat File: " + fullPath);
			Process p = Runtime.getRuntime().exec(
					"cmd /c start/wait " + fullPath);
			logger.log(Level.INFO,"Importing for batch file: ..."+name);
			int exitVal = p.waitFor();
			logger.log(Level.INFO,"ExitValue: " + exitVal);
			logger.log(Level.INFO,"Batch file import done: "+name);
		} catch (Exception e) {
			logger.log(Level.ERROR, e.toString());
			e.printStackTrace();
		}
	}
}
