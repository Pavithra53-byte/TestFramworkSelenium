package com.selenium.demo.ui;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestRunnerUI extends JFrame {

    private JTextArea outputArea;
    private JButton runTestsButton;
    private JLabel statusLabel;

    public TestRunnerUI() {
        setTitle("Selenium Test Runner");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top status
        statusLabel = new JLabel("Click 'Run Tests' to begin.", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(statusLabel, BorderLayout.NORTH);

        // Center output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom button
        runTestsButton = new JButton("Run Tests");
        runTestsButton.setFont(new Font("Arial", Font.BOLD, 16));
        runTestsButton.addActionListener(e -> runTests());
        add(runTestsButton, BorderLayout.SOUTH);
    }

    private void runTests() {
        outputArea.setText("");
        statusLabel.setText("Running tests...");
        runTestsButton.setEnabled(false);

        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    // Run Maven test command
                    ProcessBuilder builder = new ProcessBuilder("mvn", "test");
                    builder.directory(new java.io.File(System.getProperty("user.dir"))); // root folder
                    builder.redirectErrorStream(true);
                    Process process = builder.start();

                    // Read output
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputArea.append(line + "\n");
                    }

                    int exitCode = process.waitFor();
                    if (exitCode == 0) {
                        statusLabel.setText("Tests Passed!");
                    } else {
                        statusLabel.setText(" Tests Failed! See details above.");
                    }

                } catch (Exception ex) {
                    outputArea.append("Error running tests: " + ex.getMessage());
                    statusLabel.setText("Error during test run.");
                }
                return null;
            }

            @Override
            protected void done() {
                runTestsButton.setEnabled(true);
            }
        };

        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TestRunnerUI ui = new TestRunnerUI();
            ui.setVisible(true);
        });
    }
}
