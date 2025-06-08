package zhi;

import java.util.Comparator;
import java.util.Date;


/**
 * Customized comparator for photos
 * 
 * @author Zhi Chen
 * created: 04/23/2016
 * updated: 06/07/2020
 */
public class FileCreationTimeComparator implements Comparator<Photo> {

	/**
	 * Compare photos based on the take they were taken
	 */
	public int compare(Photo o1, Photo o2) {
		Date dateA = o1.getCreatedDate();
		Date dateB = o2.getCreatedDate();
		if (dateA != null && dateB != null) {
			return dateA.compareTo(dateB);
		} else {
			if (dateA == null) {
				throw new RuntimeException(o1 + " created date is null");
			}
			if (dateB == null) {
				throw new RuntimeException(o2 + " created date is null");
			}
		}
		return -1;
	}
}
