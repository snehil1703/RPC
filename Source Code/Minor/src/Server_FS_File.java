
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_FS_File 
{
    public static void execute()
    {
        ObjectInputStream ois = null;
        try 
        {
            ois = new ObjectInputStream(Front.ssocket.getInputStream());
            String path=(String)ois.readObject();
            File myfile = new File(path);
            byte[] byteArray = new byte[1024];
            FileInputStream fis = new FileInputStream(myfile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            OutputStream os = Front.ssocket.getOutputStream();
            int trxBytes =0;
            while((trxBytes = bis.read(byteArray, 0, byteArray.length)) !=-1)
            {           
                os.write(byteArray, 0, byteArray.length);
                System.out.println("Transfering bytes : "+trxBytes );
            }
            os.flush();
            bis.close();
            ois.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(Server_FS_File.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Server_FS_File.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally 
        {
            try 
            {
                ois.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(Server_FS_File.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }    
}
