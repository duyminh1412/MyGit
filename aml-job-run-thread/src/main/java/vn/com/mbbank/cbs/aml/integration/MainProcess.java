package vn.com.mbbank.cbs.aml.integration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while (true) {
			System.out.println("### START AML JOB SCANNING IMPORT FILE #####");
			int maxThread = Integer.valueOf(Utils.loadPros(AMLProcess.class).getProperty("maxThread"));
			int interval = Integer.valueOf(Utils.loadPros(AMLProcess.class).getProperty("interval"));
			System.out.println("Config maxThread: "+maxThread+ "  interval: "+interval);
			ExecutorService executorCustomer = Executors.newFixedThreadPool(1);
			Runnable worker = new AMLProcess();
			executorCustomer.execute(worker);
			executorCustomer.shutdown();
			while (!executorCustomer.isTerminated()) {
				//System.out.println("Importing....");
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
