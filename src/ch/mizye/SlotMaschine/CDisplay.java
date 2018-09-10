package ch.mizye.SlotMaschine;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CDisplay extends JTextField {

    public CDisplay() {
        setEditable(false);

        setDisplay(0);
    }

    public void setDisplay(double value) {
        setText("CHF " + value);
    }
}
