package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

class BackgroundImg extends JPanel {
    private final Image img;

    public BackgroundImg(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}

class LineArrow {

    private final int x;
    private final int y;
    private final int endX;
    private final int endY;
    private final double angleRadians;
    private final Color color;
    private final int thickness;
    private final double scale;

    private static final int TRIANGLE_LENGTH = 2;
    private static final Polygon ARROW_HEAD = new Polygon();

    static {
        ARROW_HEAD.addPoint(TRIANGLE_LENGTH, 0);
        ARROW_HEAD.addPoint(0, -TRIANGLE_LENGTH / 2);
        ARROW_HEAD.addPoint(0, TRIANGLE_LENGTH / 2);
    }

    public LineArrow(int x, int y, double angleDegrees, int length, Color color, int thickness, int headSize) {
        super();
        this.x = x;
        this.y = y;
        this.color = color;
        this.thickness = thickness;
        this.angleRadians = Math.toRadians(angleDegrees);
        this.scale = headSize / TRIANGLE_LENGTH;
        this.endX = (int) (x + (length - headSize) * Math.cos(angleRadians));
        this.endY = (int) (y + (length - headSize) * Math.sin(angleRadians));
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));
        g2.drawLine(x, y, endX, endY);
        AffineTransform tx1 = g2.getTransform();
        AffineTransform tx2 = (AffineTransform) tx1.clone();
        tx2.translate(endX, endY);
        tx2.scale(scale, scale);
        tx2.rotate(angleRadians);
        g2.setTransform(tx2);
        g2.fill(ARROW_HEAD);
        g2.setTransform(tx1);
    }
}

class ApplicationContents {
    static JFrame frame = new JFrame();;
    static Graphics g;
    static Graphics2D g2;
    static JTextField const_num_1, const_num_2, const_num_3, const_num_4, const_num_5;
    static JTextField var_num_1, var_num_2, var_num_3, var_num_4, var_num_5;
    static JButton btn_run, btn_reset;

    public static JTextField constantTextFieldConfiguration(int label_textField_num) {
        JTextField const_textField = new JTextField(String.format("  %d", label_textField_num), 2);
        const_textField.setEditable(false);
        const_textField.setBackground(Color.pink);
        return const_textField;
    }

    public static void constantTextFieldSetter() {
        int label_textField_num = 1;
        const_num_1 = constantTextFieldConfiguration(label_textField_num);
        label_textField_num += 1;
        const_num_2 = constantTextFieldConfiguration(label_textField_num);
        label_textField_num += 1;
        const_num_3 = constantTextFieldConfiguration(label_textField_num);
        label_textField_num += 1;
        const_num_4 = constantTextFieldConfiguration(label_textField_num);
        label_textField_num += 1;
        const_num_5 = constantTextFieldConfiguration(label_textField_num);
    }

    public static JTextField variableTextFieldConfiguration() {
        JTextField var_textField = new JTextField("", 2);
        var_textField.setBackground(new Color(255, 69, 0));
        return var_textField;
    }

