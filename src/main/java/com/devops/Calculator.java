package com.devops;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Calculator extends JFrame {
    private final JTextField display = new JTextField("0");
    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(320, 420);
        setMinimumSize(new Dimension(280, 360));
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout(8, 8));

        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Segoe UI", Font.BOLD, 32));
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(display, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new GridBagLayout());
        buttons.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        addButton(buttons, "C", 0, 0, 1, 1);
        addButton(buttons, "%", 1, 0, 1, 1);
        addButton(buttons, "/", 2, 0, 1, 1);
        addButton(buttons, "*", 3, 0, 1, 1);

        addButton(buttons, "7", 0, 1, 1, 1);
        addButton(buttons, "8", 1, 1, 1, 1);
        addButton(buttons, "9", 2, 1, 1, 1);
        addButton(buttons, "-", 3, 1, 1, 1);

        addButton(buttons, "4", 0, 2, 1, 1);
        addButton(buttons, "5", 1, 2, 1, 1);
        addButton(buttons, "6", 2, 2, 1, 1);
        addButton(buttons, "+", 3, 2, 1, 1);

        addButton(buttons, "1", 0, 3, 1, 1);
        addButton(buttons, "2", 1, 3, 1, 1);
        addButton(buttons, "3", 2, 3, 1, 1);
        addButton(buttons, "=", 3, 3, 1, 2);

        addButton(buttons, "0", 0, 4, 2, 1);
        addButton(buttons, ".", 2, 4, 1, 1);

        add(buttons, BorderLayout.CENTER);
    }

    private void addButton(JPanel panel, String label, int x, int y, int width, int height) {
        JButton button = new JButton(label);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.addActionListener(this::handleButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        panel.add(button, gbc);
    }

    private void handleButton(ActionEvent e) {
        String command = ((JButton) e.getSource()).getText();
        String current = display.getText();

        if (command.matches("[0-9.]")) {
            if (command.equals(".") && current.contains(".") && !startNewNumber) {
                return;
            }
            if (startNewNumber) {
                display.setText(command.equals(".") ? "0." : command);
                startNewNumber = false;
            } else {
                display.setText(current + command);
            }
            return;
        }

        switch (command) {
            case "C":
                display.setText("0");
                firstOperand = 0;
                operator = "";
                startNewNumber = true;
                break;
            case "%":
                display.setText(CalculatorEngine.format(Double.parseDouble(current) / 100));
                startNewNumber = true;
                break;
            case "=":
                if (!operator.isEmpty()) {
                    double secondOperand = Double.parseDouble(current);
                    try {
                        double result = CalculatorEngine.calculate(firstOperand, secondOperand, operator);
                        display.setText(CalculatorEngine.format(result));
                        operator = "";
                        startNewNumber = true;
                    } catch (ArithmeticException ex) {
                        showError(ex.getMessage());
                    }
                }
                break;
            default:
                if (!operator.isEmpty() && !startNewNumber) {
                    double secondOperand = Double.parseDouble(current);
                    try {
                        double result = CalculatorEngine.calculate(firstOperand, secondOperand, operator);
                        display.setText(CalculatorEngine.format(result));
                        firstOperand = result;
                    } catch (ArithmeticException ex) {
                        showError(ex.getMessage());
                        break;
                    }
                } else {
                    firstOperand = Double.parseDouble(current);
                }
                operator = command;
                startNewNumber = true;
                break;
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}
