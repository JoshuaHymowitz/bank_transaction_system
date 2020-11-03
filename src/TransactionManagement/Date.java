/**
Class to represent a date
Parses date in string format to assign integer values to day, month, year
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

public class Date implements Comparable<Date> {
	
	private int year;
	private int month;
	private int day;
	
	/**
	Constructor for a date object
	Takes a string and parses to assign day, month, year to the instance
	@param date string in format dd/mm/yyyy
	 */
	public Date(String date) {
		
		boolean newInt = true; 
		int valuesAdded = 0;
		// iterate through the string and pull integer values from it
		for(int i = 0; i < date.length(); i++) {
			if(date.charAt(i) == '/') {
				newInt = true;
				continue;
			}
			if(newInt) {
				if(date.charAt(i+1) == '/') { // next is a slash, set this int
					switch(valuesAdded) {
						case 0:
							this.month = Character.getNumericValue(date.charAt(i));
							valuesAdded++;
							break;
						case 1:
							this.day = Character.getNumericValue(date.charAt(i));
							valuesAdded++;
							break;
					}	
		
				}
				newInt = false;
				continue;
				
			}
			else { // this is the second digit of an int
				switch(valuesAdded) {
					case 0: // assign month
						this.month = Character.getNumericValue(date.charAt(i-1))*10 + Character.getNumericValue(date.charAt(i));
						valuesAdded++;
						newInt = true;
						break;
					case 1: // assign day
						this.day = Character.getNumericValue(date.charAt(i-1))*10 + Character.getNumericValue(date.charAt(i));
						valuesAdded++;
						newInt = true;
						break;
					case 2: // assign year
						if(i == date.length() - 1) {
							this.year = Character.getNumericValue(date.charAt(i-3))*1000 + Character.getNumericValue(date.charAt(i-2))*100 + Character.getNumericValue(date.charAt(i-1))*10 + Character.getNumericValue(date.charAt(i));
						}	
				}
				
			}
		}
	}
	
	/**
	Compares two dates and returns a value representing which date is later in time.
	@param date a date object to be compared
	@return -1 if the date this is called on is earlier than the parameter date
			0 if they are equal
			1 if the first date is later
	*/
	public int compareTo(Date date) { 
		// compare year then month then year
		// if any of them is greater then return -1 or 1
		if(this.year > date.year) {
			return 1;
		}
		else if (this.year < date.year) {
			return -1;
		}
		if(this.month > date.month) {
			return 1;
		}
		else if(this.month < date.month) {
			return -1;
		}
		if(this.day > date.day) {
			return 1;
		}
		else if(this.day < date.day) {
			return -1;
		}
		return 0; // the dates must be equal
		
	} 
	/**
	Stringifies a date object in the format mm/dd/yyyy
	@return string in the format above
	*/
	public String toString() { 
		
		String res = "";
		
		if(this.month <= 9) {
			res += "0"; // need a zero in front of a single digit
		}
		res += this.month;
		res += "/";
		
		if(this.day <= 9) {
			res += "0"; // single digit add 0
		}
		res += this.day;
		res += "/";
		
		res += this.year;
		
		return res;
	} 
	
	/**
	Check to see if a date object has valid values
	Year must be less than 2021, month must be 1-12, day must be 1-31
	@return boolean representing the validity of the date
	*/
	public boolean isValid() { 
		
		if(this.year > 2020) {
			return false;
		}
		if(this.day < 1 || this.day > 31) {
			return false;
		}
		if(this.month < 1 || this.month > 12) {
			return false;
		}
		if(this.month == 2 || this.month == 4 || this.month == 6 || this.month == 9 || this.month == 11) {
			if(this.day == 31) { // these months can't have 31 days
				return false;
			}
		}
		if(this.month == 2) { // february can't have 30 days
			if(this.day > 29) {
				return false;
			}
			if(this.day == 29 && this.year % 4 != 0) { // if feb has 29 days make sure it's leap year
				return false; // day can only be 29 if leap year
			}
		}
		
		return true;
	}
	
}


