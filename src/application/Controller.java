/**
Controller class for the graphical user interface 
@author Max Bartlik, Josh Hymowitz
*/
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.util.Scanner;

import TransactionManagement.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.scene.control.Alert.AlertType;

public class Controller {
	
	private AccountDatabase accountDatabase = new AccountDatabase();
	Date tempDate = new Date("1/1/2000"); // used for making accounts to be compared, the date is not checked
	
	@FXML
	private TextArea messageArea;
	
	@FXML
	private TextField openingDeposit, openingFirstName, openingLastName;
	
	@FXML
	private TextField closingFirstName, closingLastName;
	
	@FXML
	private TextField openDay, openMonth, openYear;
	
	@FXML
	private RadioButton openChecking, openSavings, openMoneyMarket;
	
	@FXML
	private RadioButton closeChecking, closeSavings, closeMoneyMarket;
	
	@FXML
	private CheckBox openLoyal, openDirectDeposit;
	
	@FXML
	private Button openAccount;
	
	@FXML
	private Button closeAccount;
	
	@FXML
	private Button chooseFileButton;
	
	@FXML
	private Button choosePathButton;
	
	@FXML
	private RadioButton checkingAccount, savingsAccount, moneyMarket;
	
	@FXML
	private Button depositButton, withdrawButton;
	
	@FXML
	private TextField firstName, lastName, amount;
	
	@FXML 
	private Button printAccountsButton, printByNameButton, printByDateButton, sortByNameButton, sortByDateButton;
	
	
	
	@FXML
	/**
	 * Event handler for the Deposit/Withdraw checking radio button
	 * @param event
	 */
	void depositOrWithdrawCheckingPress(ActionEvent event) {
		checkingAccount.setSelected(true);
		savingsAccount.setSelected(false);
		moneyMarket.setSelected(false);
	}
	
	@FXML
	/**
	 * Event handler for the Deposit/Withdraw savings radio button
	 * @param event
	 */
	void depositOrWithdrawSavingsPress(ActionEvent event) {
		checkingAccount.setSelected(false);
		savingsAccount.setSelected(true);
		moneyMarket.setSelected(false);
	}
	
	@FXML
	/**
	 * Event handler for the Deposit/Withdraw money market radio button
	 * @param event
	 */
	void depositOrWithdrawMoneyMarketPress(ActionEvent event) {
		checkingAccount.setSelected(false);
		savingsAccount.setSelected(false);
		moneyMarket.setSelected(true);
	}
	
