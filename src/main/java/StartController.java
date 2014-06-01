import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StartController {

    @FXML
    Button closeButton;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    void initialize() {

    }

    @FXML
    public void close() {
        System.exit(0);
    }
}
