package rwt.fview

import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import java.net.URL
import java.text.NumberFormat
import java.util.*

/**
 * Created by richard todd on 10/10/2016.
 */
internal class MainWinCtl : Initializable {
    private val blockNo : IntegerProperty = SimpleIntegerProperty(0)

    @FXML private var blockNbr : TextField? = null
    @FXML private var blockSizeLevel : ComboBox<SizeLevel>? = null

    override fun initialize(location: URL?, resources: ResourceBundle?) {
         blockNbr!!.textProperty().bindBidirectional(blockNo, NumberFormat.getIntegerInstance())
         with(blockSizeLevel!!) {
             items.setAll(*SizeLevel.values())
             value = SizeLevel.KB
         }
    }

    @FXML private fun upBlock(ae: ActionEvent) = blockNo.set(blockNo.get() + 1)
    @FXML private fun dnBlock(ae: ActionEvent) = blockNo.set(Math.max(blockNo.get() - 1, 0))
}
