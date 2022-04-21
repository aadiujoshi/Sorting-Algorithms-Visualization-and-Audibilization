import javax.swing.*;
import java.awt.*;

public class DrawingPanel extends JPanel
{
    private final int WIDTH;
    private final int HEIGHT;
    
    private Rectangle freqRect;
    
    public DrawingPanel(int width, int height)
    {
        WIDTH = width; 
        HEIGHT = height;
    }
    
    public void drawFreqRect(Rectangle rect)
    {
        freqRect = rect;
        super.update(getGraphics());
    }
    
    @Override
    public void paintComponent(Graphics gr)
    {
        Graphics2D g = (Graphics2D) gr;
        
        if(freqRect != null)
        {
            g.setColor(Color.BLACK);
            g.fillRect((int)freqRect.getX(), 0, 5, HEIGHT+10);
            
            g.setColor(Color.WHITE);
            g.fill(freqRect);
        }
    }
}