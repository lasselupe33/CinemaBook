package com.cinemaBook.globals;

import com.cinemaBook.controller.Controller;
import com.cinemaBook.model.Screening;

import javax.swing.*;

/**
 * This class exist for the purpose to contain the current state of the application - I.e. where the user has
 * currently navigated to, the selected screening etc.
 *
 * All fields should be static, since this class should never be instantiated.
 */
public class State {
    // Contains a reference to a selected Screening (Might be null)
    public static Screening selectedScreening;

    // Contains a reference to the current controller
    public static Controller currentController;

    // View specific state
    public static int viewHeight = 0;
    public static int viewWidth = 0;
}
