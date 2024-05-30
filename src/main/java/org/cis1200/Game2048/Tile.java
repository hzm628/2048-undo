package org.cis1200.Game2048;

import java.awt.*;

public class Tile {
    private int value;
    private Color tileColor;

    public Tile() {
        this.value = 0;
    }

    public Tile(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return this.value == 0;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public String toString() {
        return "" + value;
    }

    public void setTileColor() {
        if (this.getValue() == 2) {
            tileColor = new Color(238, 228, 218);
        } else if (this.getValue() == 4) {
            tileColor = new Color(250, 224, 200);
        } else if (this.getValue() == 8) {
            tileColor = new Color(250, 177, 121);
        } else if (this.getValue() == 16) {
            tileColor = new Color(240, 150, 60);
        } else if (this.getValue() == 32) {
            tileColor = new Color(245, 100, 80);
        } else if (this.getValue() == 64) {
            tileColor = new Color(250, 94, 59);
        } else if (this.getValue() == 128) {
            tileColor = new Color(237, 207, 114);
        } else if (this.getValue() == 256) {
            tileColor = new Color(237, 204, 97);
        } else if (this.getValue() == 512) {
            tileColor = new Color(237, 200, 80);
        } else if (this.getValue() == 1024) {
            tileColor = new Color(237, 197, 63);
        } else {
            tileColor = new Color(237, 194, 46);
        }
    }

    public Color getTileColor() {
        this.setTileColor();
        return this.tileColor;
    }

}
