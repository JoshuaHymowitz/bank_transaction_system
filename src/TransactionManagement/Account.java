package TransactionManagement;

import java.text.NumberFormat;

/**
Abstract class that defines the features common to all account types
@author Max Bartlik, Josh Hymowitz
*/

public abstract class Account {
	private Profile holder;
	private double balance;
	private Date dateOpen;
	
	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}
	
	/**
	 * Method to decrease the amount of money in the account by the specified value,
	 * Should only be run if the withdraw method is successful, check for insufficient funds will be there
	 * @param amount of money to be removed
	 */
	public void debit(double amount) {
		this.balance -= amount;
	} 
	
	/**
	 * Method to increase the amount of money in the account by the specified value
	 * 
	 * @param amount of money to be added
	 */
	public void credit(double amount) {
		this.balance += amount;
	} 
	
	/**
	 * Method override of toString()
	 * @return a string containing the profile information, balance, and date opened
	 */
	public String toString() {
		String output = "";
		output += this.holder.getName();
		output += "* ";
		output += NumberFormat.getCurrencyInstance().format(this.balance);
		output += "* ";
		output += this.dateOpen.toString();
		
		return output;
		
	}
	
	public abstract double monthlyInterest();
	public abstract double monthlyFee();
	
	/**
	 * Test whether two accounts are equal by comparing their opened dates, holder profiles, and balances
	 * @param otherAccount
	 * @return true if accounts are equal, false otherwise
	 */
	public boolean equals(Account otherAccount) {
		if(!(this.holder.equals(otherAccount.getProfile()))) {//compare holder Profiles
			return false;
		}
		return true; // they have the same profiles
	}
	/**
	 * accessor method for the date
	 * @return the opened date
	 */
	public Date getDate() {
		return this.dateOpen;
	}
	/**
	 * accessor method for the holder profile
	 * @return the profile for the holder of the account
	 */
	public Profile getProfile() {
		return this.holder;
	}
	/**
	 * accessor method for the balance
	 * @return the balance of the account
	 */
	public double getBalance() {
		return this.balance;
	}
}