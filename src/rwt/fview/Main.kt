package rwt.fview

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.File

/**
 * Getting the program started.
 * Created by richard todd on 10/10/2016.
 */
fun main(args: Array<String>) {
      Application.launch(Main::class.java, *args)
}

class Main : Application() {
    override fun start(primaryStage: Stage?) {
        val ldr = FXMLLoader(javaClass.getResource("/fxml/MainWin.fxml"))
        val root = ldr.load<Parent>()
        val rawparams = this.parameters.raw
        if(rawparams.size > 0) {
            ldr.getController<MainWinCtl>().newFile(File(rawparams[0]))
        }
        with(primaryStage!!) {
            title = "File Viewer"
            scene = Scene(root)
            sizeToScene()
            show()
        }
    }
}