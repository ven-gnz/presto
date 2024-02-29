package com.example.presto.Editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;


public class Main extends Application {

    // Stage : represents the interface window between app and os
    Stage initialStage;

    //Scene represents a state of the window
    static String filePath;

    static StringBuilder text = new StringBuilder();

    //Cursor initial settings
    static int currentColumn = 0;
    static int currentRow = 0;

    @Override
    public void start(Stage primaryStage) throws IOException {

        initialStage = primaryStage;
        Scene onLaunchScene = onLaunchScene();
        // Are called only once or? Callback environment is a learning goal of project
        initialStage.setScene(onLaunchScene);
        initialStage.setTitle("Presto");
        initialStage.show();
    }

    public static void main(String[] args) throws Exception {

        launch();
    }

    public Scene newFileScene(){

        Pane root = new Pane();
        VBox wrapper = new VBox(600);
        Scene fileEditScene = new Scene(root, 600,600);
        Label l = new Label(text.toString());
        System.out.println("Label "+text.toString());
        VBox mainTextArea = new VBox(400);

        /*

        Obviously only one of these should handle the normal appending and inserting situtation, while the other
        should handle special cases. however, it's more cumbersome as per this method gets the keycodes while
        the latter does not

         */


        fileEditScene.setOnKeyPressed(event -> {
            // This is apparently good train of thought for handling special characters
            System.out.println(event.getCode());
            if(event.getCode() == KeyCode.ENTER){
                System.out.println("New line");
            }
            if(event.getCode() == KeyCode.DELETE){
                System.out.println("Handle delete");
                text.delete(text.length(),text.length()-1);
            }

            // the normal case, maybe handle this more eloquently in the data structure class?
           text.append(event.getCharacter());
            l.setText(text.toString());
        });


        // Handle special keys here?
        root.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                System.out.println(keyEvent.getCharacter());

                if(keyEvent.getCharacter().equals("ENTER")){
                    System.out.println("New line");
                }
                if(keyEvent.getCharacter().equals("SPACE")){
                    System.out.println("Leave some space");
                }

            }
        });

        mainTextArea.getChildren().add(l);
        wrapper.getChildren().add(mainTextArea);
        root.getChildren().add(wrapper);

        return fileEditScene;
    }

    public Scene onLaunchScene(){

        final FileChooser fileChooser = new FileChooser();
        Pane root = new Pane();
        VBox wrapper = new VBox(20);
        Label label = new Label("Welcome to Presto");
        Button newFileButton = new Button("New File");
        Button openFileButton = new Button("Open File");
        openFileButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        File f = fileChooser.showOpenDialog(initialStage);
                        if(f != null){
                            filePath = f.getPath();
                            System.out.println("Opened file "+filePath);
                        }
                    }
                });
        newFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // initialize newFileScene
                filePath = null;
                initialStage.setScene(newFileScene());
                initialStage.setTitle("Presto - New file");
            }
        });
        wrapper.getChildren().addAll(label,openFileButton,newFileButton);
        root.getChildren().add(wrapper);
        return new Scene(root,250,250);
    }

}