package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class FileUtil {

	public static void createCSVFile(double[] degree, double[] betweenness, double[] closeness, double[] eigenvector) {
		File csvOutputFile = new File("SocNetResult.csv");
		PrintWriter pw = null;
		String [] row = new String[] {"F","SD","SB","SC","SE"};
		String line;
		try {
			pw = new PrintWriter(csvOutputFile);
			line = row[0]+","+row[1]+","+row[2]+","+row[3]+","+row[4];
			pw.println(line);
			int j = 1;
			for(int i = 0 ; i<degree.length; i++) {
				 row[0] = ""+j;
				 row[1] = ""+degree[i];
				 row[2] = ""+betweenness[i];
				 row[3] = ""+closeness[i];
				 row[4] = ""+eigenvector[i];
				 line = row[0]+","+row[1]+","+row[2]+","+row[3]+","+row[4];
				 pw.println(line);
				 j++;
			}
		}catch(IOException e) {
			System.out.println("Error occured");
			e.printStackTrace();
		} finally {
			if(pw!=null) {
				pw.close();
			}
		}
	}
	
	
}
