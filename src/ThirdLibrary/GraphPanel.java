package ThirdLibrary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class GraphPanel extends JPanel {
    private int compressionPercentage = 100;

    public GraphPanel() {
        this.setPreferredSize(new Dimension(100, 200));
        this.setBackground(new Color(200, 220, 240));
    }

    public void updateCompression(int compressionPercentage) {
        this.compressionPercentage = compressionPercentage;
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int height = (int) ((double) (this.getHeight() * this.compressionPercentage) / 100.0);
        g.setColor(new Color(70, 130, 180));
        g.fillRect(10, this.getHeight() - height, this.getWidth() - 20, height);
    }
}
