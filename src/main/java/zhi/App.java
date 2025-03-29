package zhi;


/**
 * This is an application to sort and rename photos in a folder and its sub folders
 * according to chronological order, so that all photos even from different cameras
 * can be put into a folder, and resulting renamed photos are named by the time its 
 * taken.
 * 
 * @author Zhi Chen
 * created: 04/23/2016
 * updated: 06/07/2020
 */
public class App {
	
	/**
	 * Application entrance - Main
	 * @param args
	 */
    public static void main( String[] args ) {
    	
    		String folderString = "/Users/zhichen/Desktop/1";
    		PhotoSorter photoSorter = new PhotoSorter();
    		photoSorter.organizePhotos(folderString);
    }
}
