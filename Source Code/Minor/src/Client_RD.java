
import java.awt.BorderLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;

public class Client_RD 
{
    private JFrame jFrame1=new JFrame();
    private JDesktopPane jDesktopPane1 = new JDesktopPane();
    public void client_rd_call()
    {
        drawGUI();
        new Client_RD_Access(jDesktopPane1);
    }
    public void drawGUI()
    {
            jFrame1.add(jDesktopPane1,BorderLayout.CENTER);
            jFrame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame1.setExtendedState(jFrame1.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            jFrame1.setVisible(true);
    }
}
