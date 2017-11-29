package com.cinemaBook.view;

import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.State;
import com.cinemaBook.globals.ViewTypes;

import javax.swing.*;

/**
 * This class has the responsibility to render the main frame in which the other views will be rendered.
 *
 * This class is a singleton, since there should only ever exist one JFrame.
 */
public class MainPane extends JFrame {
    // Reference to the router instance
    private static final MainPane instance = new MainPane();
    private View currentComponent = null;

    /**
     * Constructor is set private since this is a singleton.
     */
    private MainPane() {
        // Get dimensions for app
        State.viewHeight = (int) getToolkit().getScreenSize().getHeight() - 200;
        State.viewWidth = (int) getToolkit().getScreenSize().getWidth() - 200;

        // Set frame settings
        setTitle("cinemaBook");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(State.viewWidth, State.viewHeight);

        // Enable the option to position layout manually
        setLayout(null);

        // Insert buttons to change between tabs;
        insertTabButtons();

        // Display the frame!
        setVisible(true);
    }

    // This method returns the instance of the MainPane
    public static MainPane getInstance() {
        return instance;
    }

    /**
     * This method swaps components when called.
     *
     * @param newComponent The new component to be added to the frame
     */
    public void swapComponent(View newComponent) {
        // Remove old component if one is present
        if (currentComponent != null) {
            remove(currentComponent);
        }

        // Add new component to frame
        add(newComponent);

        // .. And add reference to class as well
        currentComponent = newComponent;

        // Update frame to ensure render
        revalidate();
        repaint();
    }

    /**
     * This method inserts the buttons to change between tabs.
     */
    private void insertTabButtons() {
        // Setup defaults for buttons
        int padding = 20;
        int button_location_x = padding;
        int button_location_y = padding;
        int button_width = 140;
        int button_height = 30;

        // Create first button
        JButton bookingsViewButton = new JButton("Create booking");

        // Position properly
        bookingsViewButton.setBounds(button_location_x, button_location_y, button_width, button_height);

        // Add event listener
        bookingsViewButton.addActionListener(e -> handleNavigation(ViewTypes.ScreeningsView));
        add(bookingsViewButton);

        // Create second button
        JButton bookingsOverviewButton = new JButton("Edit bookings");

        // Position next to first button
        bookingsOverviewButton.setBounds(button_location_x + button_width, button_location_y, button_width, button_height);

        // Add event listener
        bookingsOverviewButton.addActionListener(e -> handleNavigation(ViewTypes.AuditoriumView));
        add(bookingsOverviewButton);
    }

    /**
     * Upon navigation the router should be called with the new view
     *
     * @param newView Enum of the desired view
     */
    private void handleNavigation(ViewTypes newView) {
        Router.getInstance().updateLocation(newView);
    }
}
