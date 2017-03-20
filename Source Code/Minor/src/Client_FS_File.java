
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_FS_File 
{
    static int byteRead=0;
    static String spath=null;
    static String dpath=null;
    
    Client_FS_File(String spath, String dpath) 
    {
        this.spath=spath;
        this.dpath=dpath;
    }
    public static void execute()
    {
        String rp;
        int pos1;
        pos1=spath.lastIndexOf("\\");
        rp=spath.substring(pos1+1);
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(Front.csocket.getOutputStream());
            oos.writeObject(spath);

            InputStream in = Front.csocket.getInputStream();

            OutputStream os = new FileOutputStream(dpath+"\\"+rp);
            byte[] byteArray = new byte[1024];

            while((byteRead = in.read(byteArray, 0, byteArray.length))!= -1 )
            {
                os.write(byteArray, 0, byteRead);
                System.out.println("No. of Bytes Received : "+byteRead);
            }
            Client_FS.val=true;
        } 
        catch (IOException ex) 
        {
            Client_FS.val=false;
            Logger.getLogger(Client_FS_File.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
