/**
A subclass of account representing the MoneyMarket subtype
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

public class MoneyMarket extends Account {
	private int withdrawals;
	
	public MoneyMarket(Profile holder, double balance, Date dateOpen, int withdrawals) {
		super(holder, balance, dateOpen);
		this.withdrawals = withdrawals;
	}
	
	/**
	  * method to determine equality of two accounts
	  * @param otherAccount - the account being compared to
	  * @return a boolean indicating if the accounts are equal
	  */
	public boolean equals(Account otherAccount) {
		if( !(otherAccount instanceof MoneyMarket)) {
			return false;
		}else if(!(super.equals(otherAccount))){
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * accessor method to return the withdrawals count
	 * @return an int representing the number of withdrawals
	 */
	public int getWithdrawals() {
		return this.withdrawals;
	}
	
	/**
	 * A method to determine the monthly fee
	 * @return the monthly fee
	 */
	public double monthlyFee() {
		if(this.getBalance() < 2500) {
			return 12;
		}else if(withdrawals > 6){
			return 12;
		}else {
			return 0;
		}
	}
	
	/**
	 * method to get the monthlyInterest
	 * @return the monthly interest
	 */
	public double monthlyInterest() {
		return 0.0065 / 12 * this.getBalance();
		
	}
	/**
	 * Override of toString() method
	 * @return a string containing information about the account
	 */
	public String toString() {
		String str1 = super.toString();
		String str2 = "*MoneyMarket* ";
		String output = str2 + str1;
		
		output += "*";
		output += withdrawals;
		output += " withdrawals";
		return output;
	}
	
	/**
	 * Override of debit method because MoneyMarket is only subclass with special functionality when debit is called, must increment withdrawals
	 * @param amount the amount to be withdrawn
	 */
	public void debit(double amount) {
		super.debit(amount);
		this.withdrawals++;
	}
}