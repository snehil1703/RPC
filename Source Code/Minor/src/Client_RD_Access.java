
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class Client_RD_Access extends Thread
{
    private JInternalFrame jInternalFrame1 = new JInternalFrame("Server Screen",true, true, true);              
    private JDesktopPane jDesktopPane1;
    private JPanel jPanel1 = new JPanel();
    
    Client_RD_Access(JDesktopPane jDesktopPane1) 
    {
        this.jDesktopPane1 = jDesktopPane1;
        start();  
    }
    public void drawGUI() throws PropertyVetoException
    {
        jInternalFrame1.setLayout(new BorderLayout());
        jInternalFrame1.getContentPane().add(jPanel1,BorderLayout.CENTER);
        jDesktopPane1.add(jInternalFrame1);
        jInternalFrame1.setMaximum(true);
        jPanel1.setFocusable(true);
        jInternalFrame1.setVisible(true);
    }
    public void run()
    {
        Rectangle sScreen = null;
        ObjectInputStream ds = null;
        try 
        {
            drawGUI();
        } 
        catch (PropertyVetoException ex) 
        {
            Logger.getLogger(Client_RD_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            ds = new ObjectInputStream(Front.csocket.getInputStream());
            sScreen =(Rectangle)ds.readObject();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        new Client_RD_ScreenGrab(ds,jPanel1);
        try 
        {
            new Client_RD_Control(jPanel1,sScreen);
        } 
        catch (IOException ex) {
            Logger.getLogger(Client_RD_Access.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
