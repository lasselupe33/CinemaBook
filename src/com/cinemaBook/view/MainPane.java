package com.cinemaBook.view;

import com.cinemaBook.globals.Router;
import com.cinemaBook.globals.State;
import com.cinemaBook.globals.ViewTypes;

import javax.swing.*;
import java.awt.*;

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
        // Default values
        int padding = 20;
        int button_height = 30;

        // Create new panel
        JPanel tabPanel = new JPanel();

        // Create flowLayout and set alignment
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);

        // Apply flowLayout to tabPanel
        tabPanel.setLayout(flowLayout);

        // Create new booking button
        JButton bookingsViewButton = new JButton("Create booking");

        bookingsViewButton.addActionListener(e -> handleNavigation(ViewTypes.ScreeningsView));

        tabPanel.add(bookingsViewButton);

        // Create booking overview button
        JButton bookingsOverviewButton = new JButton("Edit bookings");

        bookingsOverviewButton.addActionListener(e -> handleNavigation(ViewTypes.AuditoriumView));

        tabPanel.add(bookingsOverviewButton);

        // Set tabPanel location and size
        tabPanel.setBounds(padding, padding, State.viewWidth - padding*2, button_height);

        // Add tabPanel to Component
        add(tabPanel);
        
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
