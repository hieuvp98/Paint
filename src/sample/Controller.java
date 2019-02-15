package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Controller implements Initializable {
    private double scaleX;
    private double scaleY;
    private double scrollH;
    private double scrollV;
    private Color choosingColor;
    private int currentSize = 1;
    private Button choosingButton = null;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Button color1;
    @FXML
    public Pane brushesPane;
    @FXML
    public Button btnBrushes;
    @FXML
    public Button btnPen;
    @FXML
    public Label txtPosition;
    @FXML
    public Label txtImageSize;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public MenuButton menuSize;
    @FXML
    public MenuItem size1;
    @FXML
    public MenuItem size2;
    @FXML
    public MenuItem size3;
    @FXML
    public MenuItem size4;
    private Pane drawPane;
    @FXML
    public Slider sliderZoom;

    public void clickBrushes(ActionEvent event) {
        if (!brushesPane.isVisible())
            brushesPane.setVisible(true);
        else {
            brushesPane.setVisible(false);
        }
    }

    public void clickPen(ActionEvent event) {

    }

    public void getPositon(MouseEvent event) {

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
    public void clickNew(ActionEvent event){
            drawPane.getChildren().clear();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scaleX = mainPane.getScaleX();
        scaleY = mainPane.getScaleY();
        scrollH = scrollPane.getHmax();
        scrollV = scrollPane.getVmax();
        choosingColor = new Color(0,0,0,0);
        colorPicker.setValue(Color.BLACK);
        // menu item Size
        size1.setOnAction(event -> currentSize = 1);
        size2.setOnAction(event -> currentSize = 2);
        size3.setOnAction(event -> currentSize = 3);
        size4.setOnAction(event -> currentSize = 5);
        colorPicker.setOnAction(event -> {
            color1.setBackground(new Background(new BackgroundFill(colorPicker.getValue(), CornerRadii.EMPTY, Insets.EMPTY)));
            choosingColor = colorPicker.getValue();
        });
        mainPane.localToScene(0, 0);
        drawPane = new Pane();
        drawPane.setPrefSize(1920,1080);
        scrollPane.setContent(drawPane);
        drawPane.setOnMouseMoved(event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();

            txtPosition.setText(x + ", " + y + "px");
            if (x == 0 || y == 0) {
                txtPosition.setText("");
            }
        });
        sliderZoom.valueProperty().addListener((observable, oldValue, newValue) -> {
            mainPane.setScaleX(scaleX * (1 + newValue.doubleValue() / 100));
            mainPane.setScaleY(scaleY * (1 + newValue.doubleValue() / 100));
            if (newValue.doubleValue() > 0) {
                scrollPane.setHmax(scrollH + newValue.doubleValue());
                scrollPane.setVmax(scrollV + newValue.doubleValue());
            } else {
                scrollPane.setHmax(scrollH);
                scrollPane.setVmax(scrollV);
            }
            mainPane.localToScene(0, 0);
            mainPane.setLayoutY(0);
            mainPane.setLayoutY(0);
        });
        btnPen.setOnMouseClicked(event -> {
            if (!btnPen.isCancelButton()) {
                Image image = new Image("images/pencil.png");
                ImageCursor cursor = new ImageCursor(image);
                scrollPane.setCursor(Cursor.HAND);
                btnPen.setCancelButton(true);
                String css = "-fx-border-color: transparent, blue;";
                btnPen.getStyleClass().add(css);
                drawPane.setOnMousePressed(event1 -> {
                    System.out.println("begin");
                    AtomicReference<Double> startX = new AtomicReference<>(event1.getX());
                    AtomicReference<Double> startY = new AtomicReference<>(event1.getY());
                    System.out.println(startX.get());
                    System.out.println(startY.get());
                    drawPane.setOnMouseDragged(event2 -> {
                        double endX = event2.getX();
                        double endY = event2.getY();
                        System.out.println(endX);
                        System.out.println(endY);
                        Line line = new Line(startX.get(),startY.get(),endX,endY);
                        line.setStrokeWidth(currentSize);
                        line.setStroke(colorPicker.getValue());
                        drawPane.getChildren().add(line);
                        startX.set(endX);
                        startY.set(endY);
                    });

                });

            }
            else {
                scrollPane.setCursor(Cursor.DEFAULT);
                btnPen.setCancelButton(false);
                btnPen.getStyleClass().remove(2);
                drawPane.setOnMousePressed(null);
                drawPane.setOnMouseDragged(null);
            }
        });
    }
}
