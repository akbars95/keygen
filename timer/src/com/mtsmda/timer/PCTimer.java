package com.mtsmda.timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by dminzat on 9/12/2016.
 */
public class PCTimer extends Application {

    private static final String RUN_TEXT = "Run";
    static final String ICON_NAME = "icon.png";

    private VBox vBoxContainer = new VBox();

    private Integer choseOperation;

    private String[] types = {"", "Time minutes", "Date"};
    private Integer choseType;

    private ChoiceBox choiceBoxType = new ChoiceBox();
    private ChoiceBox choiceBoxOperation = new ChoiceBox();

    private DatePicker datePicker;
    private Spinner spinnerHour;
    private Spinner spinnerMinute;
    private Spinner spinnerSecond;

    private CheckBox checkBoxForseOperation = new CheckBox("Forse");

    private Button buttonOk = new Button(RUN_TEXT);
    private Button buttonCancel = new Button("Cancel");

    private HBox hBoxInfo = new HBox();
    private Label labelInfo = new Label();
    private Label labelInfoLastTime = new Label();

    private PCOperations pcOperations = new PCOperations();
    private boolean runOperation = false;

    /*private Timer timer = new Timer();*/
    private TimerTaskImpl timerTask = new TimerTaskImpl();
    private Timeline timeline = null;

    private LocalDateTime localDateTimeKey;
    private DateTimeFormatter normalFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");

