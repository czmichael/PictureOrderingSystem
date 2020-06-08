package zhi;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;


/**
 * Class encapsulates meta data such as time taken of a photo
 * 
 * @author Zhi Chen
 * created: 04/23/2016
 * updated: 06/07/2020
 */
public class Photo {
	
	private Path path;
	private Date createdDate;
	private Metadata metadata;
	private String fileName;
	
	public Photo(Path path) {
		this.path = path;
		this.metadata = retrieveMetadata();
		findCreatedDate();
	}

	private Metadata retrieveMetadata() {
		File file = this.path.toFile();
		this.fileName = file.getName();
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(file);
		} catch (Exception e) {
			try {
				System.err.println("file: " + file.getCanonicalPath().toString());
			} catch (IOException e1) {
				throw new RuntimeException(e);
			}
			throw new RuntimeException(e);
		}
		return metadata;
	}
	
	private void findCreatedDate() {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
            	String tagName = tag.getTagName();
            	if (tagName.contains("Date/Time Original")) {

            		String timeTakenString = tag.getDescription();
            		String pattern = "yyyy:MM:dd HH:mm:ss";
            		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            		
            		try {
						this.createdDate = simpleDateFormat.parse(timeTakenString);
					} catch (ParseException e) {
						throw new RuntimeException(e);
					}
            	}
            }
        }
	}
	
	public String getFileName() {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
            	String tagName = tag.getTagName();
            	if (tagName.equals("File Name")) {
            		return tag.getDescription();
            	}
            }
        }
        return null;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}
	
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	public Path getPath() {
		return path;
	}


	public void setPath(Path path) {
		this.path = path;
	}
	
	public String toString() {
		return this.fileName;
	}
}
