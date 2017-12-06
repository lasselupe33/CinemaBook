package com.cinemaBook.view;

import javax.swing.*;
import java.awt.*;

public abstract class View extends JComponent {
    public View() {
        super();
        setBounds(200, 200, 600, 600);
        setSize(600, 600);
    }

    /**
     * This method clears previously painted components.
     *
     * Should always be called before custom render methods are implemented.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
