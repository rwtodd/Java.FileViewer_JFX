package rwt.fview

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 * Getting the program started.
 * Created by richard todd on 10/10/2016.
 */
fun main(args: Array<String>) {
      Application.launch(Main::class.java, *args)
}

class Main : Application() {
    override fun start(primaryStage: Stage?) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/fxml/MainWin.fxml"))
        with(primaryStage!!) {
            title = "File Viewer"
            scene = Scene(root)
            sizeToScene()
            show()
        }
    }
}