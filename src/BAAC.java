import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BAAC extends Application{
	public static List<User> users = new ArrayList<>();
	public static List<Manager> managers = new ArrayList<>();
	
	public void start(Stage primaryStage){
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20,20,20,20));
		pane.setVgap(5);
		pane.setHgap(5);
		Button create = new Button("Test Run");
		
		GridPane tempPane = new GridPane();
		tempPane.setAlignment(Pos.CENTER);
		tempPane.setPadding(new Insets(20,20,20,20));
		tempPane.setVgap(10);
		tempPane.setHgap(10);
		
		Label userName = new Label("User : ");
		TextField txtUser = new TextField();
		txtUser.setPromptText("Enter Username");
		Label activityCode = new Label("Activity Code : ");
		TextField txtCode = new TextField();
		txtCode.setPromptText("Enter Activity Code");
		Label resourceAccess = new Label("Resource to be accessed : ");
		TextField txtResource = new TextField();
		txtResource.setPromptText("Enter Resource");
		Label testCase = new Label("Test Case : ");
		ArrayList<String> list = new ArrayList<>();
		list.add("TestCase1");
		list.add("TestCase2");
		list.add("TestCase3");
		list.add("TestCase4");
		list.add("TestCase5");
		list.add("TestCase6");
		list.add("TestCase7");
		list.add("TestCase8");
		list.add("TestCase9");
		list.add("TestCase10");
		list.add("TestCase11");
		list.add("TestCase12");
		list.add("TestCase13");
		list.add("TestCase14");
		list.add("TestCase15");
		list.add("TestCase16");
		list.add("TestCase17");
		list.add("TestCase18");
		list.add("TestCase19");
		list.add("TestCase20");
		ComboBox<String> choice = new ComboBox<String>(collection(list));
		choice.getSelectionModel().select(0);
		Label mac = new Label("MAC Address of Device : ");
		TextField txtMac = new TextField();
		txtMac.setPromptText("Enter MAC Address");
		
		tempPane.add(userName, 0, 0);
		tempPane.add(txtUser, 1, 0);
		tempPane.add(activityCode, 0, 1);
		tempPane.add(txtCode, 1, 1);
		tempPane.add(resourceAccess, 0, 2);
		tempPane.add(txtResource, 1, 2);
		tempPane.add(testCase, 0, 3);
		tempPane.add(choice, 1, 3);
		tempPane.add(mac, 0, 4);
		tempPane.add(txtMac, 1, 4);
		
		Label error = new Label();
		error.setTextFill(Color.web("#ff0000"));
		TextArea console = new TextArea();
		console.setEditable(false);
		create.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				console.clear();
				error.setText("");
				String fileName = "TestCases/"+choice.getSelectionModel().getSelectedItem() + ".txt";
				boolean grantAccess = true;
				boolean check = false;
				
				char[] temp = txtMac.getText().toCharArray();
				if(temp.length == 17 && temp[2] == ':' && temp[5] == ':' && temp[8] == ':' && temp[11] == ':' && temp[14] == ':'){
					
				}
				else {
					error.setText("Invalid MAC Address");
					check = true;
				}
				
				if(txtUser.getText().equalsIgnoreCase("")){
					error.setText("User was left empty!");
					check = true;
				}else if(txtCode.getText().equalsIgnoreCase("")){
					error.setText("Activity Code was left empty!");
					check=true;
				}else if(txtResource.getText().equalsIgnoreCase("")){
					error.setText("Resource was left empty!");
					check=true;
				}else if(checkCode(txtCode.getText())==false){
					error.setText("Invalid Activity Code");
					check = true;
				}else if(createUsers(txtUser.getText()) == false && createManagers(txtUser.getText()) == false){
					error.setText(txtUser.getText()+" does not exist!");
					check = true;
				}else if(isResource(txtResource.getText()) == false){
					error.setText(txtResource.getText()+" does not exist!");
					check = true;
				}else if(isAllowed(txtUser.getText(), txtResource.getText(), txtCode.getText()) == false){
					error.setText("No Permission To Gain Access");
					check = true;
					reportIncident(txtUser.getText(), "Permission Denied!");
				}else{
					try {
						if(analyzeBehavior(txtUser.getText(), fileName) == false && check==false){
							console.appendText("SAC System: Possible Insider Threat Detected!\n");
							grantAccess = false;
						}else if(check == false){
							console.appendText("SAC System: Behavior Analysis Passed\n");
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				boolean roleNdevice = false;
				if(check==false){
					if(NetworkNodeCheck(txtResource.getText()) == true){
						console.appendText("SAC System: Network Node Check Passed\n");
					}else{
						grantAccess = false;
						console.appendText("SAC System: Network Node Check Failed, Resource Setting Restored\n");
						reportIncident(txtResource.getText(), "Failed Network Node Check, Settings Restored");
					}
					
					//Check User List
					for(int i=0;i<users.size();i++){
						if(users.get(i).getName().equalsIgnoreCase(txtUser.getText())){
							if(RoleDeviceAC(users.get(i), txtMac.getText()) == true){
								console.appendText("SAC System: Role / Device Match\n");
							}else {
								grantAccess = false;
								console.appendText("SAC System: Role / Device Mismatch\n");
								reportIncident(txtUser.getText(), "Using Unauthorized Device");
							}
							roleNdevice = true;
							break;
						}
					}
					
					//Check Manager List
					if(roleNdevice != true){
						for(int i=0;i<managers.size();i++){
							if(managers.get(i).getName().equalsIgnoreCase(txtUser.getText())){
								if(RoleDeviceAC(managers.get(i), txtMac.getText()) == true){
									console.appendText("SAC System: Role / Device Match -- Access Granted!\n");
								}else {
									grantAccess = false;
									console.appendText("SAC System: Role / Device Mismatch -- Access Denied!\n");
									reportIncident(txtUser.getText(), "Using Unauthorized Device");
								}
								break;
							}
						}
					}
					
					if(grantAccess == true){
						console.appendText(txtUser.getText()+" was granted Access!");
					}else {
						console.appendText(txtUser.getText()+" was denied Access!");
						reportIncident(txtUser.getText(), "Denied Access");
					}
				}
				/*
				txtUser.setText("");
				txtCode.setText("");
				txtResource.setText("");
				txtMac.setText("");
				*/
			}
		});
		
		Button exit = new Button("Close");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				System.exit(0);
			}
		});
		
		pane.add(create, 0, 1);
		pane.add(tempPane, 0, 0);
		pane.add(error, 0, 2);
		pane.add(console, 0, 3);
		pane.add(exit, 0, 4);
		
		GridPane.setHalignment(create, HPos.CENTER);
		GridPane.setHalignment(console, HPos.CENTER);
		GridPane.setHalignment(tempPane, HPos.CENTER);
		GridPane.setHalignment(exit, HPos.CENTER);
		GridPane.setHalignment(error, HPos.CENTER);
		
		Scene scene = new Scene(pane,600,600);
		primaryStage.setTitle("BAAC Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public boolean isAllowed(String user, String resourceName, String ActivityCode){
		boolean allow = false;
		
		java.io.File file = new java.io.File("ResourceBaseline.txt");
		Scanner input = null;
		String data = "";
		String[] temp = null;
		int resourceIndex = 0;
		int lineNo = 0;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine(); 
			temp = data.split(";");
			if(temp[0].equalsIgnoreCase(resourceName)){
				resourceIndex = lineNo;
			}
			lineNo++;
		}
		input.close();
		
		/* Permission Granting
		 * 1 - Read & Own & Read/Write
		 * 2 - Read/Write & Own & Write
		 * 3 - Own 
		 * 4 - Read & Write & Read/Write & Own
		 */
		String permission = "";
		file = new java.io.File("AccessControlMatrix.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine(); 
			temp = data.split(";");
			if(temp[1].equalsIgnoreCase(user)){
				permission = temp[resourceIndex+2];
			}
		}
		input.close();

		if(ActivityCode.equalsIgnoreCase("1")){
			if(permission.equalsIgnoreCase("read") || permission.equalsIgnoreCase("own") || permission.equalsIgnoreCase("read/write")){
				allow = true;
			}
		}else if(ActivityCode.equalsIgnoreCase("2")){
			if(permission.equalsIgnoreCase("read") || permission.equalsIgnoreCase("own") || permission.equalsIgnoreCase("read/write")){
				allow = true;
			}
		}else if(ActivityCode.equalsIgnoreCase("3")){
			if(permission.equalsIgnoreCase("own")){
				allow=true;
			}
		}else if(ActivityCode.equalsIgnoreCase("4")){
			if(permission.equalsIgnoreCase("Blocked")){
				allow = false;
			}else {
				allow = true;
			}
		}
		
		file = new java.io.File("ManagersList.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine(); 
			temp = data.split(";");
			if(temp[0].equalsIgnoreCase(user)){
				allow = true;
			}
		}
		input.close();
		
		return allow;
	}
	
	public boolean isResource(String resourceName){
		boolean exist = false;
		
		java.io.File file = new java.io.File("ResourceBaseline.txt");
		Scanner input = null;
		String data = "";
		String[] temp = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine(); 
			temp = data.split(";");
			if(temp[0].equalsIgnoreCase(resourceName)){
				exist = true;
			}
		}
		input.close();
		
		return exist;
	}
	
	public void reportIncident(String user, String desc){
		java.io.File file = new java.io.File("IncidentReport.txt");
		Scanner input = null;
		String UserID = "";
		String[] temp = null;
		ArrayList<String> lines = new ArrayList<>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			lines.add(input.nextLine()); 
		}
		input.close();
		
		file = new java.io.File("UsersList.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			temp = input.nextLine().split(";"); 
			if(temp[0].equalsIgnoreCase(user)){
				UserID = temp[1];
			}
		}
		input.close();
		
		file = new java.io.File("ManagersList.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			temp = input.nextLine().split(";"); 
			if(temp[0].equalsIgnoreCase(user)){
				UserID = temp[1];
			}
		}
		input.close();
		Date date = new Date();
		long time = date.getTime();
		Timestamp ts = new Timestamp(time);
		file = new java.io.File("IncidentReport.txt");
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
			) {
				for(int i=0;i<lines.size();i++){
					output.println(lines.get(i));
				}
				output.println(ts+";"+user+";"+UserID+";"+desc+";");
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	public ObservableList<String> collection (ArrayList<String> option){
		ObservableList<String> options = FXCollections.observableArrayList(option);
		return options;
	}
	
	public boolean checkCode(String code){
		boolean check = false;
		
		if(code.equalsIgnoreCase("1") || code.equalsIgnoreCase("2") || code.equalsIgnoreCase("3") || code.equalsIgnoreCase("4")){
			check = true;
		}
		
		return check;
	}
	
	public boolean createManagers(String id){
		java.io.File file = new java.io.File("ManagersList.txt");
		Scanner input = null;
		boolean exist = false;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] temp = null;
		while ( input.hasNext()){
			String userInfo = input.nextLine();
			temp = userInfo.split(";");
			if(temp[0].equalsIgnoreCase(id)){
				exist = true;
			}
			managers.add(new Manager(temp[0], temp[1]));
		}
		input.close();
		
		return exist;
	}
	
	public boolean createUsers(String id){
		java.io.File file = new java.io.File("UsersList.txt");
		Scanner input = null;
		boolean exist = false;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] temp = null;
		while ( input.hasNext()){
			String userInfo = input.nextLine();
			temp = userInfo.split(";");
			if(temp[0].equalsIgnoreCase(id)){
				exist = true;
			}
			users.add(new User(temp[0], temp[1]));
		}
		input.close();
		
		return exist;
	}
	
	public boolean NetworkNodeCheck (String resource){
		boolean grantAccess = false;
		
		Map<String, String> baselineSetting = new TreeMap<String, String>();
		Map<String, String> currentSetting = new TreeMap<String, String>();
		
		java.io.File file = new java.io.File("ResourceBaseline.txt");
		Scanner input = null;
		String[] temp = null;
		String data = "";
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			baselineSetting.put(temp[0], temp[1]);
		}
		input.close();
		
		file = new java.io.File("CurrentResource.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			currentSetting.put(temp[0], temp[1]);
		}
		input.close();

		if(baselineSetting.get(resource).equalsIgnoreCase(currentSetting.get(resource))){
			grantAccess = true;
		}else {
			grantAccess = false;
			restoreSettings(baselineSetting);
		}
		
		return grantAccess;
	}
	
	public void restoreSettings(Map<String,String> baselineSetting){
		java.io.File file = new java.io.File("CurrentResource.txt");
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for (String key: baselineSetting.keySet()){
				output.println(key+";"+baselineSetting.get(key)+";");
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean RoleDeviceAC (User user, String macAddress){
		boolean grantAccess = false;
		
		java.io.File file = new java.io.File("TrustedUserDevice.txt");
		Scanner input = null;
		String[] temp = null;
		String data = "";
		ArrayList<String> device = new ArrayList<String>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			device.add(temp[0]);
		}
		input.close();
		
		for(int i=0;i<device.size();i++){
			if(device.get(i).equalsIgnoreCase(macAddress)){
				grantAccess = true;
			}
		}
		
		return grantAccess;
	}
	
	public boolean RoleDeviceAC (Manager manager, String macAddress){
		boolean grantAccess = false;
		
		java.io.File file = new java.io.File("TrustedManagerDevice.txt");
		Scanner input = null;
		String[] temp = null;
		String data = "";
		ArrayList<String> device = new ArrayList<String>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			device.add(temp[0]);
		}
		input.close();
		
		for(int i=0;i<device.size();i++){
			if(device.get(i).equalsIgnoreCase(macAddress)){
				grantAccess = true;
			}
		}
		
		return grantAccess;
	}
	
	public boolean analyzeBehavior(String user, String testCase) throws ParseException{
		boolean grantAccess = true;
		
		Map<String,String> userList = new TreeMap<String,String>();
		
		//All Activity
		ArrayList<String> userid = new ArrayList<>();
		ArrayList<String> time = new ArrayList<>();
		ArrayList<String> resourceAccessed = new ArrayList<>();
		ArrayList<String> activityCode = new ArrayList<>();
		
		//Temporary Analysis Array Index
		ArrayList<Integer> index = new ArrayList<>();
		
		//Time Checker
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		Date tempDate = null;
		int repeats=0;
		String tempResource = "None";
		
		//Office Hour Check Variables
		final String maxDate = "9999-12-31 20:00:00";
		final int Fixed = 86400000;
		final int officeHour = 50400000;
		long total = 0;
		boolean flag = false;
		
		//Retrieve Activity Data
		java.io.File file = new java.io.File(testCase);
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] temp = null;
		while ( input.hasNext()){
			String activityInfo = input.nextLine();
			temp = activityInfo.split(";");
			userList.put(temp[1], temp[0]+";"+temp[2]+";"+temp[3]);
			time.add(temp[0]);
			userid.add(temp[1]);
			resourceAccessed.add(temp[2]);
			activityCode.add(temp[3]);
		}
		input.close();
		
		/* Activity Code Definition
		 * 1 - Viewing the data in the resource (Read) <--Insider Threat of Stealing Data
		 * 2 - Adding data to the resource (Write) <-- Normal Activity
		 * 3 - Deleting Data in the resource <--Insider Threat of Sabotage
		 * 4 - Executing a function in the resource <-- Normal Activity
		 */
		boolean exist = false;
		for(Entry<String, String> m:userList.entrySet()){  
			if(m.getKey().equalsIgnoreCase(user)){
				exist = true;
				for(int i=0;i<userid.size();i++){
					if(userid.get(i).equalsIgnoreCase(user)){
						index.add(i);
					}
				}
			}
		}
		
		if(exist == true){
			//Repeated '3' Activity Threshold is 5 (Time-based, 2 seconds window)
			date = format.parse(time.get(index.get(0)));
			repeats=0;
			for(int i=1;i<index.size();i++){
				tempDate = format.parse(time.get(index.get(i)));
				if(date.getTime() == tempDate.getTime()){
					if(activityCode.get(index.get(i)).equalsIgnoreCase("3")){
						repeats++;
					}
				}
				else if((tempDate.getTime()-date.getTime()) <= 2000){
					if(activityCode.get(index.get(i)).equalsIgnoreCase("3")){
						repeats++;
					}
				}
				else {
					date = format.parse(time.get(index.get(i)));
				}
			}
			if(repeats>=5){
				grantAccess = false;
				reportIncident(user, "Repeated Deletion of Data");
			}
			
			//Repeated '1' Activity on the same resource in a row threshold is 10
			repeats=0;
			for(int i=0;i<index.size();i++){
				if(repeats>=10){
					grantAccess = false;
					reportIncident(user, "Leaking data or copying data!");
					break;
				}
				if(activityCode.get(index.get(i)).equalsIgnoreCase("1")){
					if(tempResource.equalsIgnoreCase("None")){
						tempResource = resourceAccessed.get(index.get(i));
					}else if(tempResource.equalsIgnoreCase(resourceAccessed.get(index.get(i)))){
						repeats++;
					}else {
						tempResource = resourceAccessed.get(index.get(i));
						repeats=0;
					}
				}
			}
			
			//Off-Office Hour Activity Detection (Buffer 2hours)
			/*
			 * 86400000 Milliseconds = 24 hours
			 * 50400000 Milliseconds Office Hours Max Duration
			 */
			
			date = format.parse(maxDate);
			
			for(int i=0;i<index.size();i++){
				tempDate = format.parse(time.get(index.get(i)));
				total = (date.getTime() - tempDate.getTime())%Fixed;
				if(total > officeHour || total <0){
					flag = true;
				}
			}
			if(flag == true){
				grantAccess = false;
				reportIncident(user, "Off Office Hours Request!");
			}
		}
		
		//Results of the Analysis
		return grantAccess;
	}
	
	public static void main (String [] args){
		Application.launch(args);
	}
}
