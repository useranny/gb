package Client.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setMaxWidth(480);
        primaryStage.setMinWidth(480);
        primaryStage.setWidth(480);
        primaryStage.setHeight(460);
        primaryStage.setMaxHeight(460);
        primaryStage.setMinHeight(460);

        primaryStage.setScene(new Scene(root, 480, 460));
        primaryStage.getIcons().add(new Image("Client/Styles/3.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
