package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ViewManager;
import model.BankAccount;

@SuppressWarnings("serial")
public class HomeView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JButton depositButton;
	private JButton transferButton;
	private JButton withdrawButton;
	private JButton informationButton;
	private JButton closeAccountButton;
	private JButton logoutButton;
	private static JLabel title;
	private static JLabel personName;
	private static JLabel personBalance;
	private JPanel buttons;
	private JPanel bottom;
	private JPanel top;
	private JPanel everything;
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 * @throws InterruptedException 
	 */
	
	public HomeView(ViewManager manager) throws InterruptedException {
		super();
		
		this.manager = manager;
		title = new JLabel("Account Details", SwingConstants.CENTER);
		personName = new JLabel("", SwingConstants.CENTER);
		personBalance = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() throws InterruptedException {
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the HomeView.
		
	
		top = new JPanel();
		top.setLayout(new GridLayout(0,1));
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,3));
		bottom = new JPanel();
		bottom.setLayout(new GridLayout(0,1));
		
		everything = new JPanel();
		everything.setLayout(new GridLayout(0,1));
		
		top.add(new javax.swing.JLabel("Welcome", javax.swing.SwingConstants.CENTER));
		initNameLabel();
		top.add(new javax.swing.JLabel(" ", javax.swing.SwingConstants.CENTER));
		initTitleLabel();
		initBalanceLabel();
		top.add(new javax.swing.JLabel(" ", javax.swing.SwingConstants.CENTER));
		
		initDepositButton();
		initWithdrawButton();
		initTransferButton();
		initInformationButton();
		initCloseAccountButton();
		initLogoutButton();
		
		everything.add(top);
		everything.add(buttons);
		everything.add(bottom);
		
		add(everything);
		// TODO
		//
		// this is where you should build the HomeView (i.e., all the components that
		// allow the user to interact with the ATM - deposit, withdraw, transfer, etc.).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}
	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	private void initTitleLabel() {
		title.setBounds(0, 240, 500, 35);
		
		top.add(title);
	}
	private void initNameLabel() {
		personName.setBounds(0, 240, 500, 35);
		
		top.add(personName);
	}
	public static void updateName(String name) {
		personName.setText(name);
	}
	private void initBalanceLabel() {
		personBalance.setBounds(0, 240, 500, 35);
		
		top.add(personBalance);
	}
	public static void updateBalance(String name) {
		personBalance.setText(name);
	}
	
	
	
	
	
	private void initCloseAccountButton() {
		closeAccountButton = new JButton();
		closeAccountButton.setBounds(5, 5, 50, 50);
		closeAccountButton.addActionListener(this);
		
		try {
			closeAccountButton.setText("Close Account");;
		} catch (Exception e) {
			closeAccountButton.setText("Close Account");
		}
		
		buttons.add(closeAccountButton);
	}
	private void initDepositButton() {
		depositButton = new JButton();
		depositButton.setBounds(5, 5, 50, 50);
		depositButton.addActionListener(this);
		
		try {
			depositButton.setText("Deposit");;
		} catch (Exception e) {
			depositButton.setText("Deposit");
		}
		
		buttons.add(depositButton);
	}
	private void initWithdrawButton() {
		withdrawButton = new JButton();
		withdrawButton.setBounds(5, 5, 50, 50);
		withdrawButton.addActionListener(this);
		
		try {
			withdrawButton.setText("Withdraw");;
		} catch (Exception e) {
			withdrawButton.setText("Withdraw");
		}
		
		buttons.add(withdrawButton);
	}
	private void initTransferButton() {
		transferButton = new JButton();
		transferButton.setBounds(5, 5, 50, 50);
		transferButton.addActionListener(this);
		
		try {
			transferButton.setText("Transfer");;
		} catch (Exception e) {
			transferButton.setText("Transfer");
		}
		
		buttons.add(transferButton);
	}
	private void initInformationButton() {
		informationButton = new JButton();
		informationButton.setBounds(5, 5, 50, 50);
		informationButton.addActionListener(this);
		
		try {
			informationButton.setText("View/Edit Info");;
		} catch (Exception e) {
			informationButton.setText("View/Edit Info");
		}
		
		buttons.add(informationButton);
	}
	private void initLogoutButton() {
		logoutButton = new JButton();
		logoutButton.setBounds(5, 5, 50, 50);
		logoutButton.addActionListener(this);
		
		try {
			logoutButton.setText("Log Out");;
		} catch (Exception e) {
			logoutButton.setText("Log Out");
		}
		
		bottom.add(logoutButton);
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(transferButton)) {
			manager.switchTo(ATM.TRANSFER_VIEW);
		} else if (source.equals(withdrawButton)) {
			manager.switchTo(ATM.WITHDRAW_VIEW);
		} else if (source.equals(depositButton)) {
			manager.switchTo(ATM.DEPOSIT_VIEW);
		} else if (source.equals(logoutButton)) {
			manager.logOutConfirmation();
		} else if (source.equals(informationButton)) {
			manager.updateInfoView();
			manager.switchTo(ATM.INFORMATION_VIEW);
		} else if (source.equals(closeAccountButton)) {
			manager.closeAccountConfirmation();
		} else {
			System.err.println("ERROR: Action command not found (" + e.getActionCommand() + ")");
		}
		
		// TODO
		//
		// this is where you'll setup your action listener, which is responsible for
		// responding to actions the user might take in this view (an action can be a
		// user clicking a button, typing in a textfield, etc.).
		//
		// feel free to use my action listener in LoginView.java as an example.
	}
}