package com.cinemaBook.globals;

import com.cinemaBook.controller.AuditoriumController;
import com.cinemaBook.controller.Controller;
import com.cinemaBook.controller.ScreeningsController;

/**
 * The purpose of this singleton is to ensure that the correct controller is being used, depending on which view is
 * currently specified as the active.
 */
public class Router {
    // Reference to the router instance
    private static final Router instance = new Router();

    // Only private access!
    private Router() {}

    // This method returns an instance of the router
    public static Router getInstance() {
        return instance;
    }

    /**
     * This method is called upon user navigation - E.g. navigation from ScreeningsView to BookingsView.
     *
     * @param newView The view to change to
     */
    public void updateLocation(ViewTypes newView) {
        Controller controller;

        // Depending on the new view, determine which MVC-model that should be utilized
        switch (newView) {
            case ScreeningsView:
                // Create the controller
                controller = new ScreeningsController();
                break;

            case AuditoriumView:
                // Create the controller
                controller = new AuditoriumController();
                break;

            default:
                // No other views will be handled
                throw new Error("This view isn't handled yet!");
        }

        // Expose the current controller
        State.currentController = controller;

        // Initialize the MVC
        controller.initialize();
    }
}
