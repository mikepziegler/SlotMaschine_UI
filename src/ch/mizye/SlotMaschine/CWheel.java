package ch.mizye.SlotMaschine;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.net.URL;

public class CWheel extends JPanel {

    private JLabel label;

    private Random rdm;

    private String[] symbolname = {
            "Tomate", "Banane", "Traube",
            "Herz", "Kachel", "Ass",
            "Kreuz", "Geld", "7"
    };
    private String[] iconurl = {
            "icon-tomato.jpg", "icon-banana.jpg", "icon-grape.jpg",
            "poker-heart.jpg", "poker-diamond.jpg", "poker-shade.jpg",
            "poker-clubs.jpg", "icon_money.png", "seven-gif.gif",
            "slotmaschine_icon.jpg"
    };

    public CWheel() {

        URL url = getClass().getResource("ressources/" + iconurl[9]);
        ImageIcon imageIcon = new ImageIcon(url);

        rdm = new Random();
        label = new JLabel(imageIcon);
        setLayout(new GridBagLayout());
        add(label);

    }

    public int rollWheel() {

        int i = Math.abs(rdm.nextInt() % 9);

        URL url = getClass().getResource("ressources/" + iconurl[i]);

        label.setIcon(new ImageIcon(url));

        int symbol = i;

        return symbol;
    }

    public String getSymbolname(int nr) {
        return symbolname[nr];
    }
}