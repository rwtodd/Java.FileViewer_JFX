package rwt.fview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;


public class Main extends Application {

 /**
  * Getting the program started.
  * Created by richard todd on 10/10/2016.
  */
 public static void main(String[] args) {
      launch(args);
 }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("/fxml/MainWin.fxml"));
            Parent root = ldr.load();
            java.util.List<String> rawparams = getParameters().getRaw();
            if (rawparams.size() > 0) {
                ((MainWinCtl) ldr.getController()).newFile(new File(rawparams.get(0)));
            }
            stage.setTitle("File Viewer");
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }
}
