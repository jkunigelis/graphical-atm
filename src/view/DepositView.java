package view;

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

@SuppressWarnings("serial")
public class DepositView extends JPanel implements ActionListener {
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	private JLabel amountLabel = new JLabel("Amount to Deposit:");
	private JTextField amountField = new JTextField();
	private JButton saveButton;
	private JButton backButton;
	private JPanel amount = new JPanel();
	private JPanel buttons = new JPanel();
	private JPanel everything = new JPanel();
	private JPanel error = new JPanel();
	private static JLabel errorMessageLabel;
	
	/**
	 * Constructs an instance (or objects) of the HomeView class.
	 * 
	 * @param manager
	 */
	
	public DepositView(ViewManager manager) {
		super();
		
		this.manager = manager;
		DepositView.errorMessageLabel = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the HomeView components.
	 */
	
	private void initialize() {
		
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the HomeView.
		
		this.add(new javax.swing.JLabel("Deposit View", javax.swing.SwingConstants.CENTER));
		initAmountField();
		initSaveButton();
		initBackButton();
		
		
		amount.setLayout(new GridLayout(0,2));
		amount.add(amountLabel);
		amount.add(amountField);
		
		buttons.setLayout(new GridLayout(0,2));
		buttons.add(saveButton);
		buttons.add(backButton);
		
		error.setLayout(new GridLayout(0,1));
		error.add(errorMessageLabel);
		
		everything.setLayout(new GridLayout(0,1));
		everything.add(amount);
		everything.add(buttons);
		everything.add(error);
		
		initErrorMessageLabel();
		
		add(everything);
		
		
		// TODO
		//
		// this is where you should build the HomeView (i.e., all the components that
		// allow the user to interact with the ATM - deposit, withdraw, transfer, etc.).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
	}
	
	public static void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
		error.add(errorMessageLabel);
	}
	
	private void initAmountField() {
		JLabel label = new JLabel("Amount:", SwingConstants.RIGHT);
		label.setBounds(100, 140, 95, 35);
		label.setLabelFor(amountField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		amountField = new JTextField(20);
		amountField.setBounds(205, 100, 200, 35);
		amountField.addActionListener(this);
		amount.add(amountField);
	}
	
	private void initBackButton() {
		backButton = new JButton();
		backButton.setBounds(5, 5, 50, 50);
		backButton.addActionListener(this);
		
		try {
			backButton.setText("Back");;
		} catch (Exception e) {
			backButton.setText("Back");
		}
		
		buttons.add(backButton);
	}
	private void initSaveButton() {
		saveButton = new JButton();
		saveButton.setBounds(5, 5, 50, 50);
		saveButton.addActionListener(this);
		
		try {
			saveButton.setText("Finish");;
		} catch (Exception e) {
			saveButton.setText("Finish");
		}
		
		buttons.add(saveButton);
	}
	
	/*
	 * HomeView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the HomeView.
	 * 
	 * @param e
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();
		
		if (source.equals(backButton)) {
			manager.switchTo(ATM.HOME_VIEW);
			updateErrorMessage("");
			amountField.setText("");
		}
		else if (source.equals(saveButton)) {
			try {
				if (manager.depositAmount(Double.parseDouble(amountField.getText())) == 0) {
					updateErrorMessage("");
					amountField.setText("");
					manager.switchTo(ATM.HOME_VIEW);
				}
			}
			catch (NumberFormatException e1) {
				updateErrorMessage("Please enter valid amount.");
			}
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