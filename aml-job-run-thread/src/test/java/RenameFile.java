import static org.junit.Assert.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;


public class RenameFile {

	@Test
	public void test() {
//		fail("Not yet implemented");
		File oldfile =new File("D://oldfile.txt");
		File newfile =new File("D://newfile.txt");
		String date = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
		System.out.println(date);
		if(oldfile.renameTo(newfile)){
			System.out.println("Rename succesful");
		}else{
			System.out.println("Rename failed");
		}
	}

}
