
import java.awt.Graphics;
import java.awt.Image;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Client_RD_ScreenGrab extends Thread
{
    private ObjectInputStream ds = null;
    private JPanel jPanel1 = null;

    Client_RD_ScreenGrab(ObjectInputStream ds, JPanel jPanel1) 
    {
        this.ds = ds;
        this.jPanel1 = jPanel1;
        start();
    }
    
    public void run()
    {
        try 
        {
            while(true)
            {
                ImageIcon iI = (ImageIcon) ds.readObject();
                System.out.println("New image recieved");
                Image i = iI.getImage();
                i = i.getScaledInstance(jPanel1.getWidth(),jPanel1.getHeight(),Image.SCALE_FAST);
                Graphics g = jPanel1.getGraphics();
                g.drawImage(i, 0, 0, jPanel1.getWidth(),jPanel1.getHeight(),jPanel1);
            }
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
     }
}
