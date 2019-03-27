import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SmartAC extends Application{
	public void start(Stage primaryStage){
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(50);
		
		Button exit = new Button("Close");
		exit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				System.exit(0);
			}
		});
		
		Label title = new Label("Smart Access Control");
		title.setFont(Font.font("Arial",FontWeight.BOLD,50));
		Label welcome = new Label("Welcome Manager!");
		welcome.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets(20,20,20,20));
		pane.setVgap(40);
		pane.setHgap(80); 
		
		//View Behavior Log Button
		Button viewLog = new Button("View Behavior Logs");
		viewLog.setId("shinyOrange");
		viewLog.setWrapText(true);
		viewLog.setMinHeight(100);
		viewLog.setMinWidth(150);
		viewLog.setMaxHeight(100);
		viewLog.setMaxWidth(150);
		viewLog.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ViewBehaviorLogs(primaryStage);
			}
		});
		
		//View Access Control Matrix Button
		Button viewACM = new Button("View Access Control Matrix");
		viewACM.setId("shinyOrange");
		viewACM.setWrapText(true);
		viewACM.setMinHeight(100);
		viewACM.setMinWidth(150);
		viewACM.setMaxHeight(100);
		viewACM.setMaxWidth(150);
		viewACM.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ViewAccessControlMatrix(primaryStage);
			}
		});
		
		//Manage Access Control Button
		Button manageAC = new Button("Manage Access Control");
		manageAC.setId("shinyOrange");
		manageAC.setWrapText(true);
		manageAC.setMinHeight(100);
		manageAC.setMinWidth(150);
		manageAC.setMaxHeight(100);
		manageAC.setMaxWidth(150);
		manageAC.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ManageAccessControl(primaryStage);
			}
		});
		
		//View List of trusted device Button
		Button viewTD = new Button("View List of Trusted Device");
		viewTD.setId("shinyOrange");
		viewTD.setWrapText(true);
		viewTD.setMinHeight(100);
		viewTD.setMinWidth(150);
		viewTD.setMaxHeight(100);
		viewTD.setMaxWidth(150);
		viewTD.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ViewListOfTrustedDevice(primaryStage);
			}
		});
		
		pane.add(viewLog, 0, 0);
		pane.add(viewACM, 1, 0);
		pane.add(manageAC, 0, 1);
		pane.add(viewTD, 1, 1);
		
		mainPane.add(title, 0, 1);
		mainPane.add(welcome, 0, 2);
		mainPane.add(pane, 0, 3);
		mainPane.add(exit, 0, 4);
		
		GridPane.setHalignment(viewLog, HPos.CENTER);
		GridPane.setHalignment(viewACM, HPos.CENTER);
		GridPane.setHalignment(manageAC, HPos.CENTER);
		GridPane.setHalignment(viewTD, HPos.CENTER);
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(pane, HPos.CENTER);
		GridPane.setHalignment(exit, HPos.CENTER);
		
		Scene scene = new Scene(mainPane,800,800);
		scene.getStylesheets().add("Button.css");
		primaryStage.setTitle("Smart Access Control Configuration Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ViewBehaviorLogs(Stage primaryStage){
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setVgap(30);
		
		Label title = new Label("Smart Access Control");
		title.setFont(Font.font("Arial",FontWeight.BOLD,50));
		Label welcome = new Label("Activity Log");
		welcome.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		TextArea activityLog = new TextArea();
		activityLog.setEditable(false);
		activityLog.setMinWidth(700);
		TextArea incidents = new TextArea();
		incidents.setEditable(false);
		incidents.setMinWidth(700);
		incidents.setVisible(false);
		
		java.io.File file = new java.io.File("Activity.txt");
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
			activityLog.appendText(temp[2]+" ===== "+temp[0]+" : " + temp[1]+" -> ");
			
			if(temp[3].equalsIgnoreCase("1")){
				activityLog.appendText("Viewing the data in the resource\n");
			}else if (temp[3].equalsIgnoreCase("2")){
				activityLog.appendText("Adding/Writing data to the resource\n");
			}else if (temp[3].equalsIgnoreCase("3")){
				activityLog.appendText("Deleting Data in the resource\n");
			}else if (temp[3].equalsIgnoreCase("4")){
				activityLog.appendText("Executing a function in the resource\n");
			}
		}
		input.close();
		
		file = new java.io.File("IncidentReport.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			incidents.appendText(temp[0]+" : "+temp[1]+"("+temp[2]+") : "+temp[3]+"\n");
		}
		input.close();
		
		Button viewLog = new Button("Review Activity Log");
		Button review = new Button("Review Incident Reports");
		viewLog.setVisible(false);
		viewLog.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				welcome.setText("Activity Log");
				activityLog.setVisible(true);
				incidents.setVisible(false);
				review.setVisible(true);
				viewLog.setVisible(false);
			}
		});
		
		review.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				welcome.setText("Incident History");
				review.setVisible(false);
				viewLog.setVisible(true);
				activityLog.setVisible(false);
				incidents.setVisible(true);
			}
		});
		
		Button back = new Button("Back To Menu");
		back.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				start(primaryStage);
			}
		});
		
		mainPane.add(title, 0, 0);
		mainPane.add(welcome, 0, 1);
		mainPane.add(activityLog, 0, 2);
		mainPane.add(incidents, 0, 2);
		mainPane.add(viewLog, 0, 3);
		mainPane.add(review, 0, 3);
		mainPane.add(back, 0, 4);
		
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(activityLog, HPos.CENTER);
		GridPane.setHalignment(incidents, HPos.CENTER);
		GridPane.setHalignment(viewLog, HPos.CENTER);
		GridPane.setHalignment(review, HPos.CENTER);
		GridPane.setHalignment(back, HPos.CENTER);
		
		Scene scene = new Scene(mainPane,800,800);
		primaryStage.setTitle("Smart Access Control Configuration Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ViewAccessControlMatrix(Stage primaryStage){
		Map<String, String> Setting = new TreeMap<String, String>();
		ArrayList<String> UID = new ArrayList<String>();
		ArrayList<String> uRole = new ArrayList<String>();
		ArrayList<ArrayList<String>> uPermission = new ArrayList<>();
		ArrayList<String> choice = new ArrayList<String>();
		choice.add("Read");
		choice.add("Write");
		choice.add("Blocked");
		choice.add("Read/Write");
		choice.add("Own");
		int noOfResource = 0, count = 0;
		
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
			Setting.put(temp[0], temp[1]);
			noOfResource++;
		}
		input.close();
		
		int userCount = 0;
		file = new java.io.File("UsersList.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			userCount++;
		}
		input.close();
		
		for(int i=0;i<userCount;i++){
			uPermission.add(new ArrayList<String>());
		}
		
		count = 0;
		file = new java.io.File("AccessControlMatrix.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			UID.add(temp[1]);
			uRole.add(temp[0]);
			
			for(int i=2;i<temp.length;i++){
				uPermission.get(count).add(temp[i]);
			}
			count++;
		}
		input.close();
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setVgap(10);
		
		Label title = new Label("Smart Access Control");
		title.setFont(Font.font("Arial",FontWeight.BOLD,50));
		Label welcome = new Label("View Access Control Matrix");
		welcome.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		GridPane tablePane = new GridPane();
		tablePane.setAlignment(Pos.CENTER);
		tablePane.setPadding(new Insets(15,15,15,15));
		tablePane.setHgap(20);
		tablePane.setVgap(20);
		tablePane.setStyle("-fx-border-color: black;");
		//tablePane.setGridLinesVisible(true);
		
		Label UserID = new Label("User ID");
		UserID.setFont(Font.font("Times New Roman",FontWeight.BOLD,20));
		Label UserRole = new Label("User Name");
		UserRole.setFont(Font.font("Times New Roman",FontWeight.BOLD,20));
		ArrayList<Label> UIDList = new ArrayList<Label>();
		ArrayList<Label> usernames = new ArrayList<Label>();
		ArrayList<Label> resources = new ArrayList<Label>();
		ArrayList<ComboBox<String>> permission = new ArrayList<ComboBox<String>>();
		tablePane.add(UserID, 0, 0);
		tablePane.add(UserRole, 1, 0);
		int coordinate = 2;
		int counter = 0;
		int comboboxCounter = 0;
		
		for(int i=0;i<UID.size();i++){
			for(int j=0;j<noOfResource;j++){
				permission.add(new ComboBox<String>(collection(choice)));
				permission.get(comboboxCounter).getSelectionModel().select(uPermission.get(i).get(j));
				comboboxCounter++;
			}
		}
		
		for(String key : Setting.keySet()){
			resources.add(new Label(key));
			resources.get(counter).setFont(Font.font("Times New Roman",FontWeight.BOLD,20));
			tablePane.add(resources.get(counter), coordinate, 0);
			coordinate++;
			counter++;
		}
		
		comboboxCounter = 0;
		for(int i=0;i<UID.size();i++){
			UIDList.add(new Label(UID.get(i)));
			usernames.add(new Label(uRole.get(i)));
			tablePane.add(UIDList.get(i), 0, i+1);
			tablePane.add(usernames.get(i), 1, i+1);
			for(int j=0;j<noOfResource;j++){
				tablePane.add(permission.get(comboboxCounter), j+2, i+1);
				comboboxCounter++;
			}
		}
		
		GridPane editPane = new GridPane();
		editPane.setAlignment(Pos.CENTER);
		editPane.setPadding(new Insets(15,15,15,15));
		editPane.setHgap(10);
		
		GridPane confirmPane = new GridPane();
		confirmPane.setAlignment(Pos.CENTER);
		confirmPane.setPadding(new Insets(15,15,15,15));
		confirmPane.setHgap(10);
		confirmPane.setVisible(false);
		
		GridPane tempPane = new GridPane();
		tempPane.setAlignment(Pos.CENTER);
		tempPane.setPadding(new Insets(15,15,15,15));
		tempPane.setVisible(false);
		tempPane.setHgap(10);
		
		/*EditPane Buttons*/
		Button back = new Button("Back To Menu");
		back.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				start(primaryStage);
			}
		});
		
		Button editResource = new Button("Edit Resources");
		editResource.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				editPane.setVisible(false);
				tempPane.setVisible(true);
			}
		});
		
		Button saveChange = new Button("Save Changes");
		saveChange.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				editPane.setVisible(false);
				confirmPane.setVisible(true);
			}
		});
		/*END*/
		
		/*tempPane Components*/
		Label newResource = new Label("Add New Resource");
		TextField txtResource = new TextField();
		txtResource.setPromptText("Resource Name");
		TextField portNo = new TextField();
		portNo.setPromptText("XXX,ZZ,YY (0-65535)");
		Label error = new Label();
		error.setTextFill(Color.web("#ff0000"));
		Button add = new Button("Add");
		add.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				boolean checker = true;
				boolean isSame = false;
				String[] temp = portNo.getText().split(",");
				int port;
				for(int i=0;i<temp.length;i++){
					try{
						port = Integer.parseInt(temp[i]);
						if(port<0 || port>65535){
							checker=false;
						}
					}
					catch (Exception e){
						checker = false;
					}
				}
				
				java.io.File file = new java.io.File("ResourceBaseline.txt");
				Scanner input = null;
				String data = "";
				try {
					input = new Scanner(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				while ( input.hasNext()){
					data = input.nextLine();
					temp = data.split(";");
					if(temp[0].equals(txtResource.getText())){
						isSame = true;
					}
				}
				input.close();
				
				if(txtResource.getText().equalsIgnoreCase("")){
					error.setText("Please Enter Resource Name");
				}else if(checker == false){
					error.setText("Port Number Out of Range!");
				}else if(isSame == true){
					error.setText("Resource Already Exist!");
				}else{
					addResource(txtResource.getText(), portNo.getText());
					ViewAccessControlMatrix(primaryStage);
				}
			}
		});
		
		Button delete = new Button("Delete");
		delete.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				java.io.File file = new java.io.File("ResourceBaseline.txt");
				Scanner input = null;
				String[] temp = null;
				String data = "";
				boolean isSame = false;
				try {
					input = new Scanner(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				while ( input.hasNext()){
					data = input.nextLine();
					temp = data.split(";");
					if(temp[0].equals(txtResource.getText())){
						isSame = true;
					}
				}
				input.close();
				
				if(isSame == false){
					error.setText("Resource does not exist!");
				}else {
					deleteResource(txtResource.getText());
					ViewAccessControlMatrix(primaryStage);
				}
			}
		});
		
		Button cancelEdit = new Button("Cancel");
		cancelEdit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				tempPane.setVisible(false);
				editPane.setVisible(true);
			}
		});
		//END
		
		/*ConfirmPane Components*/
		Label sure = new Label("Are you sure?");
		Button yes = new Button("YES");
		Button no = new Button("NO");
		
		yes.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				int comboboxCounter = 0;
				int no = 0;
				
				/*Count Number Of Resource*/
				java.io.File file = new java.io.File("AccessControlMatrix.txt");
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
					no = temp.length - 2;
				}
				input.close();
				
				try (
						java.io.PrintWriter output = new java.io.PrintWriter(file);
				) {
					for(int i=0;i<UID.size();i++){
						output.print(UIDList.get(i).getText()+";");
						output.print(usernames.get(i).getText()+";");
						for(int j=0;j<no;j++){
							output.print(permission.get(comboboxCounter).getSelectionModel().getSelectedItem()+";");
							comboboxCounter++;
						}
						output.println();
					}
					output.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				ViewAccessControlMatrix(primaryStage);
			}
		});
		
		no.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				confirmPane.setVisible(false);
				editPane.setVisible(true);
			}
		});
		
		confirmPane.add(sure, 0, 0);
		confirmPane.add(no, 1, 0);
		confirmPane.add(yes, 2, 0);
		
		tempPane.add(newResource, 0, 0);
		tempPane.add(txtResource, 1, 0);
		tempPane.add(cancelEdit, 1, 1);
		tempPane.add(portNo, 2, 0);
		tempPane.add(error, 2, 1);
		tempPane.add(add, 3, 0);
		tempPane.add(delete, 4, 0);
		
		editPane.add(back, 0, 0);
		editPane.add(editResource, 1, 0);
		editPane.add(saveChange, 2, 0);
		
		mainPane.add(title, 0, 0);
		mainPane.add(welcome, 0, 1);
		mainPane.add(tablePane, 0, 2);
		mainPane.add(editPane, 0, 3);
		mainPane.add(tempPane, 0, 3);
		mainPane.add(confirmPane, 0, 3);
		
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(tablePane, HPos.CENTER);
		GridPane.setHalignment(editPane, HPos.CENTER);
		GridPane.setHalignment(tempPane, HPos.CENTER);
		GridPane.setHalignment(confirmPane, HPos.CENTER);
		GridPane.setHalignment(cancelEdit, HPos.CENTER);
		
		Scene scene = new Scene(mainPane,800,800);
		primaryStage.setTitle("Smart Access Control Configuration Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void deleteResource(String resourceName){
		java.io.File file = new java.io.File("ResourceBaseline.txt");
		Scanner input = null;
		String data = "";
		String[] temp = null;
		int index = 0;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				temp = lines.get(i).split(";");
				if(!(temp[0].equalsIgnoreCase(resourceName))){
					output.println(lines.get(i));
				}else {
					index = i;
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		lines.clear();
		file = new java.io.File("CurrentResource.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				temp = lines.get(i).split(";");
				if(!(temp[0].equalsIgnoreCase(resourceName))){
					output.println(lines.get(i));
				}
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		lines.clear();
		file = new java.io.File("AccessControlMatrix.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				temp = lines.get(i).split(";");
				output.print(temp[0]+";");
				output.print(temp[1]+";");
				for(int y=2;y<temp.length;y++){
					if(y != (index+2)){
						output.print(temp[y]+";");
					}
				}
				output.println();
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addResource (String resourceName, String portNo){
		java.io.File file = new java.io.File("ResourceBaseline.txt");
		Scanner input = null;
		String data = "";
		ArrayList<String> lines = new ArrayList<String>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				output.println(lines.get(i));
			}
			output.println(resourceName+";"+portNo+";");
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		lines.clear();
		file = new java.io.File("CurrentResource.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				output.println(lines.get(i));
			}
			output.println(resourceName+";"+portNo+";");
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		lines.clear();
		file = new java.io.File("AccessControlMatrix.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			lines.add(data);
		}
		input.close();
		
		try (
				java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<lines.size();i++){
				output.println(lines.get(i)+"Blocked;");
			}
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void ManageAccessControl(Stage primaryStage){
		java.io.File file = new java.io.File("AccessControlMatrix.txt");
		Scanner input = null;
		String data = "";
		String [] temp = null;
		int noOfResource = 0;
		ArrayList<String> lines = new ArrayList<String>();
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			noOfResource = temp.length-2;
			lines.add(data);
		}
		input.close();
		
		ArrayList<String> resourceName = new ArrayList<>();
		file = new java.io.File("ResourceBaseline.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			resourceName.add(temp[0]);
		}
		input.close();
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setHgap(15);
		mainPane.setVgap(10);
		
		GridPane tempPane = new GridPane();
		tempPane.setAlignment(Pos.CENTER);
		tempPane.setPadding(new Insets(20,20,20,20));
		tempPane.setHgap(15);
		tempPane.setVgap(5);
		tempPane.setVisible(false);
		
		GridPane deletePane = new GridPane();
		deletePane.setAlignment(Pos.CENTER);
		deletePane.setPadding(new Insets(20,20,20,20));
		deletePane.setHgap(15);
		deletePane.setVgap(5);
		deletePane.setVisible(false);
		
		Label title = new Label("Smart Access Control");
		title.setFont(Font.font("Arial",FontWeight.BOLD,50));
		Label welcome = new Label("Manage Access Control");
		welcome.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		ArrayList<Label> resource = new ArrayList<>();
		ArrayList<ComboBox<String>> rules = new ArrayList<>();
		ArrayList<String> choice = new ArrayList<String>();
		choice.add("Read");
		choice.add("Write");
		choice.add("Blocked");
		choice.add("Read/Write");
		choice.add("Own");
		
		Label name = new Label("New User Name : ");
		Label UID = new Label("New User ID : ");
		TextField txtName = new TextField();
		txtName.setPromptText("Enter User Name");
		TextField txtUID = new TextField();
		txtUID.setPromptText("Enter User ID");
		Label error = new Label();
		error.setTextFill(Color.web("#ff0000"));
		tempPane.add(name, 0, 0);
		tempPane.add(txtName, 1, 0);
		tempPane.add(UID, 0, 1);
		tempPane.add(txtUID, 1, 1);
		int paneLength = 0;
		for(int i=0;i<noOfResource;i++){
			resource.add(new Label(resourceName.get(i)));
			rules.add(new ComboBox<String>(collection(choice)));
			rules.get(i).getSelectionModel().select("Blocked");
			GridPane.setHalignment(rules.get(i), HPos.CENTER);
			tempPane.add(resource.get(i), 0, i+2);
			tempPane.add(rules.get(i), 1, i+2);
			paneLength = i+2;
		}
		
		Button addUserBtn = new Button("Add New User");
		Button deleteUserBtn = new Button("Delete a User");
		addUserBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				addUserBtn.setVisible(false);
				tempPane.setVisible(true);
				deleteUserBtn.setVisible(false);
			}
		});
		
		Button cancel = new Button("Cancel");
		cancel.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ManageAccessControl(primaryStage);
			}
		});
		
		Button confirmAdd = new Button("Confirm");
		confirmAdd.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String[] temp = null;
				boolean sameUID = false;
				boolean sameUserName = false;
				for(int i=0;i<lines.size();i++){
					temp = lines.get(i).split(";");
					if(temp[0].equalsIgnoreCase(txtUID.getText())){
						sameUID = true;
					}
					if(temp[1].equalsIgnoreCase(txtName.getText())){
						sameUserName = true;
					}
				}
				
				if(txtUID.getText().equalsIgnoreCase("")){
					error.setText("User ID was left empty! Invalid Input!");
				}else if(txtName.getText().equalsIgnoreCase("")){
					error.setText("User Name was left empty! Invalid Input!");
				}else if(sameUID == true){
					error.setText("User ID Already Exist!");
				}else if(sameUserName == true){
					error.setText("User Name Already Exist!");
				}else {
					java.io.File file = new java.io.File("AccessControlMatrix.txt");
					try (
							java.io.PrintWriter output = new java.io.PrintWriter(file);
					) {
						for(int i=0;i<lines.size();i++){
							output.println(lines.get(i));
						}
						output.print(txtName.getText()+";"+txtUID.getText()+";");
						for(int i=0;i<rules.size();i++){
							output.print(rules.get(i).getSelectionModel().getSelectedItem()+";");
						}
						output.println();
						output.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					file = new java.io.File("UsersList.txt");
					Scanner input = null;
					ArrayList<String> data = new ArrayList<>();
					try {
						input = new Scanner(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					while ( input.hasNext()){
						data.add(input.nextLine());
					}
					input.close();
					
					file = new java.io.File("UsersList.txt");
					try (
							java.io.PrintWriter output = new java.io.PrintWriter(file);
					) {
						for(int i=0;i<data.size();i++){
							output.println(data.get(i));
						}
						output.println(txtName.getText()+";"+txtUID.getText()+";");
						output.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					ManageAccessControl(primaryStage);
				}
			}
		});
		
		deleteUserBtn.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				addUserBtn.setVisible(false);
				deletePane.setVisible(true);
				deleteUserBtn.setVisible(false);
			}
		});
		
		Label toDelete = new Label("User ID to Delete : ");
		TextField txtDelete = new TextField();
		txtDelete.setPromptText("Enter User ID");
		
		Button deleteUser = new Button("Delete User");
		deleteUser.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				String[] temp = null;
				boolean sameUID = false;
				for(int i=0;i<lines.size();i++){
					temp = lines.get(i).split(";");
					if(temp[0].equalsIgnoreCase(txtDelete.getText())){
						sameUID = true;
					}
				}
				
				if(txtDelete.getText().equalsIgnoreCase("")){
					error.setText("User ID was left empty! Invalid Input!");
				}else if(sameUID == false){
					error.setText("User ID Does Not Exist!");
				}else {
					java.io.File file = new java.io.File("AccessControlMatrix.txt");
					try (
							java.io.PrintWriter output = new java.io.PrintWriter(file);
					) {
						for(int i=0;i<lines.size();i++){
							temp = lines.get(i).split(";");
							if(!(temp[0].equalsIgnoreCase(txtDelete.getText()))){
								output.println(lines.get(i));
							}
						}
						output.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					file = new java.io.File("UsersList.txt");
					Scanner input = null;
					ArrayList<String> data = new ArrayList<>();
					try {
						input = new Scanner(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					while ( input.hasNext()){
						data.add(input.nextLine());
					}
					input.close();
					
					file = new java.io.File("UsersList.txt");
					try (
							java.io.PrintWriter output = new java.io.PrintWriter(file);
					) {
						for(int i=0;i<data.size();i++){
							temp = data.get(i).split(";");
							if(!(temp[0].equalsIgnoreCase(txtDelete.getText()))){
								output.println(data.get(i));
							}
						}
						output.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					
					ManageAccessControl(primaryStage);
				}
			}
		});
		
		Button deleteCancel = new Button("Cancel");
		deleteCancel.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				ManageAccessControl(primaryStage);
			}
		});
		
		deletePane.add(toDelete, 0, 0);
		deletePane.add(txtDelete, 1, 0);
		deletePane.add(deleteUser, 2, 0);
		deletePane.add(deleteCancel, 3, 0);
		
		Button back = new Button("Back To Main Menu");
		back.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				start(primaryStage);
			}
		});
		
		tempPane.add(cancel, 0, paneLength+1);
		tempPane.add(confirmAdd, 1, paneLength+1);
		
		mainPane.add(title, 0, 0);
		mainPane.add(welcome, 0, 1);
		mainPane.add(tempPane, 0, 2);
		mainPane.add(addUserBtn, 0, 2);
		mainPane.add(deleteUserBtn, 0, 3);
		mainPane.add(deletePane, 0, 3);
		mainPane.add(error, 0, 4);
		mainPane.add(back, 0, 5);
		
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(tempPane, HPos.CENTER);
		GridPane.setHalignment(addUserBtn, HPos.CENTER);
		GridPane.setHalignment(deletePane, HPos.CENTER);
		GridPane.setHalignment(deleteUserBtn, HPos.CENTER);
		GridPane.setHalignment(back, HPos.CENTER);
		GridPane.setHalignment(error, HPos.CENTER);
		
		Scene scene = new Scene(mainPane,800,800);
		primaryStage.setTitle("Smart Access Control Configuration Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ViewListOfTrustedDevice(Stage primaryStage){
		ArrayList<String> uDevice = new ArrayList<String>();
		ArrayList<String> mDevice = new ArrayList<String>();
		String data = "";
		
		GridPane mainPane = new GridPane();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(20,20,20,20));
		mainPane.setHgap(30);
		mainPane.setVgap(10);
		
		Label title = new Label("Smart Access Control");
		title.setFont(Font.font("Arial",FontWeight.BOLD,50));
		Label welcome = new Label("View List of trusted device");
		welcome.setFont(Font.font("Times New Roman",FontWeight.BOLD,30));
		
		GridPane displayPane = new GridPane();
		displayPane.setAlignment(Pos.CENTER);
		displayPane.setPadding(new Insets(20,20,20,20));
		displayPane.setHgap(20);
		displayPane.setVgap(5);
		
		Label UserDevices = new Label("Trusted User Devices (MAC Address)");
		Label ManagerDevices = new Label("Trusted Manager Devices (MAC Address)");
		TextArea userMAC = new TextArea();
		userMAC.setEditable(false);
		TextArea managerMAC = new TextArea();
		managerMAC.setEditable(false);
		displayPane.add(UserDevices, 0, 0);
		displayPane.add(ManagerDevices, 1, 0);
		displayPane.add(userMAC, 0, 1);
		displayPane.add(managerMAC, 1, 1);
		
		java.io.File file = new java.io.File("TrustedUserDevice.txt");
		Scanner input = null;
		String[] temp = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			uDevice.add(temp[0]);
		}
		input.close();
		
		file = new java.io.File("TrustedManagerDevice.txt");
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while ( input.hasNext()){
			data = input.nextLine();
			temp = data.split(";");
			mDevice.add(temp[0]);
		}
		input.close();
		
		for(int i=0;i<uDevice.size();i++){
			userMAC.appendText("MAC Address = "+uDevice.get(i)+"\n");
		}
		
		for(int i=0;i<mDevice.size();i++){
			managerMAC.appendText("MAC Address = "+mDevice.get(i)+"\n");
		}
		
		final ComboBox<String> cbUser = new ComboBox<String>(collection(uDevice));
		final ComboBox<String> cbManager = new ComboBox<String>(collection(mDevice));
		displayPane.add(cbUser, 0, 2);
		displayPane.add(cbManager, 1, 2);
		
		Button removeUser = new Button("Remove User MAC Address");
		Button removeManager = new Button("Remove Manager MAC Address");
		
		removeUser.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				removeMAC(cbUser.getSelectionModel().getSelectedItem(), "TrustedUserDevice.txt");
				ViewListOfTrustedDevice(primaryStage);
			}
		});
		
		removeManager.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				removeMAC(cbManager.getSelectionModel().getSelectedItem(), "TrustedManagerDevice.txt");
				ViewListOfTrustedDevice(primaryStage);
			}
		});
		
		displayPane.add(removeUser, 0, 3);
		displayPane.add(removeManager, 1, 3);
		
		GridPane addPane = new GridPane();
		addPane.setAlignment(Pos.CENTER);
		addPane.setPadding(new Insets(20,20,20,20));
		addPane.setHgap(10);
		
		Label desc = new Label("Add New MAC Address : ");
		TextField txtMAC = new TextField();
		txtMAC.setPromptText("XX:XX:XX:XX:XX:XX");
		Button addUser = new Button("Add to User");
		Button addManager = new Button("Add to Manager");
		Button cancel = new Button("Cancel");
		
		addPane.add(desc, 0, 0);
		addPane.add(txtMAC, 1, 0);
		addPane.add(addUser, 2, 0);
		addPane.add(addManager, 3, 0);
		addPane.add(cancel, 4, 0);
		
		Button addMAC = new Button("Add New Trusted Device");
		
		addUser.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				char[] temp = txtMAC.getText().toCharArray();
				boolean valid = false;
				int count = 0;
				for(int i=0;i<temp.length;i++){
					if(temp[i] == ':' || temp[i] == ')' || temp[i] == '(' || temp[i] == '*' 
							|| temp[i] == '&' || temp[i] == '^' || temp[i] == '%' || temp[i] == '$' 
							|| temp[i] == '#' || temp[i] == '@' || temp[i] == '-' || temp[i] == '_' 
							|| temp[i] == '=' || temp[i] == '+' || temp[i] == '{' || temp[i] == '}' 
							|| temp[i] == '[' || temp[i] == ']' || temp[i] == '|' || temp[i] == ';' 
							|| temp[i] == ':' || temp[i] == '"' || temp[i] == ',' || temp[i] == '.' 
							|| temp[i] == '<' || temp[i] == '>' || temp[i] == '/' || temp[i] == '?' 
							|| temp[i] == '`' || temp[i] == '~' || temp[i] == '!' ){
						count++;
					}
				}
				
				if(count == 5){
					valid = true;
				}
				
				if(valid == true && temp.length == 17 && temp[2] == ':' && temp[5] == ':' && temp[8] == ':' 
						&& temp[11] == ':' && temp[14] == ':'){
					plusMAC(txtMAC.getText(), "TrustedUserDevice.txt");
					ViewListOfTrustedDevice(primaryStage);
				}
				else{
					txtMAC.setText("Invalid MAC Address");
				}
			}
		});
		
		addManager.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				char[] temp = txtMAC.getText().toCharArray();
				if(temp.length == 17 && temp[2] == ':' && temp[5] == ':' && temp[8] == ':' && temp[11] == ':' && temp[14] == ':'){
					plusMAC(txtMAC.getText(), "TrustedManagerDevice.txt");
					ViewListOfTrustedDevice(primaryStage);
				}
				else {
					txtMAC.setText("Invalid MAC Address");
				}
			}
		});
		
		cancel.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mainPane.getChildren().remove(addPane);
				mainPane.add(addMAC, 0, 4);
				txtMAC.setText("");
			}
		});
		
		addMAC.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				mainPane.getChildren().remove(addMAC);
				mainPane.add(addPane, 0, 4);
			}
		});
		
		Button back = new Button("Back To Menu");
		back.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				start(primaryStage);
			}
		});
		
		mainPane.add(title, 0, 0);
		mainPane.add(welcome, 0, 1);
		mainPane.add(displayPane, 0, 2);
		mainPane.add(addMAC, 0, 4);
		mainPane.add(back, 0, 5);
		
		GridPane.setHalignment(title, HPos.CENTER);
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(displayPane, HPos.CENTER);
		GridPane.setHalignment(addMAC, HPos.CENTER);
		GridPane.setHalignment(addPane, HPos.CENTER);
		GridPane.setHalignment(back, HPos.CENTER);
		
		Scene scene = new Scene(mainPane,800,800);
		primaryStage.setTitle("Smart Access Control Configuration Program");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void plusMAC(String macAddress, String fileName){
		java.io.File file = new java.io.File(fileName);
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
		
		try (
			java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<device.size();i++){
				output.println(device.get(i)+";");
			}
			output.println(macAddress+";");
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void removeMAC (String macAddress, String fileName){
		java.io.File file = new java.io.File(fileName);
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
		
		try (
			java.io.PrintWriter output = new java.io.PrintWriter(file);
		) {
			for(int i=0;i<device.size();i++){
				if(!(device.get(i).equalsIgnoreCase(macAddress))){
					output.println(device.get(i)+";");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<String> collection (ArrayList<String> option){
		ObservableList<String> options = FXCollections.observableArrayList(option);
		return options;
	}
	
	public static void main (String [] args){
		Application.launch(args);
	}
}
