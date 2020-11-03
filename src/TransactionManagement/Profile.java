/**
A class representing the profile of the person who opens an account
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

public class Profile {
	private String fname;
	private String lname;
	
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}
	/**
	 * accessor method to return a string containing a concatentaion of the first and last name
	 * 
	 * @return string containing the full name
	 */
	public String getName() {
		String output = "";
		output += fname;
		output += " ";
		output += lname;
		return output;
	}
	
	/**
	 * method to determine equality of two profiles
	 * @param otherProfile
	 * @return boolean indicating if the profiles are equal
	 */
	public boolean equals(Profile otherProfile) {
		if(this.getName().equals(otherProfile.getName())) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * accessor method to get the last name
	 * @return the string containing the last name
	 */
	public String getLastName() {
		return this.lname;
	}
}