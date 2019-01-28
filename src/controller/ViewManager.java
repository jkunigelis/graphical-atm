package controller;

import java.awt.CardLayout;
import java.awt.Container;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.Database;
import model.User;
import model.BankAccount;
import view.ATM;
import view.CreateView;
import view.DepositView;
import view.HomeView;
import view.InformationView;
import view.LoginView;
import view.TransferView;
import view.WithdrawView;

public class ViewManager {
	
	private Container views;				// the collection of all views in the application
	private Database db;					// a reference to the database
	private BankAccount account;			// the user's bank account
	private BankAccount destination;		// an account to which the user can transfer funds
	private String temp;
	private Thread b = new Thread();
	
	/**
	 * Constructs an instance (or object) of the ViewManager class.
	 * 
	 * @param layout
	 * @param container
	 */
	
	public ViewManager(Container views) {
		this.views = views;
		this.db = new Database();
	}
	
	///////////////////// INSTANCE METHODS ////////////////////////////////////////////
	
	public BankAccount getAccount() {
		return account;
	}

	public void setAccount(BankAccount account) {
		this.account = account;
	}

	/**
	 * Routes a login request from the LoginView to the Database.
	 * 
	 * @param accountNumber
	 * @param pin
	 * @throws InterruptedException 
	 */
	public void login(String accountNumber, char[] pin) {
		
		if (accountNumber.equals("") || pin.length == 0) {
			try {
				account = db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
			}
			catch (NumberFormatException e) {
				if (account == null) {
					LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
					lv.updateErrorMessage("Invalid account number and/or PIN.");
				}
				else {
					switchTo(ATM.HOME_VIEW);
					LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
					lv.updateErrorMessage("");
				}
			}
		}
		else {
			account = db.getAccount(Long.valueOf(accountNumber), Integer.valueOf(new String(pin)));
			
			if (account == null) {
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("Invalid account number and/or PIN.");
			}
			else {
				HomeView.updateName(getAccount().getUser().getFirstName() + " " + getAccount().getUser().getLastName());
				HomeView.updateBalance("Current Balance: $" + String.valueOf(getAccount().getBalance()));
				switchTo(ATM.HOME_VIEW);
				LoginView lv = ((LoginView) views.getComponents()[ATM.LOGIN_VIEW_INDEX]);
				lv.updateErrorMessage("");
			}
		}
	}
	public void logOut() {
		LoginView.getAccountField().setText("");
		LoginView.pinField.setText("");
		CreateView.getFNameField().setText("");
		CreateView.getLNameField().setText("");
		CreateView.getAddressField().setText("");
		CreateView.getCityField().setText("");
		CreateView.getZipField().setText("");
		CreateView.getPhoneField().setText("");
		CreateView.getPinField().setText("");
		CreateView.getErrorMessageLabel().setText("");
		account = null;
		switchTo(ATM.LOGIN_VIEW);
	}
	public void exitNewAccount() {
		LoginView.updateNewAccountMessage("");
	}
	public void accountCreate(char a, double c, Integer d, Integer e, Long f, String g, String h, String i, String j, String k, String l) {
		Long b = db.findAvailableAccountNum();
		db.insertAccount(new BankAccount(a, b, c, new User(d, e, f, g, h, i, j, k, l)));
		account = db.getAccount(b);
		LoginView.updateNewAccountMessage("Your new account number is " + b + ".");
	}
	public void changeAccountInfo(char a, double c, Integer d, Integer e, Long f, String g, String h, String i, String j, String k, String l) {
		Long b = account.getAccountNumber();
		db.updateAccount(new BankAccount(a, b, c, new User(d, e, f, g, h, i, j, k, l)));
	}
	public void updateInfoView() {
		InformationView.updateFName(account.getUser().getFirstName());
		InformationView.updateLName(account.getUser().getLastName());
		String tempMonth = String.valueOf(account.getUser().getDob()).substring(4,6); 
		switch(tempMonth) {
		case "01":
			tempMonth = "Jan";
			break;
		case "02":
			tempMonth = "Feb";
			break;
		case "03":
			tempMonth = "Mar";
			break;
		case "04":
			tempMonth = "Apr";
			break;
		case "05":
			tempMonth = "May";
			break;
		case "06":
			tempMonth = "Jun";
			break;
		case "07":
			tempMonth = "Jul";
			break;
		case "08":
			tempMonth = "Aug";
			break;
		case "09":
			tempMonth = "Sep";
			break;
		case "10":
			tempMonth = "Oct";
			break;
		case "11":
			tempMonth = "Nov";
			break;
		case "12":
			tempMonth = "Dec";
			break;
		}
		InformationView.updateMonth(tempMonth);
		InformationView.updateDay(String.valueOf(account.getUser().getDob()).substring(6,8));
		InformationView.updateYear(String.valueOf(account.getUser().getDob()).substring(0,4));
		InformationView.updateAddress(account.getUser().getStreetAddress());
		InformationView.updateCity(account.getUser().getCity());
		InformationView.updateState(account.getUser().getState());
		InformationView.updateZip(account.getUser().getZip());
		InformationView.updatePhone(String.valueOf(account.getUser().getPhone()));
		InformationView.updatePin(String.valueOf(account.getUser().getPin()));
	}
	
