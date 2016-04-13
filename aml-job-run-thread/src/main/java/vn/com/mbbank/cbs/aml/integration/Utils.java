package vn.com.mbbank.cbs.aml.integration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author minhbd written on Mar 23, 2016
 * 
 */
public class Utils {

	/**
	 * @author minhbd written on Mar 23, 2016
	 * @return properties
	 */
/*	public static Properties loadPros() {
		Properties property = null;
		FileInputStream is = null;
		String file = "D://config/aml-job.properties";
		property = new Properties();
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			property.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return property;
	}*/

	/**
	 * @author minhbd written on Mar 23, 2016
	 * @param clazz
	 * @return
	 */
	public static Properties loadPros(Class<?> clazz) {
		Properties property = null;
		InputStream is = null;
		try {
			property = new Properties();
			is = clazz.getResourceAsStream("/aml-job.properties");
			property.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return property;
	}

	/**
	 * @author minhbd written on Mar 23, 2016
	 * @param folderImport
	 * @param prefix
	 * @param suffix
	 * @return checkFile Import Exit or NotExit
	 */
	public static Boolean checkFile(String folderImport, String prefix,
			String suffix) {
		String pathImport = loadPros(AMLProcess.class).getProperty(folderImport);
		File dir = new File(pathImport);
		final String prefixImport = loadPros(AMLProcess.class).getProperty(prefix);
		final String suffixImport = loadPros(AMLProcess.class).getProperty(suffix);
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return (name.startsWith(prefixImport) && name
						.endsWith(suffixImport));
			}
		};
		File[] foundFiles = dir.listFiles(filter);
		if (foundFiles.length == 0)
			return false;
		else
			return true;
	}

	public static Boolean checkFile(String filePath) {
		File f = new File(filePath);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		return false;
	}

	/**
	 * @author minhbd written on Mar 23, 2016
	 * @param folderBatch
	 * @param prefix
	 * @param suffix
	 * @return list Batch File in Folder Batch
	 */
	public static File[] getBatFiles(String folderBatch, final String prefix,
			final String suffix) {
		File dir = new File(folderBatch);
		FilenameFilter filter = new FilenameFilter() {

			public boolean accept(File dir, String name) {
				return (name.startsWith(prefix) && name.endsWith(suffix));

			}

		};
		File[] foundFiles = dir.listFiles(filter);
		return foundFiles;
	}

	/**
	 * @author minhbd written on Mar 23, 2016
	 * @param foundFiles
	 * Execute Batch File in Batch Folder
	 */
	public static void callBatFile(File[] foundFiles) {
		for (File file : foundFiles) {
			try {
				String fullPath = file.getPath();
				System.out.println("full path: " + fullPath);
				Process p = Runtime.getRuntime().exec(
						"cmd /c start/wait " + fullPath);
				System.out.println("Waiting for batch file ...");
				int exitVal = p.waitFor();
				System.out.println("ExitValue: " + exitVal);
				System.out.println("Batch file done.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
