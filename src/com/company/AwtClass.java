package com.company;

import java.awt.*;
import java.awt.event.*;

public class AwtClass {
    static Frame frame;
    static Canvas canvas;
    Graphics g;

    TextField const_num_1, const_num_2, const_num_3, const_num_4, const_num_5;
    TextField var_num_1, var_num_2, var_num_3, var_num_4, var_num_5;
    Button btn_run, btn_reset;

    public AwtClass() {
        frame = new Frame("Mini Project");
        canvas = new Canvas();
        canvas.setSize(400, 450);
    }

    public TextField constantTextFieldConfiguration(int label_textField_num, int x_position) {
        TextField const_textField = new TextField();
        const_textField.setText(String.format(" %d", label_textField_num));
        const_textField.setBounds(x_position, 50, 20, 20);
        const_textField.setEditable(false);
        const_textField.setBackground(Color.ORANGE);
        frame.add(const_textField);
        return const_textField;
    }

    public void constantTextFieldSetter(){
        int label_textField_num = 1, x_position = 90;
        const_num_1 = constantTextFieldConfiguration(label_textField_num, x_position);
        label_textField_num += 1;
        x_position += 50;
        const_num_2 = constantTextFieldConfiguration(label_textField_num, x_position);
        label_textField_num += 1;
        x_position += 50;
        const_num_3 = constantTextFieldConfiguration(label_textField_num, x_position);
        label_textField_num += 1;
        x_position += 50;
        const_num_4 = constantTextFieldConfiguration(label_textField_num, x_position);
        label_textField_num += 1;
        x_position += 50;
        const_num_5 = constantTextFieldConfiguration(label_textField_num, x_position);
    }

    public TextField variableTextFieldHandling(int x_position) {
        TextField var_textField = new TextField();
        var_textField.setText("");
        var_textField.setBounds(x_position, 200, 20, 20);
        var_textField.setBackground(Color.CYAN);
        frame.add(var_textField);
        return var_textField;
    }

    public void variableTextFieldSetter(){
        int x_position = 90;
        var_num_1 = variableTextFieldHandling(x_position);
        x_position += 50;
        var_num_2 = variableTextFieldHandling(x_position);
        x_position += 50;
        var_num_3 = variableTextFieldHandling(x_position);
        x_position += 50;
        var_num_4 = variableTextFieldHandling(x_position);
        x_position += 50;
        var_num_5 = variableTextFieldHandling(x_position);
    }

    public void btnRunHandling() {
        btn_run = new Button("Run Algorithm");
        btn_run.setBounds(90, 300, 100, 20);
        btn_run.setBackground(Color.GREEN);

        btn_run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_input = "";
                String output_for_gui_drawing = "";

                user_input += var_num_1.getText() + var_num_2.getText() + var_num_3.getText() + var_num_4.getText() + var_num_5.getText();

                output_for_gui_drawing = solver("12345", user_input, 5, 5);
                System.out.println(output_for_gui_drawing);

                for (int i = 0; i < output_for_gui_drawing.length(); i++) {
                    int x_src = 0, y_src = 0, x_dst = 0, y_dst = 0;
                    //---src---
                    if (output_for_gui_drawing.charAt(i) == const_num_1.getText().charAt(1)) {
                        x_src = const_num_1.getX();
                        y_src = const_num_1.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_2.getText().charAt(1)) {
                        x_src = const_num_2.getX();
                        y_src = const_num_2.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_3.getText().charAt(1)) {
                        x_src = const_num_3.getX();
                        y_src = const_num_3.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_4.getText().charAt(1)) {
                        x_src = const_num_4.getX();
                        y_src = const_num_4.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_5.getText().charAt(1)) {
                        x_src = const_num_5.getX();
                        y_src = const_num_5.getY();
                    }
                    //---dest---
                    if (output_for_gui_drawing.charAt(i) == var_num_1.getText().charAt(0)) {
                        x_dst = var_num_1.getX();
                        y_dst = var_num_1.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == var_num_2.getText().charAt(0)) {
                        x_dst = var_num_2.getX();
                        y_dst = var_num_2.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == var_num_3.getText().charAt(0)) {
                        x_dst = var_num_3.getX();
                        y_dst = var_num_3.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == var_num_4.getText().charAt(0)) {
                        x_dst = var_num_4.getX();
                        y_dst = var_num_4.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == var_num_5.getText().charAt(0)) {
                        x_dst = var_num_5.getX();
                        y_dst = var_num_5.getY();
                    }

                    g = canvas.getGraphics();
                    g.setColor(Color.BLACK);
                    g.drawLine(x_src + 10, y_src, x_dst + 10, y_dst);

                }
            }
        });
        frame.add(btn_run);
    }

    public void btnResetHandling() {
        btn_reset = new Button("Reset Algorithm");
        btn_reset.setBounds(210, 300, 100, 20);
        btn_reset.setBackground(Color.RED);
        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var_num_1.setText("");
                var_num_2.setText("");
                var_num_3.setText("");
                var_num_4.setText("");
                var_num_5.setText("");
                g.clearRect(0,0,600,600);
            }
        });
        frame.add(btn_reset);
    }

    static String solver(String X, String Y, int m, int n) {
        int[][] L = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    L[i][j] = 0;
                else if (X.charAt(i - 1) == Y.charAt(j - 1))
                    L[i][j] = L[i - 1][j - 1] + 1;
                else
                    L[i][j] = Math.max(L[i - 1][j], L[i][j - 1]);
            }
        }

        int index = L[m][n];
        int temp = index;

        char[] lcs = new char[index + 1];
        lcs[index] = '\u0000';

        int i = m;
        int j = n;
        while (i > 0 && j > 0) {
            if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                lcs[index - 1] = X.charAt(i - 1);
                i--;
                j--;
                index--;
            } else if (L[i - 1][j] > L[i][j - 1])
                i--;
            else
                j--;
        }

        String output = "";
        for (int k = 0; k <= temp; k++)
            output += lcs[k];

        return output;
    }

    public static void main(String[] args) {
        AwtClass awtObj = new AwtClass();
        //-----------------Constant Texts-------------------
        awtObj.constantTextFieldSetter();
        //-----------------Variable Texts-------------------
        awtObj.variableTextFieldSetter();
        //------------------Run BTN--------------------------
        awtObj.btnRunHandling();
        //------------------Reset BTN------------------------
        awtObj.btnResetHandling();
        //----------------Config Frame-----------------------

        frame.add(canvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.setSize(400, 450);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
