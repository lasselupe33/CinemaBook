package com.cinemaBook.globals;

import com.cinemaBook.controller.AuditoriumController;
import com.cinemaBook.controller.ScreeningsController;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;

public class RouterTest {
    /**
     * This test ensures that the Router properly updates the controller when a new view is given.
     */
    @Test
    public void testLocationUpdate() {
        // Update location to auditoriumView
        ViewTypes newView = ViewTypes.AuditoriumView;
        Router.getInstance().updateLocation(newView);

        // Ensure that the current controller is of the type auditoriumController
        assertThat(State.currentController, instanceOf(AuditoriumController.class));

        // Update location to screeningsView
        newView = ViewTypes.ScreeningsView;
        Router.getInstance().updateLocation(newView);

        // Ensure that the current controller is now of the type screeningsController
        assertThat(State.currentController, instanceOf(ScreeningsController.class));
    }
}