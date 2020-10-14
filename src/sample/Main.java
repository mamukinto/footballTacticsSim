package sample;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;


public class Main extends Application {

    Player[] players = new Player[11];
    Player chosenOne = new Player();
    VBox sidebar = new VBox();
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    @Override
    public void start(Stage primaryStage) {
        createPlayers();
        BorderPane root = generateUI();

        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 800,600));

        primaryStage.setTitle("Football Tactics Sandbox");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void createPlayers() {
        Random random = new Random();
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(25.0f, Color.BLACK);
            players[i].setName(String.valueOf(i+1));
            players[i].setCenterX(random.nextInt(500));
            players[i].setCenterY(random.nextInt(500));
            players[i].setCursor(Cursor.MOVE);
            players[i].setOnMousePressed(circleOnMousePressedEventHandler);
            int finalI = i;
            players[i].setOnMouseClicked(click -> {
                chosenOne = players[finalI];
                generateSideBar();
            });
            players[i].setOnMouseDragged(drag -> {
                circleOnMouseDraggedEventHandler.handle(drag);
                System.out.println("X:" + players[finalI].getCenterX() + " Y:" + players[finalI].getCenterY());
                if (players[finalI].getPartnerY() != null) {
                    players[finalI].getPartnerY().setTranslateY(players[finalI].getCenterY() - players[finalI].getPartnerY().getCenterY());
                }
                if  (players[finalI].getPartnerX() != null) {
                    players[finalI].getPartnerX().setTranslateX(players[finalI].getCenterX() - players[finalI].getPartnerX().getCenterX());
                }
            });
    
        }
    }


    private BorderPane generateUI() {
        BorderPane root = new BorderPane();

        //menubar
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("file");
        Menu settingsMenu = new Menu("settings");
        Menu viewMenu = new Menu("view");
        Menu helpMenu = new Menu("help");
        menuBar.getMenus().addAll(fileMenu,settingsMenu,viewMenu,helpMenu);
        root.setTop(menuBar);


        //sidebar
        root.setRight(sidebar);


        //group
        Group group = new Group();
        root.getChildren().addAll(players);
        root.setCenter(group);

        return root;
    }

    private void generateSideBar() {
        sidebar.getChildren().clear();
        sidebar.setPrefWidth(150);
        sidebar.setStyle("-fx-border-width: 0 0 0 4; -fx-border-color: black; -fx-background-color: #9b9b9b");

        Label nameLabel = new Label("label:");
        TextField nameTextField = new TextField(chosenOne.getName());
        sidebar.getChildren().add(nameLabel);
        sidebar.getChildren().add(nameTextField);



        Label colorLabel = new Label("color:");
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue((Color) chosenOne.getFill());
        sidebar.getChildren().add(colorLabel);
        sidebar.getChildren().add(colorPicker);


        Label sizeLabel = new Label("size:");
        Slider slider = new Slider(10, 40.0, chosenOne.getRadius());
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setBlockIncrement(4.0);
        slider.setMajorTickUnit(10.0);
        slider.setMinorTickCount(8);
        slider.setSnapToTicks(true);
        sidebar.getChildren().add(sizeLabel);
        sidebar.getChildren().add(slider);


        Label groupYLabel = new Label("Horizontaly Group with:");
        ChoiceBox<Player> choiceBoxY = new ChoiceBox<>();
        choiceBoxY.setValue(chosenOne.getPartnerY());
        choiceBoxY.getItems().addAll(players);
        sidebar.getChildren().add(groupYLabel);
        sidebar.getChildren().add(choiceBoxY);



        Label groupXLabel = new Label("Vertically Group with:");
        ChoiceBox<Player> choiceBoxX = new ChoiceBox<>();
        choiceBoxX.setValue(chosenOne.getPartnerX());
        choiceBoxX.getItems().addAll(players);
        sidebar.getChildren().add(groupXLabel);
        sidebar.getChildren().add(choiceBoxX);





        Button submitButton = new Button("Submit");
        sidebar.getChildren().add(submitButton);

        submitButton.setOnAction(click -> {
            chosenOne.setName(nameTextField.getText());
            chosenOne.setFill(colorPicker.getValue());
            chosenOne.setStrokeWidth(0);
            chosenOne.setRadius(slider.getValue());
            groupX(chosenOne,choiceBoxX.getValue());
            groupY(chosenOne,choiceBoxY.getValue());
        });


        chosenOne.setStroke(Color.web("#555"));
        chosenOne.setStrokeWidth(4);
    }

    private void groupX(Player p1, Player p2) {
        p1.setPartnerX(p2);
    }

    private void groupY(Player p1, Player p2) {
        p1.setPartnerY(p2);
    }


    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    orgTranslateX = ((Circle)(t.getSource())).getTranslateX();
                    orgTranslateY = ((Circle)(t.getSource())).getTranslateY();
                }
            };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    double offsetX = t.getSceneX() - orgSceneX;
                    double offsetY = t.getSceneY() - orgSceneY;
                    double newTranslateX = orgTranslateX + offsetX;
                    double newTranslateY = orgTranslateY + offsetY;

//                    ((Circle)(t.getSource())).setTranslateX(newTranslateX);
//                    ((Circle)(t.getSource())).setTranslateY(newTranslateY);


                    ((Circle)(t.getSource())).setCenterX(t.getX());
                    ((Circle)(t.getSource())).setCenterY(t.getY());
                }
            };
}