    public static void variableTextFieldSetter() {
        var_num_1 = variableTextFieldConfiguration();
        var_num_2 = variableTextFieldConfiguration();
        var_num_3 = variableTextFieldConfiguration();
        var_num_4 = variableTextFieldConfiguration();
        var_num_5 = variableTextFieldConfiguration();
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

    public static JButton btnRunHandling(JPanel panel) {
        btn_run = new JButton("Run");
//        btn_run.setBackground(Color.GREEN);
        btn_run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_input = "";
                String output_for_gui_drawing = "";

                user_input += var_num_1.getText() + var_num_2.getText() + var_num_3.getText() + var_num_4.getText() + var_num_5.getText();
                System.out.println("user_input : " + user_input);
                output_for_gui_drawing = solver("12345", user_input, 5, 5);
                System.out.println("output_for_gui_drawing : " + output_for_gui_drawing);

                for (int i = 0; i < output_for_gui_drawing.length(); i++) {
                    int x_src = 0, y_src = 0, x_dst = 0, y_dst = 0;
                    //---src---
                    if (output_for_gui_drawing.charAt(i) == const_num_1.getText().charAt(2)) {
                        x_src = const_num_1.getX();
                        y_src = const_num_1.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_2.getText().charAt(2)) {
                        x_src = const_num_2.getX();
                        y_src = const_num_2.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_3.getText().charAt(2)) {
                        x_src = const_num_3.getX();
                        y_src = const_num_3.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_4.getText().charAt(2)) {
                        x_src = const_num_4.getX();
                        y_src = const_num_4.getY();
                    }
                    if (output_for_gui_drawing.charAt(i) == const_num_5.getText().charAt(2)) {
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
                    System.out.println("NO [ " + (i + 1) + " ] : " +
                            String.valueOf(x_src) + "  " +
                            String.valueOf(y_src) + "  " +
                            String.valueOf(x_dst) + "  " +
                            String.valueOf(y_dst));

                    g = panel.getGraphics();
//                    g.setColor(Color.BLACK);
//                    g.drawLine(x_src + 10, y_src+15, x_dst + 10, y_dst);

                    int dx = (x_src+20) - x_dst, dy = (y_src+20) - y_dst;
                    int distance;
                    int distance_1=y_dst-(y_src+20);
                    int distance_2=x_dst-(x_src+20);
                    distance=Math.max(dx,dy);
                    distance=Math.max(distance,distance_1);
                    distance=Math.max(distance,distance_2);

                    double angle = Math.atan2(dy, dx);
//                    System.out.println(angle);
                    if (angle<-2) {
                        distance+=30;
                        angle+=(-0.2);
                    }
                    if (-1.9>angle&&angle>-2.0) {
                        angle+=(-0.1);
                    }
//                    System.out.println(angle);

                    g2 = (Graphics2D) g;
                    g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
                    LineArrow line = new LineArrow(x_dst+10, y_dst-13, angle *180/Math.PI, distance, Color.GREEN, 7, 20);
                    line.draw(g2);

                }
            }
        });
        return btn_run;
    }

    public static JButton btnResetHandling(JPanel panel) {
        btn_reset = new JButton("Reset");
//        btn_reset.setBackground(Color.RED);
        btn_reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var_num_1.setText("");
                var_num_2.setText("");
                var_num_3.setText("");
                var_num_4.setText("");
                var_num_5.setText("");

                panel.removeAll();
                BackgroundImg panel = new BackgroundImg(Toolkit.getDefaultToolkit().getImage("E:\\Java_Projects\\AI\\untitled\\src\\com\\company\\map.jpg"));
                frame.getContentPane().add(panel);
                ApplicationContents.addContentsToPane(panel);
                frame.setVisible(true);
                panel.setVisible(true);

//                g2.clearRect(183, 100, 250, 100);
//                g2.dispose();
//                g2.getComposite();
            }
        });
        return btn_reset;
    }

    public static void addContentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(80, 20, -5, 8);

        constantTextFieldSetter();
        variableTextFieldSetter();

        gbc.gridx = 0;
        gbc.gridy = 0;
        pane.add(const_num_1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        pane.add(const_num_2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        pane.add(const_num_3, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        pane.add(const_num_4, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        pane.add(const_num_5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pane.add(var_num_1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        pane.add(var_num_2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        pane.add(var_num_3, gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        pane.add(var_num_4, gbc);
        gbc.gridx = 4;
        gbc.gridy = 2;
        pane.add(var_num_5, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.gridwidth = 2;
        pane.add(btnRunHandling((JPanel) pane), gbc);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.gridwidth = 2;
        pane.add(btnResetHandling((JPanel) pane), gbc);
    }

    public static void main(String[] args) {
//        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setResizable(false);

        BackgroundImg panel = new BackgroundImg(Toolkit.getDefaultToolkit().getImage("E:\\Java_Projects\\AI\\untitled\\src\\com\\company\\map.jpg"));
        frame.getContentPane().add(panel);
        ApplicationContents.addContentsToPane(panel);

        frame.setVisible(true);
    }
}
