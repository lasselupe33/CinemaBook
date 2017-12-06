package com.cinemaBook.view;

import java.awt.*;

/**
 * This class's purpose is to draw the view that correlates to the Screenings tab
 */
public class ScreeningsView extends View {
    public ScreeningsView() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Hello", 200, 300);
    }
}
