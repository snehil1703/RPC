
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class Server_RD_ScreenGrab extends Thread 
{
    Robot robot = null;
    Rectangle rectangle = null; 
    
    public Server_RD_ScreenGrab(Robot robot,Rectangle rect) throws IOException 
    {
        this.robot = robot;
        rectangle = rect;
        start();
    }

    public void run() 
    {
        ObjectOutputStream ds = null; 
        
        try
        {
            ds = new ObjectOutputStream(Front.ssocket.getOutputStream());
            ds.writeObject(rectangle);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
       while(true)
       {
            BufferedImage i = robot.createScreenCapture(rectangle);
            ImageIcon iI = new ImageIcon(i);

            System.out.println("before sending image");
            try 
            {
                ds.writeObject(iI);
                ds.reset();
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(Server_RD_ScreenGrab.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("New screenshot sent");
           
            try
            {
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }    
}
