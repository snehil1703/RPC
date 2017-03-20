
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server_FS_Directory 
{
    static ObjectInputStream ois=null;
    static ObjectOutputStream oos=null;
    static OutputStream os=null;
    static FileInputStream fis=null;
    static BufferedInputStream bis=null;
    static long total=0,total_f=0;
    
    static void count(File myfile)
    {
        File[] files=myfile.listFiles();
        int i;
        for(i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
                count(files[i]);
            else
                total_f++;
            total++;
        }
    }
    
    static void send_length(File myfile) throws IOException
    {
        File[] files=myfile.listFiles();
        int i;
        for(i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
                send_length(files[i]);
            else
                oos.writeObject(files[i].length());
        }
    }
    
    static void send_path(String a,File myfile) throws IOException
    {
        File[] files=myfile.listFiles();
        int i,j;
        String b;
        for(i=0;i<files.length;i++)
        {
            j=files[i].toString().lastIndexOf("\\");
            b=files[i].toString().substring(j+1);
            b=a+b;
            System.out.println(b);
            if(files[i].isDirectory())
            {
                oos.writeObject("d "+b);
                send_path(b+"\\",files[i]);
            }
            else
            {
                oos.writeObject("f "+b);
            }
        }  
    }
    
    
    static void send_file(File file) throws IOException 
    {
        byte[] byteArray = new byte[1024];
                
        long len;
        int i,j;
        File[] files=file.listFiles();
                
        for(i=0;i<files.length;i++)
        {
            
            System.out.println("Sending: "+files[i].toString());
            if(files[i].isDirectory())
            {
                os = Front.ssocket.getOutputStream();
                
                os.write(0);
                send_file(files[i]);
                os.write(2);
            }
            else
            {
                fis = new FileInputStream(files[i]);
                bis = new BufferedInputStream(fis);
                
                os = Front.ssocket.getOutputStream();
                os.write(1);
                
                len=files[i].length();
                int trxBytes;
                while(len>byteArray.length)
                {
                    trxBytes =bis.read(byteArray, 0, byteArray.length);
                    os.write(byteArray, 0, byteArray.length);
                    os.flush();
                    System.out.println("Transfering bytes : "+trxBytes );
                    len-=trxBytes;
                }    
                System.out.println(len);
                trxBytes =bis.read(byteArray, 0,(int)len);
                os.write(byteArray, 0, trxBytes);
                System.out.println("Transfering bytes : "+trxBytes );
                os.flush();
            }
        }
    }
    
    public static void execute() throws IOException
    {
        int i,j;
        
        ois=new ObjectInputStream(Front.ssocket.getInputStream());
        String path="";
        try 
        {
            path=(String)ois.readObject();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(Server_FS_Directory.class.getName()).log(Level.SEVERE, null, ex);
        } 
        File myfile=new File(path);
        if(!myfile.exists())
            System.out.println("Directory Not Existing.");
        else
            System.out.println("Directory Existing.");
        File[] files=myfile.listFiles();
        
        
        total=0;
        total_f=0;
        
        for(i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
            {
                count(files[i]);
            }
            else
                total_f++;
            total++;
        }
        
        String a;
        
        System.out.println(total);
        a=Long.toString(total);
        
        oos = new ObjectOutputStream(Front.ssocket.getOutputStream());
        oos.writeObject(a);
        
        System.out.println(total_f);
        a=Long.toString(total_f);
        oos.writeObject(a);
        
        for(i=0;i<files.length;i++)
        {
            if(files[i].isDirectory())
                send_length(files[i]);
            else
                oos.writeObject(files[i].length());
        }
        
        
        for(i=0;i<files.length;i++)
        {
            j=files[i].toString().lastIndexOf("\\");
            a=files[i].toString().substring(j+1);
            System.out.println(a);
            if(files[i].isDirectory())
            {
                oos.writeObject("d "+a);
                send_path(a+"\\",files[i]);
            }
            else
            {
                oos.writeObject("f "+a);
            }
        }
        
        byte[] byteArray = new byte[1024];
                
        long len;
                
        for(i=0;i<files.length;i++)
        {
            
            System.out.println("Sending: "+files[i].toString());
            
            if(files[i].isDirectory())
            {
                os = Front.ssocket.getOutputStream();
                
                os.write(0);
                send_file(files[i]);
                os.write(2);
            }
            else
            {
                fis = new FileInputStream(files[i]);
                bis = new BufferedInputStream(fis);
                
                os = Front.ssocket.getOutputStream();
                os.write(1);
                len=files[i].length();
                int trxBytes;
                while(len>byteArray.length)
                {
                    trxBytes =bis.read(byteArray, 0, byteArray.length);
                    os.write(byteArray, 0, byteArray.length);
                    os.flush();
                    System.out.println("Transfering bytes : "+trxBytes );
                    len-=trxBytes;
                }    
                System.out.println(len);
                trxBytes =bis.read(byteArray, 0,(int)len);
                os.write(byteArray, 0, trxBytes);
                System.out.println("Transfering bytes : "+trxBytes );
                os.flush();
            }
        }
        
    }
}