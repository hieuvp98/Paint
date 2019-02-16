package entities;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ColorButton {
    private Button button;
    private Color color;

    public ColorButton(Button button, Color color) {
        this.button = button;
        this.color = color;
        this.button.setBackground(new Background(new BackgroundFill(this.color,CornerRadii.EMPTY,Insets.EMPTY)));
    }
    public ColorButton(){
        this.button = new Button();
        this.button.setPadding(new Insets(1,1,1,1));
        this.button.setPrefSize(20,20);
        this.button.setMinSize(20,20);
        this.button.setMaxSize(20,20);
        this.color = new Color(randomColor(),randomColor(),randomColor(),1);
        this.button.setBackground(new Background(new BackgroundFill(this.color, CornerRadii.EMPTY,Insets.EMPTY)));
    }
    private double randomColor(){
        return ThreadLocalRandom.current().nextDouble(0,1.0);
    }
    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.button.setBackground(new Background(new BackgroundFill(this.color,CornerRadii.EMPTY,Insets.EMPTY)));
    }
}
