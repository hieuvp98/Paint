package sample;

import entities.ColorButton;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Controller implements Initializable {
    private Color choosingColor;
    private int currentSize = 1;
    private Button choosingButton = null;
    private RadioButton buttonSin, buttonCos;
    // ve do thi
    private Dialog dialog;
    @FXML
    public TextField edtStartX;
    @FXML
    public TextField edtEndX;
    @FXML
    public TextField edtDoChia;
    // color
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Button color1;
    private ColorButton colorbutton1;
    @FXML
    public Button color2;
    private ColorButton colorButton2;
    @FXML
    public Pane brushesPane;
    @FXML
    public Pane chartPane;
    @FXML
    public Button btnBrushes;
    @FXML
    public Button btnPen;
    @FXML
    public Button btnEraser;
    @FXML
    public Label txtPosition;
    @FXML
    public Label txtImageSize;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane mainPane;
    @FXML
    public GridPane gridColor;
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
        try {
            drawPane.getChildren().add(brushesPane);
        } catch (Exception ignored) {
        } finally {

            if (!brushesPane.isVisible())
                brushesPane.setVisible(true);
            else {
                brushesPane.setVisible(false);
            }
        }
    }

    public void clickEraser(ActionEvent event) {
        clickButton(btnEraser);
        Image image = new Image("images/eraser2.png");
        ImageCursor cursor = new ImageCursor(image);
        if (!choosingButton.isCancelButton()) {
//                Image image = new Image("images/pen.png");
//                ImageCursor cursor = new ImageCursor(image);
            scrollPane.setCursor(cursor);
            choosingButton.setCancelButton(true);
            drawPane.setOnMousePressed(event1 -> {
                double bonus = 16;
                AtomicReference<Double> startX = new AtomicReference<>(event1.getX() + bonus);
                AtomicReference<Double> startY = new AtomicReference<>(event1.getY() + bonus);

                drawPane.setOnMouseDragged(event2 -> {
                    double endX = event2.getX() + bonus;
                    double endY = event2.getY() + bonus;
                    Line line = new Line(startX.get(), startY.get(), endX, endY);
                    line.setStrokeWidth(currentSize * 8);
                    line.setStroke(Color.WHITE);
                    drawPane.getChildren().add(line);
                    startX.set(endX);
                    startY.set(endY);
                });

            });

        } else {
            cancelDraw();
        }
    }

    public void clickColor1(ActionEvent event) {
        color1.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        color2.setBorder(null);
        choosingColor = colorbutton1.getColor();
    }

    public void clickColor2(ActionEvent event) {
        color2.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
        color1.setBorder(null);
        choosingColor = colorButton2.getColor();
    }

    public void pickColor(ActionEvent event) {
        colorButton2.setColor(choosingColor);
        choosingColor = colorPicker.getValue();
        colorbutton1.setColor(choosingColor);
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
            drawPane.getChildren().add(imageView);
            imageView.setX(0);
            imageView.setY(0);
            txtImageSize.setText((int) image.getWidth() + ", " + (int) image.getHeight() + "px");
        }
    }

    public void clickNew(ActionEvent event) {
        drawPane.getChildren().clear();
    }

    public void veDoThi(ActionEvent event) {
        dialog = new Dialog();
        dialog.setWidth(250);
        dialog.setOnCloseRequest(event1 -> {
            dialog.setResult(true);
            dialog.close();
        });
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setContent(chartPane);
        chartPane.setVisible(true);
        // Group
        ToggleGroup group = new ToggleGroup();

// Radio 1: sin(x)
        buttonSin = new RadioButton("sin(x)");
        buttonSin.setLayoutX(98);
        buttonSin.setLayoutY(42);
        buttonSin.setToggleGroup(group);
        buttonSin.setSelected(true);

// Radio 2: cos(x)
        buttonCos = new RadioButton("cos(x)");
        buttonCos.setLayoutX(185);
        buttonCos.setLayoutY(42);
        buttonCos.setToggleGroup(group);
        chartPane.getChildren().add(buttonSin);
        chartPane.getChildren().add(buttonCos);
        dialog.show();
    }

    public void clickBtnVe(ActionEvent event) {
        drawPane.getChildren().clear();
        double value = 40;
        double startX = -2;
        double endX = 2;
        try {
            value = Double.parseDouble(edtDoChia.getText());
            startX = Double.parseDouble(edtStartX.getText());
            endX = Double.parseDouble(edtEndX.getText());
        } catch (Exception ignored) {
        }
        Line lineY = new Line(scrollPane.getWidth() / 2, 50, scrollPane.getWidth() / 2, 250);
        Line lineX = new Line(0, 150, scrollPane.getWidth() * 2, 150);
        lineY.setStrokeWidth(2);
        lineX.setStrokeWidth(2);
        Text labelY = new Text("y");
        Text labelX = new Text("x");
        Text txtDoThi = new Text("Đồ thị hàm số y = sin(x)");
        labelY.setX(scrollPane.getWidth() / 2 - 2);
        labelY.setY(42);
        labelY.setFont(Font.font(20));
        labelX.setX(scrollPane.getWidth() * 0.875 + 4);
        labelX.setY(148);
        labelX.setFont(Font.font(20));
        txtDoThi.setY(280);
        txtDoThi.setX(scrollPane.getWidth() / 2 - 130);
        txtDoThi.setFont(Font.font(30));
        // ve truc toa do
        drawPane.getChildren().add(labelY);
        drawPane.getChildren().add(labelX);
        drawPane.getChildren().add(lineX);
        drawPane.getChildren().add(lineY);
        drawPane.getChildren().add(txtDoThi);
        // bat dau ve
        double beginX = scrollPane.getWidth() / 2 + startX * Math.PI * 30;
        double beginY;
        if (buttonSin.isSelected()) {
            beginY = lineX.getStartY()-Math.sin(startX*Math.PI)*50;
        } else {
            beginY = lineX.getStartY() - Math.cos(startX*Math.PI)*50;
        }
        for (double x = startX * Math.PI; x <= endX * Math.PI; x += ((Math.abs(startX) + endX) * Math.PI / value)) {
            double y;
            if (buttonSin.isSelected()) {
                y = Math.sin(x);
            } else {
                y = Math.cos(x);
            }
            double nextX = scrollPane.getWidth() / 2 + x * 30;
            double nextY = lineX.getStartY() - y * 50;
            Line path = new Line(beginX, beginY, nextX, nextY);
            drawPane.getChildren().add(path);
            beginX = nextX;
            beginY = nextY;
        }
        System.out.println("painted");
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    public void clickBtnThoi(ActionEvent event) {
        dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choosingColor = new Color(0, 0, 0, 1);
        colorbutton1 = new ColorButton(color1, choosingColor);
        colorButton2 = new ColorButton(color2, Color.WHITE);
        color1.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(4))));
        ColorButton[][] colorButton = new ColorButton[10][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                colorButton[j][i] = new ColorButton();
                int finalJ = j;
                int finalI = i;
                colorButton[j][i].getButton().setOnMousePressed(event -> {
                    colorButton2.setColor(choosingColor);
                    choosingColor = colorButton[finalJ][finalI].getColor();
                    colorbutton1.setColor(choosingColor);
                    colorButton[finalJ][finalI].getButton().setBorder(new Border(new BorderStroke(new Color(0, 0, 1, 1), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
                });
                colorButton[j][i].getButton().setOnMouseReleased(event -> colorButton[finalJ][finalI].getButton().setBorder(null));
                gridColor.add(colorButton[j][i].getButton(), j, i);
            }
        }
        colorPicker.setValue(Color.BLACK);
        // menu item Size
        size1.setOnAction(event -> currentSize = 1);
        size2.setOnAction(event -> currentSize = 2);
        size3.setOnAction(event -> currentSize = 3);
        size4.setOnAction(event -> currentSize = 5);
        mainPane.localToScene(0, 0);
        drawPane = new Pane();
        drawPane.setPrefSize(1920, 1080);
        drawPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        scrollPane.setContent(drawPane);
        drawPane.getChildren().add(brushesPane);
        drawPane.getChildren().add(chartPane);
        chartPane.setVisible(false);
        drawPane.setOnMouseMoved(event -> {
            int x = (int) event.getX();
            int y = (int) event.getY();

            txtPosition.setText(x + ", " + y + "px");
            if (x == 0 || y == 0) {
                txtPosition.setText("");
            }
        });
//
        btnPen.setOnMouseClicked(event -> {
            clickButton(btnPen);
            System.out.println(choosingColor.toString());
            if (!choosingButton.isCancelButton()) {
//                Image image = new Image("images/pen.png");
//                ImageCursor cursor = new ImageCursor(image);
                scrollPane.setCursor(Cursor.HAND);
                choosingButton.setCancelButton(true);
                drawPane.setOnMousePressed(event1 -> {

                    AtomicReference<Double> startX = new AtomicReference<>(event1.getX());
                    AtomicReference<Double> startY = new AtomicReference<>(event1.getY());

                    drawPane.setOnMouseDragged(event2 -> {
                        double endX = event2.getX();
                        double endY = event2.getY();
                        Line line = new Line(startX.get(), startY.get(), endX, endY);
                        line.setStrokeWidth(currentSize);
                        line.setStroke(choosingColor);
                        drawPane.getChildren().add(line);
                        startX.set(endX);
                        startY.set(endY);
                    });

                });

            } else {
                cancelDraw();
            }
        });
    }

    private void clickButton(Button newButton) {
        if (choosingButton != null) {
            choosingButton.setBorder(Border.EMPTY);
            choosingButton.setCancelButton(false);
        }
        choosingButton = newButton;
        Paint border = new Color(0, 0, 1, 1);
        choosingButton.setBorder(new Border(new BorderStroke(border, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void cancelDraw() {
        scrollPane.setCursor(Cursor.DEFAULT);
        choosingButton.setCancelButton(false);
        drawPane.setOnMousePressed(null);
        drawPane.setOnMouseDragged(null);
        choosingButton.setBorder(Border.EMPTY);
    }
}
