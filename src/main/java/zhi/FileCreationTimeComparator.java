package zhi;

import java.util.Comparator;
import java.util.Date;


public class FileCreationTimeComparator implements Comparator<Photo> {

	public int compare(Photo o1, Photo o2) {
		Date dateA = o1.getCreatedDate();
		Date dateB = o2.getCreatedDate();
		if (dateA != null && dateB != null) {
			return dateA.compareTo(dateB);
		}
		return -1;
		
	}

}
