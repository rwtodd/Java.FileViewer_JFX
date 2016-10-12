package rwt.fview

import javafx.beans.binding.StringBinding
import javafx.beans.property.IntegerProperty
import javafx.beans.property.LongProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleLongProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ComboBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import java.io.File
import java.net.URL
import java.text.NumberFormat
import java.util.*

/**
 * Created by richard todd on 10/10/2016.
 */
internal class MainWinCtl : Initializable {
    private val blockNo : LongProperty = SimpleLongProperty(0)
    private var srcData : FileData? = null

    @FXML private var blockNbr : TextField? = null
    @FXML private var blockSize : TextField? = null
    @FXML private var blockSizeLevel : ComboBox<SizeLevel>? = null
    @FXML private var viewZone : TextArea? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
         blockNbr!!.textProperty().bindBidirectional(blockNo, NumberFormat.getIntegerInstance())
         with(blockSizeLevel!!) {
             items.setAll(*SizeLevel.values())
             value = SizeLevel.KB
         }

         viewZone?.textProperty()?.bind(
                 object : StringBinding() {
                     init {
                         bind(blockNo)
                     }

                     override fun computeValue(): String =
                         if(srcData == null) {
                             "Lorem ipsum ${blockNo.get()}"
                         } else {
                             String(srcData!!.fetchData(blockNo.get()))
                         }

                 }
         )
    }

    @FXML private fun upBlock(ae: ActionEvent) = blockNo.set(blockNo.get() + 1)
    @FXML private fun dnBlock(ae: ActionEvent) = blockNo.set(Math.max(blockNo.get() - 1, 0))
    @FXML private fun selectFile(me: ActionEvent) {
        val fc = javafx.stage.FileChooser()
        fc.title = "Select a File"
        val ans : File? = fc.showOpenDialog(blockNbr!!.scene.window)
        if(ans != null) {
           newFile(ans)
        }
    }

    fun newFile(f : File) {
        srcData?.close()
        srcData = null
        blockNo.set(-1)
        srcData = FileData(f,
                           Integer.valueOf(blockSize!!.text) * blockSizeLevel!!.value.multiplier)
        blockNo.set(0)
    }
}
