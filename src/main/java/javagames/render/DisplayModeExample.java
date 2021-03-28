package javagames.render;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayModeExample extends JFrame {
    class DisplayModeWrapper {
        private DisplayMode dm;
        public DisplayModeWrapper(DisplayMode dm) {
            this.dm = dm;
        }

        @Override
        public boolean equals(Object o) {
            DisplayModeWrapper other = (DisplayModeWrapper) o;
            if (dm.getWidth() != other.dm.getWidth()) {
                return false;
            }
            if (dm.getHeight() != other.dm.getHeight()) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return "" + dm.getWidth() + " x " + dm.getHeight();
        }
    }

    private JComboBox displayModes;
    private GraphicsDevice graphicsDevice;
    private DisplayMode currentDisplayMode;

    public DisplayModeExample() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        graphicsDevice = ge.getDefaultScreenDevice();
        currentDisplayMode = graphicsDevice.getDisplayMode();
    }

    private DisplayModeWrapper[] listDisplayModes() {
        List<DisplayModeWrapper> list = new ArrayList<>();
        for(DisplayMode mode : graphicsDevice.getDisplayModes()) {
            if(mode.getBitDepth() == -1) {
                DisplayModeWrapper wrapper = new DisplayModeWrapper(mode);
                if(!list.contains(wrapper)) {
                    list.add(wrapper);
                }
            }
        }
        return list.toArray(new DisplayModeWrapper[0]);
    }

    protected DisplayMode getSelectedMode() {
        DisplayModeWrapper wrapper = (DisplayModeWrapper) displayModes.getSelectedItem();
        DisplayMode dm = wrapper.dm;
        int width = dm.getWidth();
        int height = dm.getHeight();
        int bit = 32;
        int refresh = DisplayMode.REFRESH_RATE_UNKNOWN;
        return new DisplayMode( width, height, bit, refresh );
    }

    protected void onEnterFullScreen() {
        if(graphicsDevice.isFullScreenSupported()) {
            DisplayMode newMode = getSelectedMode();
            graphicsDevice.setFullScreenWindow(this);
            graphicsDevice.setDisplayMode(newMode);
        }
    }

    protected void onExitFullScreen() {
        graphicsDevice.setDisplayMode(currentDisplayMode);
        graphicsDevice.setFullScreenWindow(null);
    }

    private JPanel getMainPanel() {
        JPanel p = new JPanel();
        displayModes = new JComboBox(listDisplayModes());
        p.add(displayModes);
        JButton enterButton = new JButton("Enter Full Screen");
        enterButton.addActionListener((e) -> onEnterFullScreen());
        p.add(enterButton);
        JButton exitButton = new JButton("Exit Full Screen");
        exitButton.addActionListener((e) -> onExitFullScreen());
        p.add(exitButton);
        return p;
    }

    protected void createAndShowGUI() {
        Container canvas = getContentPane();
        canvas.add( getMainPanel() );
        canvas.setIgnoreRepaint(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Display Mode Test");
        pack();
        setVisible( true );
    }

    public static void main(String[] args) {
        final DisplayModeExample app = new DisplayModeExample();
        SwingUtilities.invokeLater(() -> app.createAndShowGUI());
    }
}
