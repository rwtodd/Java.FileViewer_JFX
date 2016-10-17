package rwt.fview;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.File;
import java.text.NumberFormat;

/**
 * Created by richard todd on 10/10/2016.
 */
public class MainWinCtl {
    private final LongProperty blockNo;
    private FileData srcData;

    @FXML private TextField blockNbr;
    @FXML private TextField blockSize;
    @FXML private ComboBox<SizeLevel> blockSizeLevel;
    @FXML private TextArea viewZone;

    public MainWinCtl() {
         blockNo = new SimpleLongProperty(0);
    }

    public void initialize() {
         blockNbr.textProperty().bindBidirectional(blockNo, NumberFormat.getIntegerInstance());
         blockSizeLevel.getItems().setAll(SizeLevel.values());
         blockSizeLevel.setValue(SizeLevel.KB);

        viewZone.textProperty().bind(new StringBinding() {
            {
                bind(blockNo);
            }

            @Override
            protected String computeValue() {
                String answer;
                try {
                    if (srcData == null) {
                        answer = "Lorem ipsum " + blockNo.getValue().toString();
                    } else {
                        answer = new String(srcData.fetchData(blockNo.get()));
                    }
                } catch (Exception e) {
                    answer = "Exception " + e.toString();
                }
                return answer;
            }

        });
    }

    @FXML private void upBlock(ActionEvent ae) { blockNo.set(blockNo.get() + 1); }
    @FXML private void dnBlock(ActionEvent ae) { blockNo.set(Math.max(blockNo.get() - 1, 0)); }
    @FXML private void selectFile(ActionEvent ae) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Select a File");
        File ans = fc.showOpenDialog(blockNbr.getScene().getWindow());
        if(ans != null) {
            try {
              newFile(ans);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void newFile(File f) throws java.io.IOException {
        if(srcData != null) {
            srcData.close();
            srcData = null;
        }
        blockNo.set(-1);
        srcData = new FileData(f,
                               Integer.valueOf(blockSize.getText()) * blockSizeLevel.getValue().getMultiplier());       
        blockNo.set(0);
    }
}
