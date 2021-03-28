package javagames.render;

import javagames.util.FrameRate;

import javax.swing.*;
import java.awt.*;

public class HelloWorldApp extends JFrame {
    private FrameRate frameRate;

    public HelloWorldApp() {
        frameRate = new FrameRate();
    }

    private class GamePanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            onPaint(g);
        }
    }

    protected void onPaint(Graphics g) {
        frameRate.calculate();
        g.setColor(Color.WHITE);
        g.drawString(frameRate.getFrameRate(), 30, 30);
        repaint();
    }

    protected void createAndShowGUI() {
        GamePanel gamePanel = new GamePanel();
        gamePanel.setBackground(Color.BLACK);
        gamePanel.setPreferredSize(new Dimension(320, 240));
        getContentPane().add(gamePanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Hello World!");
        pack();
        frameRate.initialize();
        setVisible(true);
    }

    public static void main(String[] args) {
        final HelloWorldApp helloWorldApp = new HelloWorldApp();
        SwingUtilities.invokeLater(() -> helloWorldApp.createAndShowGUI());
    }
}