	public int depositAmount(double a) {
		if (getAccount().deposit(a) == ATM.INVALID_AMOUNT) {
			DepositView.updateErrorMessage("Invalid amount.");
			return -1;
		}
		else {
			getAccount().deposit(a);
			db.updateAccount(new BankAccount(getAccount().getStatus(), getAccount().getAccountNumber(), getAccount().getBalance(), getAccount().getUser()));
			HomeView.updateBalance("Current Balance: $" + String.valueOf(getAccount().getBalance()));
			return 0;
		}
	}
	public int withdrawAmount(double a) {
		if (getAccount().withdraw(a) == ATM.INSUFFICIENT_FUNDS) {
			WithdrawView.updateErrorMessage("Amount exceeds available funds.");
			return -1;
		}
		else if (getAccount().withdraw(a) == ATM.INVALID_AMOUNT) {
			WithdrawView.updateErrorMessage("Invalid amount.");
			return -1;
		}
		else {
			getAccount().withdraw(a);
			db.updateAccount(new BankAccount(getAccount().getStatus(), getAccount().getAccountNumber(), getAccount().getBalance(), getAccount().getUser()));
			HomeView.updateBalance("Current Balance: $" + String.valueOf(getAccount().getBalance()));
			return 0;
		}
	}
	public int transferAmount(long b, double a) {
		if (db.getAccount(b) == null) {
			TransferView.updateErrorMessage("That account does not exist.");
			return -1;
		}
		else {
			BankAccount c = db.getAccount(b);
			if (getAccount().transfer(c,a) == ATM.INSUFFICIENT_FUNDS) {
				TransferView.updateErrorMessage("Amount exceeds available funds.");
				return -1;
			}
			else if (getAccount().transfer(c,a) == ATM.INVALID_AMOUNT) {
				TransferView.updateErrorMessage("Invalid amount.");
				return -1;
			}
			else {
				getAccount().transfer(c,a);
				db.updateAccount(new BankAccount(getAccount().getStatus(), getAccount().getAccountNumber(), getAccount().getBalance(), getAccount().getUser()));
				BankAccount tempOriginalAccount = account;
				account = c;
				db.updateAccount(new BankAccount(getAccount().getStatus(), getAccount().getAccountNumber(), getAccount().getBalance(), getAccount().getUser()));
				account = tempOriginalAccount;
				HomeView.updateBalance("Current Balance: $" + String.valueOf(getAccount().getBalance()));
				return 0;
			}
		}
	}
	
	/**
	 * Switches the active (or visible) view upon request.
	 * 
	 * @param view
	 * @throws InterruptedException 
	 */
	
	public void switchTo(String view) {
		((CardLayout) views.getLayout()).show(views, view);
	}
	
	/**
	 * Routes a shutdown request to the database before exiting the application. This
	 * allows the database to clean up any open resources it used.
	 */
	public void closeAccountConfirmation() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Close Account",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if (choice == 0) {
				db.closeAccount(getAccount());
				logOut();
				exitNewAccount();
				switchTo(ATM.LOGIN_VIEW);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void logOutConfirmation() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"LogOut ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if (choice == 0) {
				logOut();
				exitNewAccount();
				switchTo(ATM.LOGIN_VIEW);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shutdown() {
		try {			
			int choice = JOptionPane.showConfirmDialog(
				views,
				"Are you sure?",
				"Shutdown ATM",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if (choice == 0) {
				db.shutdown();
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
