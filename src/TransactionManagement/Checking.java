/**
A subclass of Account, representing the Checking account subtype
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

public class Checking extends Account {
	private boolean directDeposit;
	
	public Checking(Profile holder, double balance, Date dateOpen, boolean directDeposit) {
		super(holder, balance, dateOpen);
		this.directDeposit = directDeposit;
	}
	
	
	 /**
	  * method to determine equality of two accounts
	  * @param otherAccount - the account being compared to
	  * @return a boolean indicating if the accounts are equal
	  */
	public boolean equals(Account otherAccount) {
		if( !(otherAccount instanceof Checking)) { //verify the account being compared is a checking account
			return false;
		}else if(!(super.equals(otherAccount))){ //invoke the super class equals method
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * an accessor methoed to return the directDeposit
	 * @return the directDeposit boolean
	 */
	public boolean getDirectDeposit() {
		return this.directDeposit;
	}
	
	/**
	 * A method to determine the monthly fee
	 * @return the monthly fee
	 */
	public double monthlyFee() {
		if(directDeposit) {
			return 0;
		}
		if(this.getBalance() < 1500) {
			return 25;
		}else {
			return 0;
		}
	}
	
	/**
	 * method to get the monthlyInterest
	 * @return the monthly interest
	 */
	public double monthlyInterest() {
		return 0.0005 / 12 * this.getBalance();
		
	}
	
	/**
	 * Override of toString() method
	 * @return a string containing information about the account
	 */
	public String toString() {
		String str1 = super.toString();
		String str2 = "*Checking* ";
		String output = str2 + str1;
		if(directDeposit) {
			output += "*direct deposit account";
		}
		return output;
	}
	
}
