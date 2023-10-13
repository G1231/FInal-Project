package finalproject;
import java.util.Arrays;
import java.util.Date;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class display extends Application {
	//initializing objects in order to use on a larger scope
    private User user;
    private LogBook logBook = new LogBook();
    TableView<LogEntry> table = new TableView<>();
    ObservableList<LogEntry> entry = FXCollections.observableArrayList();
    VBox workoutDisplay = workoutLayout();
    VBox userdisplay = userLayout();
    VBox logBookDisplay = LogBookLayout();	


    @Override
    public void start(Stage primaryStage) {
    	user = new User("test", 0, 0.0, ""); // making empty user as a placeholder for values
        logBook = new LogBook(); //making empty logbook

        // Create a tab pane to switch between user information, work-outs, and logbook display
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(new Tab("User", userdisplay), new Tab("Log Exercises", workoutDisplay), new Tab("Logbook", logBookDisplay));
        workoutDisplay.setDisable(true); //Preventing user from inputting information in workout section before inputting user info
        Scene scene = new Scene(tabPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LogBook Application");
        primaryStage.show();
        
    }
    //layout for a display where users input their workout information
    public VBox workoutLayout() {
    	//creating observable lists to use for combo boxes
    	ObservableList<String> listOfWorkouts = FXCollections.observableArrayList(
    			"running",
    			"swimming",
    			"cycling",
    			"weight lifting",
    			"rowing",
    			"calisthenics",
    			"walking",
    			"skateboarding",
    			"dancing",
    			"hiking",
    			"soccer",
    			"basketball",
    			"football");
    	ObservableList<String> intensities = FXCollections.observableArrayList("low", "moderate", "high");
    	
    	//creating vbox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        
        //creating textfields, combo boxes, and buttons
        ComboBox exercisesC = new ComboBox(listOfWorkouts);
        TextField durationT = new TextField();
        TextFormatter<Double> textFormatterDuration = new TextFormatter<>(new DoubleStringConverter());
        durationT.setTextFormatter(textFormatterDuration);
        ComboBox intensityC = new ComboBox(intensities);
        Button saveButton = new Button("Save Workout");
        
        //when clicking the button
        saveButton.setOnAction(e -> {
        	if (noWorkoutInput(exercisesC, durationT, intensityC)) {//if all input fields aren't blank, then
        		
            // Parse and save user info
            String exercise = (String) exercisesC.getValue();
            Double duration =  Double.parseDouble(durationT.getText());
            String intensity = (String) intensityC.getValue();
            Date date = new Date();
            
            //create an entry and updating it
            LogEntry tempLog = new LogEntry(user.getName(), exercise, duration, intensity, date);
            double cal = tempLog.calculateCaloriesBurnt(user); //calculating calories from entry, then adding calories onto the entry
            LogEntry log = new LogEntry(user.getName(), exercise, duration, intensity, date, cal);
            
            //clearing all controls after clicking save
            exercisesC.valueProperty().set(null);
            durationT.clear();
            intensityC.valueProperty().set(null);
            
            //adding the entry into the table and logbook
            entry.add(log);
            logBook.addLogEntry(log);
            }
        });
        //adding all the combo boxes, text fields, and button
        layout.getChildren().addAll(
                new Label("Exercises"),
                exercisesC,
                new Label("Intensity:"),
                intensityC,
                new Label("Duration (Minutes)"),
                durationT,
                saveButton
        );

        return layout;
    }
    //Layout for a display where user can enter private information for calorie calculations
    private VBox userLayout() {
    	
    	//creating vbox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        
        //gender list for combo box
        ObservableList<String> genders = FXCollections.observableArrayList("male", "female");

        // Creating user input fields
        TextField nameT = new TextField();
        
        TextField ageT = new TextField();
        
        //Formatting text field for age so that it only allows an integer or removes user answer
        TextFormatter<Integer> textFormatterAge = new TextFormatter<>(new IntegerStringConverter());
        ageT.setTextFormatter(textFormatterAge);
        
        TextField weightT = new TextField();
        
        //Formatting text field for weight so that it only allows a double or removes user answer
        TextFormatter<Double> textFormatterWeight = new TextFormatter<>(new DoubleStringConverter());
        weightT.setTextFormatter(textFormatterWeight);
        
        
        ComboBox genderC = new ComboBox(genders);
        
        
        //Creating save button
        Button saveButton = new Button("Save User Info");
        saveButton.setOnAction(e -> {
        	if (noUserInput(nameT, ageT, weightT, genderC)) { //checking if inputs are blank
        		
            // Parse and save user info
            String name = nameT.getText();
            int age = Integer.parseInt(ageT.getText());
            double weight = Double.parseDouble(weightT.getText());
            String gender = (String) genderC.getValue();
            user.setAge(age);
            user.setGender(gender);
            user.setName(name);
            user.setWeight(weight);
            
            
            //clearing all fields once button is clicked
            nameT.clear();
            ageT.clear();
            weightT.clear();
            genderC.valueProperty().set(null);
            
            //allowing user to access workout tab after entering user info
            workoutDisplay.setDisable(false);
            }
        });
        //adding all text fields, combo boxes, and buttons
        layout.getChildren().addAll(
                new Label("Name:"),
                nameT,
                new Label("Age:"),
                ageT,
                new Label("Weight:"),
                weightT,
                new Label("Gender:"),
                genderC,
                
                saveButton
        );

        return layout;
    }
    //creating method to check if user left any inputs blank on userDisplay
    private boolean noUserInput(TextField nameT, TextField ageT, TextField weightT, ComboBox genderC) {
        if (nameT.getText().isEmpty() || ageT.getText().isEmpty() || weightT.getText().isEmpty() || genderC.getValue() == null) {
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please fill in all user information fields.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    //creating method to check if user left any inputs blank on workoutDisplay
    private boolean noWorkoutInput(ComboBox exercisesC, TextField durationT, ComboBox intensityC) {
        if (exercisesC.getValue() == null || durationT.getText().isEmpty() || intensityC.getValue() == null) {
            // Display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Please fill in all workout information fields.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    //layout for a log book where a table is displayed with all the inputed workouts. 
    private VBox LogBookLayout() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        
        //preventing table from being editable and removing extra column.
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //creating all columns for table and associated properties
        TableColumn userColumn = new TableColumn("User");
        userColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        
        TableColumn exerciseColumn = new TableColumn("Exercise");
        exerciseColumn.setCellValueFactory(new PropertyValueFactory<>("exerciseType"));
        
        TableColumn durationColumn = new TableColumn("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        
        TableColumn intensityColumn = new TableColumn("Intensity");
        intensityColumn.setCellValueFactory(new PropertyValueFactory<>("intensity"));
        
        TableColumn dateColumn = new TableColumn("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        TableColumn caloriesColumn = new TableColumn("Calories");
        caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        
        //adding all columns to table and adding table to layout.
        table.getColumns().addAll(userColumn, exerciseColumn, durationColumn, intensityColumn, dateColumn, caloriesColumn);// do something with logbook, not logentry
        layout.getChildren().addAll(table);
        table.setItems(entry);
        
        
        return layout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