	@FXML
	/**
	 * Event handler for the deposit button
	 * @param event
	 */
	void deposit(ActionEvent event) {
		
		// parse for name
		String firstName;
		String lastName;
		try {
			firstName = this.firstName.getText();
			lastName = this.lastName.getText();
		}catch(Exception e) {
			messageArea.appendText("Make sure you enter a first name and a last name\n");
			return;
		}
		
		// parse for deposit amount
		double amount;
		try {
			amount = Double.parseDouble(this.amount.getCharacters().toString());
		}catch(Exception e) {
			messageArea.appendText("Make sure to enter a number value to be deposited\n");
			return;
		}
		
		// perform checking operations
		if(checkingAccount.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			Checking tempChecking = new Checking(newProfile, 1, tempDate,false); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			boolean depositability = accountDatabase.deposit(tempChecking, amount);
			if(depositability) {
				messageArea.appendText("Deposit Successful\n");
			}else {
				messageArea.appendText("Unable to Deposit, account not found in that name\n");
			}
		}
		
		// perform savings operations
		else if(savingsAccount.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			Savings tempSavings = new Savings(newProfile, 1, tempDate,false); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			boolean depositability = accountDatabase.deposit(tempSavings, amount);
			if(depositability) {
				messageArea.appendText("Deposit Successful\n");
			}else {
				messageArea.appendText("Unable to Deposit, account not found in that name\n");
			}
		}
		
		// perform money market operations
		else if(moneyMarket.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			MoneyMarket tempMoneyMarket = new MoneyMarket(newProfile, 1, tempDate,1); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			boolean depositability = accountDatabase.deposit(tempMoneyMarket, amount);
			if(depositability) {
				messageArea.appendText("Deposit Successful\n");
			}else {
				messageArea.appendText("Unable to Deposit, account not found in that name\n");
			}
		}else {
			messageArea.appendText("Select an account type\n");
			return;
		}
	}
	
	
	@FXML
	/**
	 * event handler for the withdraw button
	 * @param event
	 */
	void withdraw(ActionEvent event) {
		
		// parse for name
		String firstName;
		String lastName;
		try {
			firstName = this.firstName.getText();
			lastName = this.lastName.getText();
		}catch(Exception e) {
			messageArea.appendText("Make sure you enter a first name and a last name\n");
			return;
		}
		
		// parse for withdrawal amount
		double amount;
		try {
			amount = Double.parseDouble(this.amount.getCharacters().toString());
		}catch(Exception e) {
			messageArea.appendText("Make sure to enter a number value to be deposited\n");
			return;
		}
		
		// perform checking operations
		if(checkingAccount.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			Checking tempChecking = new Checking(newProfile, 1, tempDate,false); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			int couldWithdraw = accountDatabase.withdrawal(tempChecking, amount);
			if(couldWithdraw == 0) {
				messageArea.appendText("Withdrawal Successful\n");
			}else if(couldWithdraw == 1){
				messageArea.appendText("Unable to withdraw, insufficient funds\n");
			}else {
				messageArea.appendText("Unable to withdraw, account not found in that name\n");
			}
		}
		
		// perform savings operations
		else if(savingsAccount.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			Savings tempSavings = new Savings(newProfile, 1, tempDate,false); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			int couldWithdraw = accountDatabase.withdrawal(tempSavings, amount);
			if(couldWithdraw == 0) {
				messageArea.appendText("Withdrawal Successful\n");
			}else if(couldWithdraw == 1){
				messageArea.appendText("Unable to withdraw, insufficient funds\n");
			}else {
				messageArea.appendText("Unable to withdraw, account not found in that name\n");
			}
			
		// perform money market operations
		}else if(moneyMarket.isSelected()) {
			Profile newProfile = new Profile(firstName, lastName);
			MoneyMarket tempMoneyMarket = new MoneyMarket(newProfile, 1, tempDate,1); //only the profile is compared to check for equality of accounts, so other values are temporaries
			
			int couldWithdraw = accountDatabase.withdrawal(tempMoneyMarket, amount);
			if(couldWithdraw == 0) {
				messageArea.appendText("Withdrawal Successful\n");
			}else if(couldWithdraw == 1){
				messageArea.appendText("Unable to withdraw, insufficient funds\n");
			}else {
				messageArea.appendText("Unable to withdraw, account not found in that name\n");
			}
		}
		else {
			messageArea.appendText("Select an account type\n");
		}
		
	}
	
	
	@FXML
	/**
	 * Event handler for the open account button
	 * @param event
	 */
	void openAccount(ActionEvent event) {
		
		// get initial deposit amount
		double openDepositAmount;
		try {
			openDepositAmount = Double.parseDouble(openingDeposit.getText());
		}
		catch (Exception e) {
			messageArea.appendText("Make sure to enter a number value for initial deposit\n");
			return;
		}
		
		// get account holder name
		String firstName;
		String lastName;
		try {
			firstName = openingFirstName.getText();
			lastName = openingLastName.getText();
		}
		catch (Exception e) {
			messageArea.appendText("Make sure to enter a first and last name\n");
			return;
		}
		
		// make sure the name fields have something in them
		if(firstName.length() == 0 || lastName.length() == 0) {
			messageArea.appendText("Make sure to enter a value for the first and last name\n");
			return;
		}
		
		if(openChecking.isSelected()) {
			Profile checkingsNewProfile = new Profile(firstName, lastName);
			
			String dateString = openDay.getText() + "/" + openMonth.getText() + "/" + openYear.getText();
			Date checkingsDateOpened = new Date(dateString);
			if(!checkingsDateOpened.isValid()) {
				messageArea.appendText("Date is invalid\n");
				return;
			}
			
			boolean directDeposit;
			if(openDirectDeposit.isPressed()) {
				directDeposit = true;
			}
			else {
				directDeposit = false;
			}

			
			Checking newChecking = new Checking(checkingsNewProfile, openDepositAmount,checkingsDateOpened, directDeposit);
			
			boolean checkingAddable = accountDatabase.add(newChecking);
			
			if(!checkingAddable) {
				messageArea.appendText("Accountis already in the database.\n");
			}
			else {
				messageArea.appendText("Account opened and added to the database\n");
			}
		}
		else if(openSavings.isSelected()) {
			Profile savingsNewProfile = new Profile(firstName, lastName);
			
			String dateString = openDay.getText() + "/" + openMonth.getText() + "/" + openYear.getText();
			Date savingsDateOpened = new Date(dateString);
			if(!savingsDateOpened.isValid()) {
				messageArea.appendText("Date is invalid\n");
				return;
			}
			
			boolean isLoyal;
			if(openLoyal.isPressed()) {
				isLoyal = true;
			}
			else {
				isLoyal = false;
			}
			
			Savings newSavings = new Savings(savingsNewProfile,openDepositAmount, savingsDateOpened, isLoyal);
			boolean savingsAddable = accountDatabase.add(newSavings);
			
			if(!savingsAddable) {
				messageArea.appendText("Account is already in the database.\n");
			}
			else {
				messageArea.appendText("Account opened and added to the database\n");
			}
		}
		else if(openMoneyMarket.isSelected()) {
			Profile moneyNewProfile = new Profile(firstName,lastName);
			
			String dateString = openDay.getText() + "/" + openMonth.getText() + "/" + openYear.getText();
			Date moneyDateOpened = new Date(dateString);
			if(!moneyDateOpened.isValid()) {
				messageArea.appendText("Date is invalid\n");
				return;
			}
			
			MoneyMarket newMoneyMarket = new MoneyMarket(moneyNewProfile,openDepositAmount, moneyDateOpened, 0);
			boolean moneyMarketAddable = accountDatabase.add(newMoneyMarket);
			
			if(!moneyMarketAddable) {
				messageArea.appendText("Account is already in the database.\n");
			}
			else {
				messageArea.appendText("Account opened and added to the database\n");
			}
		}
		else {
			messageArea.appendText("Select an account type\n");
		}
		accountDatabase.printAccounts();
	}
		
