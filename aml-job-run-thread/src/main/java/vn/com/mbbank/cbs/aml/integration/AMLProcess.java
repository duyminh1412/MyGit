package vn.com.mbbank.cbs.aml.integration;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 * @author minhbd
 *
 */
public class AMLProcess implements Runnable {

	private static Logger logger = Logger.getLogger(AMLProcess.class.getName());
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		logger.log(Level.INFO, "Start AML Import Scanning Job");
		Properties props = Utils.loadPros(AMLProcess.class);
		int maxThread = Integer.valueOf(Utils.loadPros(AMLProcess.class).getProperty("maxThread"));
		ExecutorService executorCustomer = Executors.newFixedThreadPool(maxThread);
		ExecutorService executorAccount = Executors.newFixedThreadPool(maxThread);
		ExecutorService executorTransaction = Executors.newFixedThreadPool(maxThread);
		
		Boolean checkConditionRun = false;
		String pathImport_Customer = props.getProperty("pathImport_Customer");
		String pathImport_Account = props.getProperty("pathImport_Account");
		String pathImport_Transaction = props.getProperty("pathImport_Transaction");
		checkConditionRun = (Utils.checkFile(pathImport_Customer)
				&& Utils.checkFile(pathImport_Account)
				&& Utils.checkFile(pathImport_Transaction)); 
		logger.log(Level.INFO, "check Condition Run: "+checkConditionRun);
		if (checkConditionRun == true){
			// import Customer
			logger.log(Level.INFO,"Start Import Customer Table");
			File[] listBatFilesCustomer = Utils.getBatFiles(props.getProperty("folderBatch_Customer"),
					props.getProperty("prefix_Customer"),
					props.getProperty("suffix_Customer"));
			for (File file : listBatFilesCustomer) {
				logger.log(Level.INFO,"start file: "+file.getName());
				Runnable worker = new JobTask(file);
				executorCustomer.execute(worker);
			}
			executorCustomer.shutdown();
			
			while (!executorCustomer.isTerminated()) {
				 
			}
			logger.log(Level.INFO,"\nFinished all threads import Customer");
			
			// import Account
			logger.log(Level.INFO,"Start Import Account Table");
			File[] listBatFilesAccount = Utils.getBatFiles(props.getProperty("folderBatch_Account"),
					props.getProperty("prefix_Account"),
					props.getProperty("suffix_Account"));
			for (File file : listBatFilesAccount) {
				Runnable worker = new JobTask(file);
				executorAccount.execute(worker);
			}
			executorAccount.shutdown();
			
			while (!executorAccount.isTerminated()) {
				 
			}
			logger.log(Level.INFO,"\nFinished all threads import Account");
			
			// import Transaction
			logger.log(Level.INFO,"Start Import Transaction Table");
			File[] listBatFilesTransaction = Utils.getBatFiles(props.getProperty("folderBatch_Transaction"),
					props.getProperty("prefix_Transaction"),
					props.getProperty("suffix_Transaction"));
			for (File file : listBatFilesTransaction) {
				Runnable worker = new JobTask(file);
				executorTransaction.execute(worker);
			}
			executorTransaction.shutdown();
			
			while (!executorTransaction.isTerminated()) {
				 
			}
			logger.log(Level.INFO,"\nFinished all threads import Transaction");
			//Rename File
			String dateString = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
			
			File customerFile = new File(pathImport_Customer);
			File customerFileNew = new File(pathImport_Customer+"_"+dateString);
			customerFile.renameTo(customerFileNew);
			
			File accountFile = new File(pathImport_Account);
			File accountFileNew = new File(pathImport_Account+"_"+dateString);
			accountFile.renameTo(accountFileNew);
			
			File transactionFile = new File(pathImport_Transaction);
			File transactionFileNew = new File(pathImport_Transaction+"_"+dateString);
			transactionFile.renameTo(transactionFileNew);
			
			logger.log(Level.INFO, "Rename File Succesfully");
		}
		
		logger.log(Level.INFO,"Main thread exiting.");
	}
}
