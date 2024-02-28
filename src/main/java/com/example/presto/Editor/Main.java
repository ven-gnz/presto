package com.example.presto.Editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    // Stage : represents the interface window between app and os
    Stage initialStage;
    static int currentColumn = 0;
    static int currentRow = 0;
    //Scene represents a state of the window
    static String filePath;

    static boolean eventCompleted = false;

    static StringBuilder text = new StringBuilder();

    @Override
    public void start(Stage primaryStage) throws IOException {

        KeyboardHandler kh = new KeyboardHandler();

        initialStage = primaryStage;
        final FileChooser fileChooser = new FileChooser();

        Label label = new Label("Welcome to Presto");
        Button newFile = new Button("New File");
        Button open = new Button("Open File");
        open.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        File f = fileChooser.showOpenDialog(primaryStage);
                        if(f != null){
                            filePath = f.getPath();
                            System.out.println("Opened file"+filePath);
                            eventCompleted = true;
                        }
                    }
                }
        );

        // TODO check if this funcionality can be refactored into it's own class
        newFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                    filePath = null;
                    eventCompleted = true;
                    System.out.println("Opening new file");
                    primaryStage.setHeight(400);
                    primaryStage.setWidth(400);
                    // Parameterize from here? Make a different scene in another file maybe, work in progress
                    VBox newTextArea = new VBox(400);
                    Label l = new Label(text.toString());

                    initialStage.addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    // Set cursor position, cursor has to point to anything first
                                    // System.out.println("Clicked text area");
                                }
                            });
                    initialStage.addEventFilter(KeyEvent.KEY_TYPED,
                            new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent keyEvent) {


                                    text.append(keyEvent.getCharacter());
                                    l.setText(text.toString());
                                }
                            });

                    newTextArea.getChildren().add(l);
                    Scene newFileScene = new Scene(newTextArea,400,400);
                    initialStage.setScene(newFileScene);
                    initialStage.setTitle("Presto - New file");

            }
        });

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label,open,newFile);
        Scene initialScene = new Scene(layout1, 200,200);

        // Are called only once or? Callback environment is a learning goal of project
        initialStage.setScene(initialScene);
        initialStage.setTitle("Presto");
        initialStage.show();

    }

    public static void main(String[] args) throws Exception {

        launch();
    }


}