	@FXML
	/**
	 * Event handler for the open checking radio button
	 * @param event
	 */
	void openCheckingButtonPress(ActionEvent event) {
		openSavings.setSelected(false);
		openMoneyMarket.setSelected(false);
		
		openDirectDeposit.setDisable(false);
		openLoyal.setSelected(false);
		openLoyal.setDisable(true);
	}
	
	@FXML
	/**
	 * Event handler for the open savings radio button
	 * @param event
	 */
	void openSavingsButtonPress(ActionEvent event) {
		openChecking.setSelected(false);
		openMoneyMarket.setSelected(false);
		
		openLoyal.setDisable(false);
		openDirectDeposit.setSelected(false);
		openDirectDeposit.setDisable(true);
	}
	
	@FXML
	/**
	 * Event handler for the open money market radio button
	 * @param event
	 */
	void openMoneyMarketButtonPress(ActionEvent event) {
		openChecking.setSelected(false);
		openSavings.setSelected(false);
		
		openLoyal.setSelected(false);
		openLoyal.setDisable(true);
		openDirectDeposit.setSelected(false);
		openDirectDeposit.setDisable(true);
	}
	
	@FXML
	void closeAccount(ActionEvent event) {
		// get account holder name
		String firstName;
		String lastName;
		try {
			firstName = closingFirstName.getText();
			lastName = closingLastName.getText();
		}
		catch (Exception e) {
			messageArea.appendText("Make sure to enter a first and last name\n");
			return;
		}
		
		if(closeChecking.isSelected()) {
			Profile checkingDeleteProfile = new Profile(firstName, lastName);
			
			Checking deleteChecking = new Checking(checkingDeleteProfile,1,tempDate, true);
			boolean checkingRemovability = accountDatabase.remove(deleteChecking);
			
			if(!checkingRemovability) {
				messageArea.appendText("Account does not exist.\n");
			}
			else {
				messageArea.appendText("Account closed and removed from the database\n");
			}
			
		}
		else if(closeSavings.isSelected()) {
			Profile savingsDeleteProfile = new Profile(firstName,lastName);
			
			Savings deleteSavings = new Savings(savingsDeleteProfile,1,tempDate, true);
			boolean savingsRemovability = accountDatabase.remove(deleteSavings);
			
			if(!savingsRemovability) {
				messageArea.appendText("Account does not exist.\n");
			}
			else {
				messageArea.appendText("Account closed and removed from the database\n");
			}
			
		}
		else if(closeMoneyMarket.isSelected()) {
			Profile moneyDeleteProfile = new Profile(firstName,lastName);

			MoneyMarket deleteMoney = new MoneyMarket(moneyDeleteProfile,1,tempDate, 1);
			boolean moneyMarketRemovability = accountDatabase.remove(deleteMoney);
			
			if(!moneyMarketRemovability) {
				messageArea.appendText("Account does not exist.\n");
			}
			else {
				messageArea.appendText("Account closed and removed from the database\n");
			}
		}
		else {
			messageArea.appendText("Select an account type\n");
		}
		accountDatabase.printAccounts();
	}
	
