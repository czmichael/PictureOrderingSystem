package zhi;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class App {
	
	private final int INTERVAL = 3;
	
    public static void main( String[] args ) {
    	
	    	App app = new App();
	    	
	    	List<Photo> photoList = new ArrayList<Photo>();
	    	
	    	
	
	    	
	    	String folderString = "/Users/cz_michael/Desktop/1";
	    	Path dir = Paths.get(folderString);
	    	try {
	    		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
	    	    for (Path path: stream) {
	    	    		if (path.toString().contains(".DS_Store") == false) {
	    	    			Photo photo = new Photo(path);
	    	    			photoList.add(photo);    	    			
	    	    		}
	    	    }
	    	    
	    	    
	    	    Collections.sort(photoList, new FileCreationTimeComparator());
	    	    int i = 1;
	    	    Random rand = new Random();
	    	    Integer randInt = rand.nextInt(1000000000);
	    	    for (Photo p: photoList) {
		    	    	String formattedName = randInt.toString() + "_" + String.format("%04d", i);
		    	    	System.out.println(p.getFileName() + " --- "  + p.getCreatedDate());
		    	    	
		    	    	File file = new File(folderString + "/" + formattedName +".JPG");
		    	    	if (file.exists() && ! file.isDirectory()) { 
		    	    	    System.out.print(folderString + "/" + formattedName +".JPG ---- exist." );
		    	    	} else {
		    	    		Files.move(p.getPath(), Paths.get(folderString + "/" + formattedName +".JPG"), StandardCopyOption.REPLACE_EXISTING);
		    	    	}
		    	    	
		    	    	
		    	    	i = i + app.INTERVAL;
	    	    }
	    	} catch (IOException e) {
	    	    // IOException can never be thrown by the iteration.
	    	    // In this snippet, it can only be thrown by newDirectoryStream.
	    	    System.err.println(e);
	    	}
    }
}
