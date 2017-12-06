package com.cinemaBook.view;

import com.cinemaBook.controller.AuditoriumController;
import com.cinemaBook.globals.State;

import java.awt.*;

/**
 * This class is used to render the auditorium view. Used when the user tries to select the desired seats.
 */
public class AuditoriumView extends View {
    // Contains a reference to the controller (in order to propagate events)
    AuditoriumController controller = (AuditoriumController) State.currentController;

    public AuditoriumView() {
        super();
    }

    public void paint(Graphics g) {
        g.drawRect(0,0,400,500);
        g.drawString("Auditorium", 300, 200);
    }
}
