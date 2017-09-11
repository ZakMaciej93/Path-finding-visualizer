package editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

public class RoundTextArea extends JTextArea{

    public RoundTextArea() {
        super();
        setWrapStyleWord(true);
        setLineWrap(true);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
        setBorder(new EmptyBorder(4, 4, 4, 4));
        setEditable(false);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        setVisible(false);
        DefaultCaret c=(DefaultCaret)getCaret();
        c.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        setColumns(25);
        setSize(getPreferredSize());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D=(Graphics2D)g.create();
        int width=getWidth();
        int height=getHeight();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setPaint(Color.BLACK);
        g2D.drawRoundRect(0, 0, width-1, height-1, 25, 25);
        g2D.setPaint(new GradientPaint(0, 0, Color.YELLOW, width, height, Color.ORANGE, true));
        g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2D.fillRoundRect(1, 1, width-2, height-2, 25, 25);
        g2D.dispose();
        super.paintComponent(g);
    }

    @Override
    public void setText(String text) {
        
        super.setText(text);
        setSize(getPreferredSize());
    }
}
