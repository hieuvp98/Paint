package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private  double scaleX;
    private  double scaleY;
    private  double scrollH;
    private double scrollV;
    @FXML
    public Pane brushesPane;
    @FXML
    public Button btnBrushes;
    @FXML
    public Label txtPosition;
    @FXML
    public Label txtImageSize;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Slider sliderZoom;

    public void clickBrushes(ActionEvent event) {
        if (!brushesPane.isVisible())
            brushesPane.setVisible(true);
        else {
            brushesPane.setVisible(false);
        }
    }

    public void getPositon(MouseEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        txtPosition.setText(x + ", " + y + "px");
        if (x == 0 || y == 0) {
            txtPosition.setText("");
        }
    }

    public void exitMainPane() {
        txtPosition.setText("");
    }

    public void openFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
//            BackgroundImage backgroundImage = new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//            mainPane.setBackground(new Background(backgroundImage));

            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            mainPane.getChildren().add(imageView);
            imageView.setX(0);
            imageView.setY(0);
            txtImageSize.setText((int) image.getWidth() + ", " + (int) image.getHeight() + "px");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scaleX = mainPane.getScaleX();
        scaleY = mainPane.getScaleY();
        scrollH = scrollPane.getHmax();
        scrollV = scrollPane.getVmax();
        mainPane.localToScene(0,0);
        sliderZoom.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainPane.setScaleX(scaleX * (1 + newValue.doubleValue() / 100));
            mainPane.setScaleY(scaleY * (1 + newValue.doubleValue() / 100));
            if (newValue.doubleValue() > 0) {
                scrollPane.setHmax(scrollH + newValue.doubleValue());
                scrollPane.setVmax(scrollV + newValue.doubleValue());
            }
            else {
                scrollPane.setHmax(scrollH);
                scrollPane.setVmax(scrollV);
            }
            mainPane.localToScene(0,0);
            mainPane.setLayoutY(0);
            mainPane.setLayoutY(0);
        });
    }
}
