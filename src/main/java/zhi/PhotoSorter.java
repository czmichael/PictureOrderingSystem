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
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Class to rename and sort photos
 * 
 * @author Zhi Chen
 * created: 04/23/2016
 * updated: 06/07/2020
 */
public class PhotoSorter {
	
	/* 
	 * This is the interval of photo number sequence and is used so that later if manual 
	 * reordering is needed.
	 * Change this if photos from different devices and clocks that were not synchronized. 
	 */
	private static final int INTERVAL = 10;
	
	/**
	 * Recursively collect photos in the current and sub folders. 
	 * 
	 * @param folderString String representation of the path of current folder
	 */
	public void organizePhotos(String folderString) {
		
	    	try {
	    	 	Path dir = Paths.get(folderString);
	    		DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
	    		
	    		List<Photo> photoList = new ArrayList<Photo>();
	    		
	    	    for (Path path: stream) {
    	    	
					File file = new File(path.toString());
					if (file.isDirectory() && file.getAbsolutePath().contains(".DS_Store") == false) { 
						/*
						 * Recursive call to sort and rename all sub folders
						 */
						organizePhotos(file.getAbsolutePath());
					} else if (file.isFile() && file.getAbsolutePath().contains(".DS_Store") == false) {
						Photo photo = new Photo(path);
						if (photo.getCreatedDate() != null) {
							photoList.add(photo); // only add photo with created date info, otherwise the order will be wrong
						}
						   
					} else {
						if (file.getAbsolutePath().contains(".DS_Store") == true) {
							/* Nothing to do */
						} else {
							throw new RuntimeException("Unregonized file or folder: " + file.getAbsolutePath());
						}
					}
	    	    }
	    	    
	    	    if (photoList.size() > 0) {	    	    		
	    	    		sortAndRenamePhotos(folderString, photoList);
	    	    } else {
	    	    		System.out.println(folderString + " contains no photos.");
	    	    }
	    	  
		} catch (IOException e) {
	    	    /*
	    	     *  IOException can never be thrown by the iteration.
	    	     *  In this snippet, it can only be thrown by newDirectoryStream.
	    	     */
	    	    System.err.println(e);
    		}
    	}
	
	/**
	 * Use customized comparator to sort and rename photos in a folder
	 * 
	 * @param folderString String representation of the target folder
	 * @param photoList List of photos in this folder to be sorted and renamed
	 * @throws IOException thrown if renaming has file system issues.
	 */
	public void sortAndRenamePhotos(String folderString, List<Photo> photoList) throws IOException {
		
	    Collections.sort(photoList, new FileCreationTimeComparator());
	    int i = 1;
	    Random rand = new Random();
	    Integer randInt = rand.nextInt(1000000000);
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
	    
	    for (Photo p: photoList) {
	    	Date createdDate = p.getCreatedDate();
	    	String formattedDate = formatter.format(createdDate);
	    	String formattedName = randInt.toString() + "_" + String.format("%04d", i) + "_" + formattedDate; 
	    	System.out.println(p.getFileName() + " --- "  + p.getCreatedDate());
	    	
	    	File file = new File(folderString + "/" + formattedName +".JPG");
	    	if (file.exists() && ! file.isDirectory()) { 
	    	    System.out.print(folderString + "/" + formattedName +".JPG ---- exist." );
	    	} else {
	    		Files.move(p.getPath(), Paths.get(folderString + "/" + formattedName +".JPG"), 
	    														StandardCopyOption.REPLACE_EXISTING);
	    	}
	    	    	
	     	System.out.println(p.getFileName() + " --- "  + p.getCreatedDate());
	    	    	i = i + INTERVAL;
	    }
	}
}
