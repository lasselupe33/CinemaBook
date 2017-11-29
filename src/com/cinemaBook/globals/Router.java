package com.cinemaBook.globals;

import com.cinemaBook.controller.AuditoriumController;
import com.cinemaBook.controller.Controller;
import com.cinemaBook.controller.ScreeningsController;
import com.cinemaBook.model.Auditorium;
import com.cinemaBook.model.Film;
import com.cinemaBook.model.Screening;
import com.cinemaBook.view.AuditoriumView;
import com.cinemaBook.view.ScreeningsView;

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
        // Update the current view
        State.currentView = newView;
        Controller controller;

        // Depending on the new view, determine which MVC-model that should be rendered
        switch (State.currentView) {
            case ScreeningsView:
                // Create models with necessary data
                Screening screeningModel = new Screening(123, new Film("test", 5.4, 12), new Auditorium("sal 1", 4, 5));
                ScreeningsView screeningsView = new ScreeningsView();

                // Create the controller
                controller = new ScreeningsController(screeningModel, screeningsView);
                break;

            case AuditoriumView:
                // Create models with the necessary data
                AuditoriumView auditoriumView = new AuditoriumView();

                // Create the controller
                controller = new AuditoriumController(auditoriumView);
                break;

            default:
                // No other views will be handled
                throw new Error("This view isn't handled yet!");
        }

        // Initialize the MVC
        controller.initialize();

        // Expose the current controller
        State.currentController = controller;
    }
}