    @Override
    public void start(Stage primaryStage) throws Exception {
        DBHelper.init();
        mainWindowSettings(primaryStage);
        vBoxContainer.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(vBoxContainer, 200, 100);
        primaryStage.setScene(scene);
        String javaVersion = System.getProperty("java.version");
        if (javaVersion == null || (javaVersion != null && Double.parseDouble(javaVersion.substring(0, 3)) < 1.8)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information Dialog");
            DialogPane dialogPane = new DialogPane();
            dialogPane.setHeaderText("Java version not valid!");
            HBox hBox = new HBox(new Label("Error! Current java version is " + javaVersion + ", but need 1.8 and latest!"));
            Hyperlink hyperlinkJava8 = new Hyperlink("www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html");
            hyperlinkJava8.setOnAction(event -> {
                getHostServices().showDocument(hyperlinkJava8.getText());
                System.exit(0);
            });
            VBox vBoxMainOnAlert = new VBox(hBox, new HBox(hyperlinkJava8));
            dialogPane.setContent(vBoxMainOnAlert);
            dialogPane.getButtonTypes().addAll(ButtonType.OK);

            alert.setDialogPane(dialogPane);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    System.exit(0);
                }
            });
            return;
        }

        main();

    }

    private void main() {
        try {
            localDateTimeKey = KeyHelper.checkIsValidKey(DBHelper.getLastKey());
            if (null == localDateTimeKey) {
                addKeyUI();
                return;
            }
        } catch (RuntimeException e) {
            addKeyUI();
            return;
        }

        operationLine();
        typeLine();
        infoLine();
        commandButtonLine();
    }

    private void addKeyUI() {
        Label label = new Label("Write key");
        Label labelForCountSymbols = new Label("\t\tCount: ");
        Label labelCountSymbols = new Label("0\t\t");
        labelCountSymbols.setTextFill(Color.RED);
        TextField textField = new TextField();
        Button button = new Button("Add key");
        button.setDisable(true);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            labelCountSymbols.setText(observable.getValue().length() + "\t\t");
            labelCountSymbols.setTextFill(observable.getValue().length() == 99 ? Color.GREEN : Color.RED);
            button.setDisable(observable.getValue().length() != 99);
        });
        button.setOnAction(event -> {
            try {
                String key = textField.getText();
                KeyHelper.checkKey(key);
                try (Connection connection = DBHelper.getConnection();) {
                    String queryInsert = "INSERT INTO keys (genkey) VALUES (?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryInsert);
                    preparedStatement.setString(1, key);
                    int i = preparedStatement.executeUpdate();
                    if (i > 0) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Dialog");
                        alert.setHeaderText("Key add");
                        alert.setContentText("Success added key!");
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get() == ButtonType.OK || buttonType.get() == ButtonType.CLOSE) {
                            vBoxContainer.getChildren().clear();
                            main();
                        }
                    }
                } /*finally {
                    *//*try {
                        DBHelper.closeConnection();
                    } catch (SQLException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Close connection");
                        alert.setContentText("CLOSE CONNECTION IS UNSUCCESSFUL!" + e.getMessage());
                        Optional<ButtonType> buttonType = alert.showAndWait();
                    }*//*
                }*/
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Key add");
                alert.setContentText("Error added key!" + e.getMessage());
                Optional<ButtonType> buttonType = alert.showAndWait();
                /*buttonType.ifPresent(buttonType1 -> {
                    if (buttonType.get() == ButtonType.OK || buttonType.get() == ButtonType.CANCEL) {
                        textField.setText("");
                    }
                });*/
                textField.setText("");
            }
        });
        Label labelMessage = new Label(((null != localDateTimeKey) ? "Your licence has expired: "
                + localDateTimeKey.format(normalFormatter) : "You haven't licence!Please generate key!Key should be 99 symbols!"));
        HBox hBoxMessage = new HBox(labelMessage);
        HBox hBoxAddKey = new HBox(label, textField, labelForCountSymbols, labelCountSymbols, button);
        VBox vBox = new VBox(hBoxMessage, hBoxAddKey);
        vBoxContainer.getChildren().addAll(vBox);
    }

    private void processEvent() {
        runOperation = !runOperation;
        buttonOk.setDisable(runOperation);
        buttonCancel.setDisable(!runOperation);
        choiceBoxOperation.setDisable(runOperation);
        choiceBoxType.setDisable(runOperation);
        datePicker.setDisable(runOperation);
        spinnerHour.setDisable(runOperation);
        spinnerMinute.setDisable(runOperation);
        spinnerSecond.setDisable(runOperation);
        checkBoxForseOperation.setDisable(runOperation);
        hBoxInfo.setVisible(runOperation);
//        timerTask.setRun(runOperation);
    }

    private void commandButtonLine() {
        buttonOk.setDisable(true);
        HBox hBoxButtons = new HBox(buttonCancel, buttonOk);
        buttonCancel.setDisable(!runOperation);
        buttonCancel.setOnAction(event -> {
            processEvent();
            try {
                pcOperations.cancel();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Cancel " + PCOperations.operations[choseOperation]);
                alert.setContentText("Success!");
                alert.showAndWait();
                timeline.stop();
                timeline = null;
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Cancel " + PCOperations.operations[choseOperation]);
                alert.setContentText("Error! " + e.getMessage());
                alert.showAndWait();
            }
        });
        buttonOk.setOnAction(event -> {
            try {
                long seconds = 0L;
                LocalDateTime localDateTime = LocalDateTime.now();
                LocalDate localDate = LocalDate.now();
                switch (choseType) {
                    case 2: {
                        localDate = datePicker.getValue();
                        if (localDate.isBefore(LocalDate.now())) {
                            throw new RuntimeException("Date should be today or latest!");
                        }
                    }
                    case 1: {
                        int hour = Integer.parseInt(spinnerHour.getValueFactory().getValue().toString());
                        int minute = Integer.parseInt(spinnerMinute.getValueFactory().getValue().toString());
                        int second = Integer.parseInt(spinnerSecond.getValueFactory().getValue().toString());

                        seconds += (hour * 3600) + (minute * 60) + second;

                        localDateTime = LocalDateTime.of(localDate, LocalTime.now()).plusSeconds(seconds);
                        seconds = Duration.between(LocalDateTime.now(), localDateTime).getSeconds();
                    }
                    break;
                }
                if (seconds <= 0) {
                    throw new RuntimeException("Should be more than 0 seconds!");
                }
                pcOperations.command(choseOperation, checkBoxForseOperation.isSelected(), seconds);
                String infoTextIn = PCOperations.operations[choseOperation] + " on " + getLocalDateTime(localDateTime);

                timerTask.setSecond(seconds);
                timerTask.setRun(true);
                timerTask.setLabel(this.labelInfoLastTime);
                timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1),
                        ae -> timerTask.run()));
                timeline.setCycleCount(new Long(seconds).intValue());
                timeline.play();
//                this.timer.schedule(this.timerTask, 0, 1000);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Operation name is " + PCOperations.operations[choseOperation]);
                alert.setContentText("Success!\t" + infoTextIn);
                alert.showAndWait();
                processEvent();
                labelInfo.setText(infoTextIn);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Not working");
                alert.setContentText("Error! " + e.getMessage());
                alert.showAndWait();
            }
        });
        vBoxContainer.getChildren().addAll(hBoxButtons);
    }

    private void infoLine() {
        hBoxInfo.getChildren().addAll(labelInfo, labelInfoLastTime);
        hBoxInfo.setSpacing(25.0);
        labelInfoLastTime.setAlignment(Pos.CENTER_RIGHT);
        labelInfoLastTime.setFont(new Font(19));
        labelInfoLastTime.setTextAlignment(TextAlignment.RIGHT);
        labelInfoLastTime.setTextFill(Color.GREEN);
        hBoxInfo.setVisible(runOperation);
        vBoxContainer.getChildren().addAll(hBoxInfo);
    }

    private void typeLine() {
        HBox hBoxSetTime = null;
        final HBox hBoxSetDate = new HBox();
        hBoxSetDate.setVisible(false);
        HBox hBoxTimerType = new HBox();
        Label labelType = new Label("Type");
        choiceBoxType.getItems().addAll(types);
        hBoxTimerType.getChildren().addAll(labelType, choiceBoxType);
        choiceBoxType.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            choseType = newValue.intValue();
            switch (choseType) {
                case 1: {
                    hBoxSetDate.setVisible(false);
                }
                break;
                case 2: {
                    hBoxSetDate.setVisible(true);
                }
                break;
            }
            buttonOk.setDisable(choseType == null || choseType == 0 || choseOperation == null || choseOperation == 0);
        });
        vBoxContainer.getChildren().addAll(hBoxTimerType);

        Label labelDate = new Label("Date");
        datePicker = new DatePicker(LocalDate.now());


        hBoxSetDate.getChildren().addAll(labelDate, datePicker);

        Label labelHour = new Label("Hours");
        spinnerHour = new Spinner(0, 23, 0, 1);
        spinnerHour.setPrefWidth(75);
        Label labelMinute = new Label("Minutes");
        spinnerMinute = new Spinner(0, 59, 0, 1);
        spinnerMinute.setPrefWidth(75);
        Label labelSecond = new Label("Seconds");
        spinnerSecond = new Spinner(0, 59, 0, 1);
        spinnerSecond.setPrefWidth(75);
        hBoxSetTime = new HBox(labelHour, spinnerHour, labelMinute, spinnerMinute, labelSecond, spinnerSecond);

        vBoxContainer.getChildren().addAll(hBoxSetDate, hBoxSetTime, new HBox(checkBoxForseOperation));

    }

    private void operationLine() {
        Label labelOperation = new Label("Operation");
        choiceBoxOperation.getItems().addAll(PCOperations.operations);
        choiceBoxOperation.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            choseOperation = newValue.intValue();
            buttonOk.setText(RUN_TEXT + " " + PCOperations.operations[choseOperation]);
            String osName = System.getProperty("os.name");
            if (choseOperation == 4 && !(osName != null && osName.startsWith("Windows") && (osName.endsWith("8.1") || osName.endsWith("8") || osName.endsWith("7")))) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("OS unsupported operation " + PCOperations.operations[choseOperation]);
                alert.setContentText("Error! " + (osName != null ? osName : "") + "\tnot supported " + PCOperations.operations[choseOperation]);
                alert.showAndWait();
            }
            buttonOk.setDisable(choseType == null || choseType == 0 || choseOperation == null || choseOperation == 0);
        });
        Label keyExpiredTime = new Label("Your key is expired after - " + localDateTimeKey.format(normalFormatter));
        HBox hBoxOperationChoose = new HBox(labelOperation, choiceBoxOperation, keyExpiredTime);
        vBoxContainer.getChildren().addAll(hBoxOperationChoose);
    }

    private void mainWindowSettings(Stage primaryStage) {
        primaryStage.setTitle("Timer");
        primaryStage.setWidth(500);
        primaryStage.setHeight(250);
        primaryStage.centerOnScreen();
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
    }

    private static String getLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public static void main(String[] args) {
        Application.launch();
    }

}

