package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import controller.ViewManager;
import data.Database;

@SuppressWarnings("serial")
public class InformationView extends JPanel implements ActionListener {
	
	private String tempAbbr;
	private String tempMonth;
	private String tempDay;
	private String tempYear;
	
	private JButton editButton = new JButton("Edit");
	private JButton backButton = new JButton("Back");
	
	private static JTextField FNameField = new JTextField();
	private JLabel FNameLabel = new JLabel("First Name:");
	private static JTextField LNameField = new JTextField();
	private JLabel LNameLabel = new JLabel("Last Name:");
	
	static String[] choices = { "Jan","Feb", "Mar","Apr","May","Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	private static JComboBox<String> monthField = new JComboBox<String>(choices);
	private JLabel monthLabel = new JLabel("Month:");
	static String[] choices1 = { "01","02", "03","04","05","06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	private static JComboBox<String> dayField = new JComboBox<String>(choices1);
	private JLabel dayLabel = new JLabel("Day:");
	static String[] choices2 = { "1971","1972", "1973","1974","1975","1976", "1977", "1978", "1979", "1980", "1981", "1982", "1983", "1984", "1985", "1986", "1987", "1988", "1989", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001"};
	private static JComboBox<String> yearField = new JComboBox<String>(choices2);
	private JLabel yearLabel = new JLabel("Year:");
	
	
	private static JTextField addressField = new JTextField();
	private JLabel addressLabel = new JLabel("Address:");
	private static JTextField cityField = new JTextField();
	private JLabel cityLabel = new JLabel("City:");
	static String[] choices3 = { "AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC",  
		    "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA",  
		    "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE",  
		    "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC",  
		    "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};
	private static JComboBox<String> stateAbbrField = new JComboBox<String>(choices3);
	private JLabel stateAbbrLabel = new JLabel("State:");
	private static JTextField zipField = new JTextField();
	private JLabel zipLabel = new JLabel("Zip Code:");
	private static JTextField phoneField = new JTextField();
	private JLabel phoneLabel = new JLabel("Phone Number:");
	private static JPasswordField pinField = new JPasswordField();
	private JLabel pinLabel = new JLabel("Pin:");
	private static JLabel errorMessageLabel = new JLabel();
	private JPanel name;
	private JPanel birthdate;
	private JPanel homeAddress;
	private JPanel phoneNumber;
	private JPanel buttons;
	private JPanel pins;
	private JPanel error;
	
	private ViewManager manager;		// manages interactions between the views, model, and database
	
	/**
	 * Constructs an instance (or object) of the CreateView class.
	 * 
	 * @param manager
	 */
	
	public InformationView(ViewManager manager) {
		super();
		
		this.manager = manager;
		InformationView.errorMessageLabel = new JLabel("", SwingConstants.CENTER);
		initialize();
	}
	
	///////////////////// PRIVATE METHODS /////////////////////////////////////////////
	
	/*
	 * Initializes the CreateView components.
	 */
	
	private void initialize() {
		initFNameField();
		initLNameField();
		initMonthField();
		initDayField();
		initYearField();
		initAddressField();
		initCityField();
		initStateAbbrField();
		initZipField();
		initPhoneField();
		initPinField();
		initErrorMessageLabel();
		initFinishButton();
		initLogoutButton();
		
		
		name = new JPanel();
		name.setLayout(new GridLayout(0,2));
		name.add(FNameLabel);
		name.add(FNameField);
		name.add(LNameLabel);
		name.add(LNameField);
		
		birthdate = new JPanel();
		birthdate.setLayout(new GridLayout(0,2));
		birthdate.add(monthLabel);
		birthdate.add(monthField);
		birthdate.add(dayLabel);
		birthdate.add(dayField);
		birthdate.add(yearLabel);
		birthdate.add(yearField);
		
		homeAddress = new JPanel();
		homeAddress.setLayout(new GridLayout(0,2));
		homeAddress.add(addressLabel);
		homeAddress.add(addressField);
		homeAddress.add(cityLabel);
		homeAddress.add(cityField);
		homeAddress.add(stateAbbrLabel);
		homeAddress.add(stateAbbrField);
		homeAddress.add(zipLabel);
		homeAddress.add(zipField);
		
		phoneNumber = new JPanel();
		phoneNumber.setLayout(new GridLayout(0,2));
		phoneNumber.add(phoneLabel);
		phoneNumber.add(phoneField);
		
		pins = new JPanel();
		pins.setLayout(new GridLayout(0,2));
		pins.add(pinLabel);
		pins.add(pinField);
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(0,2));
		buttons.add(editButton);
		buttons.add(backButton);
		
		error = new JPanel();
		error.setLayout(new GridLayout(0,1));
		error.add(errorMessageLabel);
		
		add(name);
		add(phoneNumber);
		add(birthdate);
		add(homeAddress);
		add(pins);
		add(buttons);
		add(error);
		// TODO
		//
		// this is a placeholder for this view and should be removed once you start
		// building the CreateView.
		
		// TODO
		//
		// this is where you should build the CreateView (i.e., all the components that
		// allow the user to enter his or her information and create a new account).
		//
		// feel free to use my layout in LoginView as an example for laying out and
		// positioning your components.
		
	}
	
	public void updateErrorMessage(String errorMessage) {
		errorMessageLabel.setText(errorMessage);
	}
	
	private void initErrorMessageLabel() {
		errorMessageLabel.setBounds(0, 240, 500, 35);
		errorMessageLabel.setFont(new Font("DialogInput", Font.ITALIC, 14));
		errorMessageLabel.setForeground(Color.RED);
		
	}
	
	private void initFNameField() {
		JLabel label = new JLabel("First Name", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(FNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		FNameField = new JTextField(20);
		FNameField.setBounds(205, 100, 200, 35);
		FNameField.addActionListener(this);
		FNameField.setEditable(false);
		
	}
	public static void updateFName(String text) {
		FNameField.setText(text);
	}
	
	private void initLNameField() {
		JLabel label = new JLabel("Last Name", SwingConstants.RIGHT);
		label.setBounds(100, 140, 95, 35);
		label.setLabelFor(LNameField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		LNameField = new JTextField(20);
		LNameField.setBounds(205, 100, 200, 35);
		LNameField.addActionListener(this);
		LNameField.setEditable(false);
		
	}
	public static void updateLName(String text) {
		LNameField.setText(text);
	}
	private void initMonthField() {
		JLabel label = new JLabel("Month", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(monthField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		monthField.setBounds(205, 100, 200, 35);
		monthField.addActionListener(this);
		monthField.setEnabled(false);
		
	}
	public static void updateMonth(String text) {
		monthField.setSelectedItem(text);;
	}
	private void initDayField() {
		JLabel label = new JLabel("Day", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(dayField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		dayField.setBounds(205, 100, 200, 35);
		dayField.addActionListener(this);
		dayField.setEnabled(false);
		
	}
	public static void updateDay(String text) {
		dayField.setSelectedItem(text);;
	}
	private void initYearField() {
		JLabel label = new JLabel("Year", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(yearField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		yearField.setBounds(205, 100, 200, 35);
		yearField.addActionListener(this);
		yearField.setEnabled(false);
		
	}
	public static void updateYear(String text) {
		yearField.setSelectedItem(text);;
	}
	private void initAddressField() {
		JLabel label = new JLabel("Year", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(addressField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		addressField = new JTextField(20);
		addressField.setBounds(205, 100, 200, 35);
		addressField.setEditable(false);
		
	}
	public static void updateAddress(String text) {
		addressField.setText(text);
	}
	private void initCityField() {
		JLabel label = new JLabel("City", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(cityField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		cityField = new JTextField(20);
		cityField.setBounds(205, 100, 200, 35);
		cityField.addActionListener(this);
		cityField.setEditable(false);
		
	}
	public static void updateCity(String text) {
		cityField.setText(text);
	}
	private void initStateAbbrField() {
		JLabel label = new JLabel("State Abbr.", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(stateAbbrField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		stateAbbrField.setBounds(205, 100, 200, 35);
		stateAbbrField.addActionListener(this);
		stateAbbrField.setEnabled(false);
		
	}
	public static void updateState(String text) {
		stateAbbrField.setSelectedItem(text);
	}
	private void initZipField() {
		JLabel label = new JLabel("Zip Code", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(zipField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		zipField = new JTextField(20);
		zipField.setBounds(205, 100, 200, 35);
		zipField.addActionListener(this);
		zipField.setEditable(false);
		
	}
	public static void updateZip(String text) {
		zipField.setText(text);
	}
	private void initPhoneField() {
		JLabel label = new JLabel("Phone", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(phoneField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		phoneField = new JTextField(20);
		phoneField.setBounds(205, 100, 200, 35);
		phoneField.addActionListener(this);
		phoneField.setEditable(false);
		
	}
	public static void updatePhone(String text) {
		phoneField.setText(text);
	}
	private void initPinField() {
		JLabel label = new JLabel("Pin", SwingConstants.RIGHT);
		label.setBounds(100, 100, 95, 35);
		label.setLabelFor(pinField);
		label.setFont(new Font("DialogInput", Font.BOLD, 14));
		
		pinField = new JPasswordField(20);
		pinField.setBounds(205, 100, 200, 35);
		pinField.addActionListener(this);
		pinField.setEditable(false);
		
	}
	public static void updatePin(String text) {
		pinField.setText(text);
	}
	private void initFinishButton() {
		editButton = new JButton();
		editButton.setBounds(5, 5, 50, 50);
		editButton.addActionListener(this);
		
		try {
			editButton.setText("Edit");;
		} catch (Exception e) {
			editButton.setText("Edit");
		}
	}
	private void initLogoutButton() {
		backButton = new JButton();
		backButton.setBounds(5, 5, 50, 50);
		backButton.addActionListener(this);
		
		try {
			backButton.setText("Exit");;
		} catch (Exception e) {
			backButton.setText("Exit");
		}
	}
	
	/*
	 * CreateView is not designed to be serialized, and attempts to serialize will throw an IOException.
	 * 
	 * @param oos
	 * @throws IOException
	 */
	
	private void writeObject(ObjectOutputStream oos) throws IOException {
		throw new IOException("ERROR: The CreateView class is not serializable.");
	}
	
	///////////////////// OVERRIDDEN METHODS //////////////////////////////////////////
	
	/*
	 * Responds to button clicks and other actions performed in the CreateView.
	 * 
	 * @param e
	 */
	public void changeToEdit() {
		editButton.setText("Save Changes");
		backButton.setText("Don't Save");
		addressField.setEditable(true);
		cityField.setEditable(true);
		stateAbbrField.setEnabled(true);
		zipField.setEditable(true);
		phoneField.setEditable(true);
		pinField.setEditable(true);
	}
	public void exitEdit() {
		editButton.setText("Edit");
		backButton.setText("Back");
		addressField.setEditable(false);
		cityField.setEditable(false);
		stateAbbrField.setEnabled(false);
		zipField.setEditable(false);
		phoneField.setEditable(false);
		pinField.setEditable(false);
	}
	public void finishForm() throws InterruptedException {
		
		if (addressField.getText().equals(null) || cityField.getText().equals(null) || tempAbbr.equals(null) || zipField.getText().equals(null) || phoneField.getText().equals(null) || pinField.getPassword().equals(null)) {
			updateErrorMessage("Please fill out all fields.");
		} else {
			manager.changeAccountInfo('Y', 0.00, Integer.valueOf(new String(pinField.getPassword())), manager.getAccount().getUser().getDob(), Long.valueOf(phoneField.getText()), FNameField.getText(), LNameField.getText(), addressField.getText(), cityField.getText(), tempAbbr, zipField.getText());
			updateErrorMessage("");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source.equals(backButton)) {
			if (backButton.getText().equals("Back")) {
				manager.switchTo(ATM.HOME_VIEW);
				updateErrorMessage("");
			}
			else {
				exitEdit();
			}
		}
		else if (source.equals(stateAbbrField)) {
			String selection = (String) stateAbbrField.getSelectedItem();
			tempAbbr = selection;
		}
		else if (source.equals(monthField)) {
			tempMonth = (String) monthField.getSelectedItem();
			switch(tempMonth) {
			case "Jan":
				tempMonth = "01";
				break;
			case "Feb":
				tempMonth = "02";
				break;
			case "Mar":
				tempMonth = "03";
				break;
			case "Apr":
				tempMonth = "04";
				break;
			case "May":
				tempMonth = "05";
				break;
			case "Jun":
				tempMonth = "06";
				break;
			case "Jul":
				tempMonth = "07";
				break;
			case "Aug":
				tempMonth = "08";
				break;
			case "Sep":
				tempMonth = "09";
				break;
			case "Oct":
				tempMonth = "10";
				break;
			case "Nov":
				tempMonth = "11";
				break;
			case "Dec":
				tempMonth = "12";
				break;
			}
		}
		else if (source.equals(dayField)) {
			tempDay = (String) dayField.getSelectedItem();
		}
		else if (source.equals(yearField)) {
			tempYear = (String) yearField.getSelectedItem();
		}
		else if (source.equals(editButton)){
			if (editButton.getText().equals("Edit")) {
				changeToEdit();
			}
			else {
				try {
					finishForm();
					exitEdit();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					updateErrorMessage("Please enter valid information.");
				}
			}
		}
		else {
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

	public static JTextField getFNameField() {
		return FNameField;
	}

	public static JTextField getLNameField() {
		return LNameField;
	}
	public static JTextField getAddressField() {
		return addressField;
	}
	public static JTextField getCityField() {
		return cityField;
	}
	public static JTextField getZipField() {
		return zipField;
	}
	public static JTextField getPhoneField() {
		return phoneField;
	}
	public static JPasswordField getPinField() {
		return pinField;
	}
	public static JLabel getErrorMessageLabel() {
		return errorMessageLabel;
	}
}