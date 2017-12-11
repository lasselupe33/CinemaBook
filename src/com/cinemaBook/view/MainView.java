package com.cinemaBook.view;

import javax.swing.*;

/**
 * This class has the responsibility to render the main frame in which the other views will be rendered.
 *
 * This class is a singleton, since there should only ever exist one JFrame.
 */
public class MainView extends JFrame {
    private static final MainView instance = new MainView();
    private JTabbedPane tabPane;

    private MainView() {
        tabPane = new JTabbedPane();

        // Create the frame and add the tabPane
        JFrame frame = new JFrame("CinemaBook");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(tabPane);
        frame.setBounds(0, 0, 800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // This method returns the instance of the MainPane
    public static MainView getInstance() {
        return instance;
    }

    public JTabbedPane getTabPane() {
        return tabPane;
    }
}
