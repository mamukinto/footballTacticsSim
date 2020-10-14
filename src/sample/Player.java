package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Player extends Circle {
    private String name;
    private Player partnerY = null;
    private Player partnerX = null;

    public Player(double radius) {
        super(radius);
    }

    public Player(double radius, Paint fill) {
        super(radius, fill);

    }

    public Player() {
    }

    public Player(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
    }

    public Player(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPartnerY() {
        return partnerY;
    }

    public void setPartnerY(Player partnerY) {
        this.partnerY = partnerY;
    }

    public Player getPartnerX() {
        return partnerX;
    }

    public void setPartnerX(Player partnerX) {
        this.partnerX = partnerX;
    }

    @Override
    public String toString() {
        return name;
    }
}