class KeyHelper {

    private static LocalDateTime validKey;

    public static void checkKey(String key) {
        if (key == null) {
            validKey = null;
            throw new RuntimeException("No key(s)!");
        } else {
            if (key.isEmpty() || key.trim().isEmpty()) {
                validKey = null;
                throw new RuntimeException("Key is empty!");
            }
            if (key.length() != 99) {
                validKey = null;
                throw new RuntimeException("Key format isn't valid!");
            }
            try {
                Integer number = Integer.parseInt(key.substring(0, 2));
                String substring = key.substring(number, number + 15);
                validKey = LocalDateTime.parse(substring, DateTimeFormatter.ofPattern("HHmmss|ddMMyyyy"));
                if ((null != validKey && validKey.isBefore(LocalDateTime.now()))) {
                    throw new RuntimeException("This key is expired! Put new key please!");
                }
            } catch (RuntimeException e) {
                String defaultMessage = "Key format is incorrect!";
                if (e.getClass().getCanonicalName().equals(RuntimeException.class.getCanonicalName())) {
                    defaultMessage = e.getMessage();
                }
                throw new RuntimeException(defaultMessage, e);
            }
        }
    }

    public static LocalDateTime checkIsValidKey(List<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return null;
        }
        keys.forEach(key -> {
            checkKey(key);
        });
        return validKey;
    }

    public static LocalDateTime checkIsValidKey(String key) {
        List<String> keys = new ArrayList<String>();
        keys.add(key);
        return checkIsValidKey(keys);
    }

    public static LocalDateTime getValidKey() {
        return validKey;
    }
}

