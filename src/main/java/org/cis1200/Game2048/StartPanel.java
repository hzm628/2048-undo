package org.cis1200.Game2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartPanel extends JPanel {
    private static final Color TITLE_COLOR = new Color(119, 110, 101);

    public StartPanel() {
        setBackground(Run2048.BG_COLOR);
        setPreferredSize(new Dimension(600, 260));
        setLayout(null);

        final JLabel titleLabel = new JLabel("2048");
        titleLabel.setForeground(TITLE_COLOR);
        Font font = new Font("Arial", Font.BOLD, 60);
        titleLabel.setFont(font);
        titleLabel.setBounds(30, 10, 250, 120);
        add(titleLabel);

        final JLabel howToPlayLabel = new JLabel("<html>" + "<b>" + "HOW TO PLAY: "
                + "</b>" + "Use the " + "<b>"
                + "arrow keys " + "</b>" + "to move tiles." + "<br/>"
                + "Tiles with the same number " + "</b>" + "merge into one" + "</b>"
                + " when they touch." + "<br/>"
                + "Add them up to reach " + "<b>" + "2048" + "</b>" + "!" + "<br/>"
                + "<br/>"
                + "PS: You have access to " + "<b>" + "unlimited undo" + "</b>"
                + " functionality!" + "</html>");
        howToPlayLabel.setForeground(TITLE_COLOR);
        Font font2 = new Font("Arial", Font.PLAIN, 15);
        howToPlayLabel.setFont(font2);
        howToPlayLabel.setBounds(30, 80, 500, 200);
        add(howToPlayLabel);
    }

}