	@FXML
	/**
	 * Event handler for the open checking radio button
	 * @param event
	 */
	void closeCheckingButtonPress(ActionEvent event) {
		closeSavings.setSelected(false);
		closeMoneyMarket.setSelected(false);
	}
	
	@FXML
	/**
	 * Event handler for the close savings radio button
	 * @param event
	 */
	void closeSavingsButtonPress(ActionEvent event) {
		closeChecking.setSelected(false);
		closeMoneyMarket.setSelected(false);
	}
	
	@FXML
	/**
	 * Event handler for the close money market radio button
	 * @param event
	 */
	void closeMoneyMarketButtonPress(ActionEvent event) {
		closeChecking.setSelected(false);
		closeSavings.setSelected(false);
	}
	
	@FXML
	/**
	 * Event handler for the choose file button
	 * @param event
	 */
	void chooseFile(ActionEvent event) {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		File file = fileChooser.showOpenDialog(dialog);
		if(file != null) {
			processFileInput(file);
		}
		//dialog.show();
	}
	
	
	/**
	 * Add all the accounts outlined in the file to the database (helper method for the file button handler)
	 * @param file
	 */
	void processFileInput(File file) {
		Scanner sc;
		try {
			sc = new Scanner(file);
		}catch(Exception e) {
			messageArea.appendText("Import a valid file");
			return;
		}
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			//messageArea.appendText(line);
			char accountType = line.charAt(0);//first char of any line should be S, C, or M for savings, checking or MoneyMarket
			String[] fields = new String[5];
			
			int pointer = 2;
			int pointer2 = 2;//pointers used to tokenize the line by commas
			for(int i = 0; i < 5; i++) {
				while(pointer2 < line.length() && line.charAt(pointer2) != ',' && line.charAt(pointer2) != '\n') {
					pointer2++;
				}
				fields[i] = line.substring(pointer,pointer2);//pointer should be the index of the first letter of the token, pointer2 should be the comma or newline separting the tokens
				pointer2++;
				pointer = pointer2;
				pointer2++;
			}
			
			
			//at the end of the for loop, accountType char should be S, C or M for the type, and the the fields array should be populated by the first name, last name, balance, date, and either a boolean or an int
			Profile tempProfile = new Profile(fields[0], fields[1]);
			Date tempDate = new Date(fields[3]);
			switch (accountType) {
				case 'S':
					
					Savings tempSavings = new Savings(tempProfile, Double.parseDouble(fields[2]), tempDate, Boolean.parseBoolean(fields[4]));
					accountDatabase.add(tempSavings);
					break;
				case 'C':
					
					Checking tempChecking = new Checking(tempProfile, Double.parseDouble(fields[2]), tempDate, Boolean.parseBoolean(fields[4]));
					accountDatabase.add(tempChecking);
					break;
					
				case 'M':
					MoneyMarket tempMoneyMarket = new MoneyMarket(tempProfile, Double.parseDouble(fields[2]), tempDate, Integer.parseInt(fields[4]));
					accountDatabase.add(tempMoneyMarket);
					break;
					
				default: 
			}
			
		}
		
