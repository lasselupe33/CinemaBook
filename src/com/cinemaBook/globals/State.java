package com.cinemaBook.globals;

import com.cinemaBook.controller.Controller;
import javax.swing.*;

/**
 * This singleton exist for the purpose to contain the current state of the application - I.e. where the user has
 * currently navigated to, the selected screening etc.
 */
public class State {
    // Contains a reference to the current view
    public static ViewTypes currentView;
    public static Controller currentController;

    // View specific state
    public static int viewHeight = 0;
    public static int viewWidth = 0;
}
