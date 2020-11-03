/**
Container class that manages the array containing all Account instances
@author Max Bartlik, Josh Hymowitz
*/
package TransactionManagement;

import java.text.NumberFormat;

public class AccountDatabase {
	private Account[] accounts;
	private int size;
	
	public AccountDatabase() {
		this.size = 0;
		this.accounts = new Account[5];
		
	}
	/**
	 * A method to find a given account within the array
	 * @param account - the account we are trying to find in the array
	 * @return index of the account in the array, -1 if not found
	 */
	private int find(Account account) {
		for(int i = 0; i < size; i++) {
			if(this.accounts[i].equals(account)) {
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Method to grow the array that manages the accounts, by making a new array that is 5 spaces bigger, tranferring all existing accounts to it, and replacing the original array
	 */
	private void grow() {
		
		Account[] newDB = new Account[size + 5];
		for(int i = 0; i < size; i++) {
			newDB[i] = this.accounts[i];
		}
		this.accounts = newDB;
	}
	
	/**
	 * A method to add a new account to the database
	 * @param account - the account to be added
	 * @return a boolean indicating whether the account was successfully added
	 */
	public boolean add(Account account) {
		if(size == this.accounts.length) {
			this.grow();
		}
		if(this.find(account) == -1) {
			this.accounts[size] = account;
			size++;
			return true;
		}else {
			return false;
		}
		
	} //return false if account exists
	
	
	/**
	 * A method to remove an account from the database
	 * @param account - thea ccount to be removed
	 * @return a boolean indicating whether the accouont was successfully removed
	 */
	public boolean remove(Account account) { 
		int index = this.find(account);
		if(index != -1) {
			this.accounts[index] = this.accounts[size - 1];
			this.accounts[size - 1] = null;
			size--;
			return true;
		}else {
			return false;
		}
	} //return false if account doesn’t exist
	
	
	/**
	 * A method to add money to an account
	 * @param account - the account we want to deposit money into
	 * @param amount - the amount of money we want to deposit
	 * @return a boolean indicating whether the deposit was successful
	 */
	public boolean deposit(Account account, double amount) { 
		int index = this.find(account);
		if(index != -1) {
			this.accounts[index].credit(amount);
			return true;
		}else {
			return false;
		}
	}
	//return 0: withdrawal successful, 1: insufficient funds, -1 account doesn’t exist
	
	/**
	 * A method to withdraw money from an account
	 * @param account - the account we want to withdraw from
	 * @param amount - the amount of money we want to withdraw
	 * @return 0 if the withdrawal was successful, 1 if the funds were insufficient, -1 if the account did not exist
	 */
	public int withdrawal(Account account, double amount) {
		int index = this.find(account);
		if(index != -1) {
			if(this.accounts[index].getBalance() < amount) {
				return 1;
			}else {
				this.accounts[index].debit(amount);
				return 0;
			}
		}else {
			return -1;
		}
	}
	
	/**
	 * A method that sorts the array from oldest date opened to most recent date opened
	 */
	private void sortByDateOpen() {
		
		for(int i = 0; i < size; i++) {
			int indexLowest = i;
			for(int j = i + 1; j < size; j++) {
				if(this.accounts[j].getDate().compareTo(this.accounts[indexLowest].getDate())  < 0) { //if the date being looked at right now is earlier than the date currently believed to be the lowest, replace the indexLowest variable
					indexLowest = j;
				}
			}
			Account temp;
			temp = this.accounts[i];
			this.accounts[i] = this.accounts[indexLowest];
			this.accounts[indexLowest] = temp;
		}
		
	} //sort in ascending order
	
	/**
	 * A method that sorts the array from alphabetically first last name, to alphabetically last last name
	 */
	private void sortByLastName() {
		for(int i = 0; i < size; i++) {
			int indexLowest = i;
			for(int j = i + 1; j < size; j++) {
				if(this.accounts[j].getProfile().getLastName().compareTo(this.accounts[indexLowest].getProfile().getLastName()) < 0) { //if the date being looked at right now is earlier than the date currently believed to be the lowest, replace the indexLowest variable
					indexLowest = j;
				}
			}
			Account temp;
			temp = this.accounts[i];
			this.accounts[i] = this.accounts[indexLowest];
			this.accounts[indexLowest] = temp;
		}
	} //sort in ascending order
	
	/**
	 * A method that sorts by date opened, and prints the results
	 */
	public String[] printByDateOpen() {
		this.sortByDateOpen();
		String[] output = new String[size];
		for(int i = 0; i < size; i++) {
			output[i] = this.accounts[i].toString();
			output[i] += "\n";
			output[i] += "-interest: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyInterest());
			output[i] += "\n";
			output[i] += "-fee: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyFee());
			output[i] += "\n";
			//System.out.println(this.accounts[i].toString());
			//System.out.println("-interest: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyInterest()));
			//System.out.println("-fee: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyFee()));
			this.accounts[i].credit(this.accounts[i].monthlyInterest()); // credit the account the monthly interest
			this.accounts[i].debit(this.accounts[i].monthlyFee()); // debit the account the monthly fee
			output[i] += "-new balance: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].getBalance());
			//System.out.println("-new balance: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].getBalance()));
			output[i] += "\n \n";
			//System.out.println("");
		}
		return output;
	}
	
	/**
	 * a method that sorts by last name, and prints the results
	 */
	public String[] printByLastName() { 
		this.sortByLastName();
		String[] output = new String[size];
		for(int i = 0; i < size; i++) {
			output[i] = this.accounts[i].toString();
			output[i] += "\n";
			output[i] += "-interest: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyInterest());
			output[i] += "\n";
			output[i] += "-fee: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyFee());
			output[i] += "\n";
			//System.out.println(this.accounts[i].toString());
			//System.out.println("-interest: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyInterest()));
			//System.out.println("-fee: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].monthlyFee()));
			this.accounts[i].credit(this.accounts[i].monthlyInterest()); // credit the account the monthly interest
			this.accounts[i].debit(this.accounts[i].monthlyFee()); // debit the account the monthly fee
			output[i] += "-new balance: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].getBalance());
			//System.out.println("-new balance: " + NumberFormat.getCurrencyInstance().format(this.accounts[i].getBalance()));
			output[i] += "\n \n";
			//System.out.println("");
		}
		return output;
	}
	
	/**
	 * A method that prints the array in whatever order it currently is in
	 */
	public String[] printAccounts() {
		String[] tokens = new String[size];
		for(int i = 0; i < size; i++) {
			tokens[i] = this.accounts[i].toString();
			tokens[i] += "\n";
			//System.out.println(this.accounts[i].toString());
		}
		return tokens;
	}
	/**
	 * accessor method to get the number of accounts currently in the database
	 * @return size - an int representing the number of accounts in the database
	 */
	public int getSize() {
		return this.size;
	}


}

