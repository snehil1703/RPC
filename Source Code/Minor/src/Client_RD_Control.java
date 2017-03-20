
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JPanel;

public class Client_RD_Control implements KeyListener,MouseListener,MouseMotionListener
{ 
    private JPanel jPanel1 = null;
    private Rectangle sScreen = null;
    private PrintWriter writer = null;
    
    public Client_RD_Control(JPanel jPanel1, Rectangle sScreen) throws IOException 
    {
        this.jPanel1 = jPanel1;
        this.sScreen = sScreen;
        
        jPanel1.addKeyListener(this);
        jPanel1.addMouseListener(this);
        jPanel1.addMouseMotionListener(this);
        try 
        {
            writer = new PrintWriter(Front.csocket.getOutputStream());
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }

    public void mouseDragged(MouseEvent e) {
    }    

    public void mouseMoved(MouseEvent e) {
        System.out.println("Mouse Moved");
        writer.println(-1);
        writer.println((int)(e.getX()));
        writer.println((int)(e.getY()));
        writer.flush();
    }

    public void mouseClicked(MouseEvent e) {
    }     

    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        writer.println(-2);
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) 
        {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
        writer.println(-3);
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) 
        {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    public void mouseEntered(MouseEvent e) {
    }         

    public void mouseExited(MouseEvent e) {
    }         

    public void keyTyped(KeyEvent e) {
    }    

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        writer.println(-4);
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("Mouse Released");
        writer.println(-5);
        writer.println(e.getKeyCode());
        writer.flush();
    }

}