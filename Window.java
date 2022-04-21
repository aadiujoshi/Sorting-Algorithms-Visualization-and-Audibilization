import java.awt.*;
import javax.swing.*;

public class Window
{
    private JFrame frame;
    private JPanel mainPanel;
    private DrawingPanel canvas;
    
    //canvas sizes
    private final int CANVAS_WIDTH = 1000;
    private final int CANVAS_HEIGHT = 500;
    
    
    public Window()
    {
        frame = new JFrame();
        mainPanel = new JPanel();
        canvas = new DrawingPanel(CANVAS_WIDTH, CANVAS_HEIGHT);
        
        frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());
        
        //canvas.setBorder(new LineBorder(Color.RED, 2));
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setOpaque(false);
        
        mainPanel.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        mainPanel.add(canvas, BorderLayout.CENTER);
        
        frame.add(mainPanel);
        
        frame.pack();
        frame.setVisible(true);
    }
    
    public void drawFrequency(int freq, int index)
    {
        int rectI = (int)freq/10-20;
        
        canvas.drawFreqRect(new Rectangle(index*5, 
        CANVAS_HEIGHT-(int)(2.5*rectI)-5, 5, CANVAS_HEIGHT+10));
    }
    
    public void drawAudioSet(double[][] audioSet)
    {
        for(int i = 0; i < audioSet.length; i++)
        {
            drawFrequency((int)audioSet[i][0], i);
        }
    }
}