class DBHelper {

    private static String error;

    static {
        try {
            Class.forName("org.h2.Driver");
            init();
        } catch (ClassNotFoundException e) {
            error = e.toString();
        }
    }

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager
                    .getConnection("jdbc:h2:" + H2DB.class.getResource(PCTimer.ICON_NAME).getPath()
                            .replace(PCTimer.ICON_NAME, "")
                            + "simpleDataBases", "test", "");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public static List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();) {
            ResultSet rs = statement.executeQuery("SELECT * FROM keys ORDER  BY key_id DESC");
            while (rs.next()) {
                System.out.println(rs.getInt("key_id"));
                keys.add(rs.getString("genkey"));
            }
        } catch (Exception e) {
            return keys;
        }
        return keys;
    }

    public static String getLastKey() {
        try (Statement statement = getConnection().createStatement();) {
            ResultSet rs = statement.executeQuery("SELECT * FROM  keys where key_id = (SELECT max(key_id) FROM keys)");
            while (rs.next()) {
                System.out.println(rs.getInt("key_id"));
                return rs.getString("genkey");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void init() {
        try (Statement stmt = getConnection().createStatement();) {
//            System.out.println("drop table - " + stmt.executeUpdate("DROP TABLE keys"));
            System.out.println("create table - " + stmt.executeUpdate("CREATE TABLE if not EXISTS keys (key_id bigint auto_increment primary key, genkey varchar(99) )"));
            /*stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Claudio' )");
            stmt.executeUpdate("INSERT INTO table1 ( user ) VALUES ( 'Bernasconi' )");
*/
            /*ResultSet rs = stmt.executeQuery("SELECT * FROM table1");
            while (rs.next()) {
                String name = rs.getString("user");
                System.out.println(name);
            }*/
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

class TimerTaskImpl /*extends TimerTask*/ {

    private long second;
    private Boolean run = false;
    private Label label;

    public TimerTaskImpl() {

    }

    public TimerTaskImpl(long second, boolean run) {
        this.second = second;
        this.run = run;
    }

    public TimerTaskImpl(long second, Boolean run, Label label) {
        this.second = second;
        this.run = run;
        this.label = label;
    }

    /*@Override*/
    public void run() {
        if (this.second > 0 && run) {
            this.second--;
            System.out.println(second);
            if (this.label != null) {
                if (second < 60) {
                    label.setTextFill(Color.RED);
//                    label.setStyle(" -fx-text-fill: red;");
                }
                label.setText("Last: " + second);
            }
        }
    }

    public long getSecond() {
        return second;
    }

    public void setSecond(long second) {
        this.second = second;
    }

    public Boolean getRun() {
        return run;
    }

    public void setRun(Boolean run) {
        this.run = run;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}