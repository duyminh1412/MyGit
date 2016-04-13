import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

import vn.com.mbbank.cbs.aml.integration.AMLProcess;
import vn.com.mbbank.cbs.aml.integration.Utils;


public class TestGetBatFile {

	@Test
	public void test() {
		//fail("Not yet implemented");
		Properties props = Utils.loadPros(AMLProcess.class);
		System.out.println(props.getProperty("prefix_Customer"));
		System.out.println(Utils.getBatFiles(props.getProperty("folderBatch_Customer"),
					props.getProperty("prefix_Customer"),
					props.getProperty("suffix_Customer")));
	}

}
