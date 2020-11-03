/**
A subclass representing the 
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

public class Savings extends Account {
	private boolean isLoyal;
	
	public Savings(Profile holder, double balance, Date dateOpen, boolean isLoyal) {
		super(holder, balance, dateOpen);
		this.isLoyal = isLoyal;
	}
	
	/**
	  * method to determine equality of two accounts
	  * @param otherAccount - the account being compared to
	  * @return a boolean indicating if the accounts are equal
	  */
	public boolean equals(Account otherAccount) {
		if( !(otherAccount instanceof Savings)) { //verify the account being compared is a Savings account
			return false;
		}else if(!(super.equals(otherAccount))){ //invoke the super equals method that already compares date, holder, and balance
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * accessor method to return the loyalty
	 * @return isLoyal, a boolean representing whether the account is loyal
	 */
	public boolean getLoyal() {
		return this.isLoyal;
	}
	
	/**
	 * A method to determine the monthly fee
	 * @return the monthly fee
	 */
	public double monthlyFee() {
		if(this.getBalance() < 300) {
			return 5;
		}else {
			return 0;
		}
	}
	
	/**
	 * method to get the monthlyInterest
	 * @return the monthly interest
	 */
	public double monthlyInterest() {
		if(isLoyal) {
			return 0.0035 / 12 * this.getBalance();
		}else {
			return 0.0025 / 12 * this.getBalance();
		}
		
	}
	
	/**
	 * Override of toString() method
	 * @return a string containing information about the account
	 */
	public String toString() {
		String str1 = super.toString();
		String str2 = "*Savings* ";
		String output = str2 + str1;
		if(isLoyal) {
			output += "*special savings account";
		}
		return output;
	}
}