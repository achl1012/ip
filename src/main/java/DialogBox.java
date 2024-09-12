import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with the specified text and image.
     * @param text The text to be displayed in the dialog box.
     * @param img The image to be displayed in the dialog box.
     */
    private DialogBox(String text, Image img) {
        assert text != null : "Text for DialogBox should not be null";
        assert img != null : "Image for Dialog Box should not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert dialog != null : "Label 'dialog' should be initialised in the FXML file";
        assert displayPicture != null : "ImageView 'displayPicture' should be initialised in the FXML file";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user with the specified text and image.
     *
     * @param text The text to be displayed in the user's dialog box.
     * @param img The image to be displayed in the user's dialog box.
     * @return A DialogBox instance configured for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "User dialog text should not be null";
        assert img != null : "User image should not be null";
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box for Charlotte with the specified text and image.
     *
     * @param text The text to be displayed in Charlotte’s dialog box.
     * @param img The image to be displayed in Charlotte’s dialog box.
     * @return A DialogBox instance configured for Charlotte.
     */
    public static DialogBox getCharlotteDialog(String text, Image img) {
        assert text != null : "Charlotte dialog text should not be null";
        assert img != null : "Charlotte image should not be null";
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}