		accountDatabase.printAccounts();
		messageArea.appendText("File imported\n");
	}
	
	
	@FXML
	/**
	 * event handler for export button
	 * @param event
	 */
	void exportFile(ActionEvent event) {
		String[] tokens = new String[accountDatabase.getSize()];
		tokens = accountDatabase.printAccounts();
		
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Export File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(dialog);
		if(file != null) {
			BufferedWriter br;
			try {
				br = new BufferedWriter(new FileWriter(file));
				
				for(String line : tokens) {
					//System.out.println("this line: " + line);
					char accType = line.charAt(1); //S, C or M to determine type
					int pointer = 1;
					while(line.charAt(pointer) != ' ') {//increment the pointer until we find the first space, indicating  that the next char is the first letter of the first name
						pointer++;
					}
					pointer++;
					int pointer2 = pointer;
					while(line.charAt(pointer2) != ' ') {
						pointer2++;
					}
					String firstName = line.substring(pointer, pointer2); //pointer is the index of the first letter of the first name, pointer2 should be the index of the space between the first and last name
					pointer2++;
					pointer = pointer2;
					while(line.charAt(pointer2) != '*') {
						pointer2++;
					}
					
					String lastName = line.substring(pointer,pointer2);//pointer is the index of the first letter of the last name. pointer2 the index of the asterisk signifying the end of the name
					pointer2++;
					pointer2++;
					pointer2++;//before these increments, pointer2 is the index of the asterisk, after the first increment it is the space, after the second, it is the dollar sign, after the third, it is the first digit of the balance
					pointer = pointer2;
					while(line.charAt(pointer2) != '*') {
						pointer2++;
						
					}
					
					
					String balance = line.substring(pointer, pointer2);
					balance = balance.replace(",", "");
					//String balance = NumberFormat.getCurrencyInstance().format(line.substring(pointer,pointer2)).toString();
					pointer2++;
					pointer2++;
					pointer = pointer2;
					
					while(pointer2 < line.length() && (line.charAt(pointer2) != '*')) {
						//System.out.println("pointer 2 is: " + line.charAt(pointer2) + " at this time");
						pointer2++;
					}
					String date = line.substring(pointer,pointer2-1);
					
					//at this point processing the string into the database.txt format gets more complicated because 1) the last field is different depending on the type of account and 2) the argument may or may not be there
					String lastArg;
					if(accType == 'S' || accType == 'C') {
						//if pointer2 has reached the end of the string, it did not find an asterisk, and thus the last field was missing, and thus this field is false
						if(pointer2 >= line.length()) {
							lastArg = "false";
						}else {
							//System.out.println("pointer 2 is: " + line.charAt(pointer2) + " at this time");
							lastArg = "true";
						}
					}else {//if it wasnt S or C, it must be M
						pointer2++;
						pointer = pointer2;//integer can be any number of digits, so need to parse to next space
						while(line.charAt(pointer2) != ' ') {
							pointer2++;
						}
						lastArg = line.substring(pointer, pointer2);
					}
					
					String finalOutput = accType + "," + firstName + "," + lastName + "," + balance + "," + date + "," + lastArg + "\n";
					br.write(finalOutput, 0, finalOutput.length());
					br.flush();
					
				}
			}catch (Exception e) {
				System.out.println(e.toString());
				messageArea.appendText("Choose a valid file and location\n");
				return;
			}
			
		}
		
	}
	
	//print and sort methods go here
	@FXML
	/**
	 * event handler for the print by last name button
	 * @param event
	 */
	void printByLastNamePress(ActionEvent event) {
		String[] tokens = new String[accountDatabase.getSize()];
		tokens = accountDatabase.printByLastName();
		for(String token : tokens) {
			messageArea.appendText(token);
		}
	}
	
	@FXML
	/**
	 * event handler for print by date button
	 * @param event
	 */
	void printByDatePress(ActionEvent event) {
		String[] tokens = new String[accountDatabase.getSize()];
		tokens = accountDatabase.printByDateOpen();
		for(String token : tokens) {
			messageArea.appendText(token);
		}
	}
	
	@FXML
	/**
	 * event handler for print accounts button
	 * @param event
	 */
	void printAccountsPress(ActionEvent event) {
		String[] tokens = new String[accountDatabase.getSize()];
		tokens = accountDatabase.printAccounts();
		for(String token : tokens) {
			messageArea.appendText(token);
		}
	}
		

		
}
	
	
	
	
	
