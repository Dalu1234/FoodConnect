/**
 * Class: TestFilePaths
 * @author : Chukwudalu Dumebi-Kachikwu
 * @created 11/21/2024
 */
import java.io.File;
public class TestFilePaths {
		
	    public static void main(String[] args) {
	        File file1 = new File("src/data/community_food_organizations.txt");
	        File file2 = new File("src/data/volunteers.txt");

	        System.out.println("File 1 exists: " + file1.exists());
	        System.out.println("File 2 exists: " + file2.exists());	    }
	

}