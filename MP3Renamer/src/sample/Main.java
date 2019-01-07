package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main extends Application  {
    public static void main(String[] args) {
        launch(args);
    }

    TextField txtNewSongName;
    Button button;
    File[] filesArray;
    Label lblSongNameData;
    public int mIfCounter = 0;
    String buf = "";
    String dirPath;
    File folder;
    String fileSeparator = System.getProperty("file.separator");

    //renaming method
    private String renameIt() throws IOException, InterruptedException {
        lblSongNameData.setText(buf);
        File one = new File(dirPath + fileSeparator + buf);
        File two = new File(folder.toString() + fileSeparator + txtNewSongName.getText() + ".mp3" );
        Files.copy(one.toPath(), two.toPath());
        mIfCounter++;
        giveMeName();
        return null;
    }


    private String giveMeName()  throws InterruptedException, IOException {

        if (mIfCounter < filesArray.length ){
            buf = filesArray[mIfCounter ].getName();
            lblSongNameData.setText(buf);
            txtNewSongName.setText(buf);
                if (buf.endsWith(".mp3")) {
                    button.setOnAction( e -> {
                        try {
                            renameIt();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    });
                } else {
                    mIfCounter++;
                    giveMeName();
                }
        } else {
            mIfCounter = 0;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("Job is done");
            alert.setContentText("All .mp3 are renamed");
            alert.showAndWait();
            System.exit(0);
        }
            return  null;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        String myJarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        dirPath = new File(myJarPath).getParent();
        String folderRenamed = fileSeparator + "RenamedMP3"; //Create new folder for renamed files
        folder = new File(dirPath + folderRenamed);
        folder.mkdir();
        //Add button
        button = new Button("Change name");
        button.setAlignment(Pos.BOTTOM_RIGHT);
        //Create Actual song  name label
        Label lblSongName = new Label("Actual song name:");
        lblSongName.setMinWidth(100);
        lblSongName.setAlignment(Pos.BOTTOM_RIGHT);
        //Create Actual song DATA label;
        lblSongNameData = new Label("DATA");
        lblSongNameData.setMinWidth(200);
        lblSongNameData.setMaxWidth(400);
        //Create New Song Label Name
        Label lblNewSongName = new Label("Type new name: ");
        lblNewSongName.setMinWidth(100);
        lblNewSongName.setAlignment(Pos.BOTTOM_RIGHT);
        //Create TextField for new name song
        txtNewSongName = new TextField();
        txtNewSongName.setMinWidth(400);
        txtNewSongName.setMaxWidth(400);
        txtNewSongName.setPromptText("Enter the new name of song here.");
        // Create the Actualsong pane
        HBox paneActualSong = new HBox(20, lblSongName, lblSongNameData);
        paneActualSong.setPadding(new Insets(10));
        // Create the New Song Name pane
        HBox paneNewSongName = new HBox(20, lblNewSongName, txtNewSongName);
        paneNewSongName.setPadding(new Insets(10));
        //Test label pane
        HBox paneChangeButton = new HBox(20,button);
        VBox pane = new VBox(10, paneActualSong, paneNewSongName, paneChangeButton);
        //Find the path to the files
        File filesPath = new File(dirPath + fileSeparator);
        filesArray = filesPath.listFiles();
        giveMeName();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MP3Renamer");
        primaryStage.show();
    }
}


