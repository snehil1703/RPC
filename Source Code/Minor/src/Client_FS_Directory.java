
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client_FS_Directory 
{
    static String spath,dpath;
    static int byteRead=0;
    static long total=0,count1=0,count_f=0,f=0;
    static ObjectOutputStream oos = null;
    static ObjectInputStream ois=null;
    static InputStream is=null;
    static FileOutputStream os=null;
    static long[] len_f;
                            
    static void receive_file(File file) throws IOException
    {
        File[] files=file.listFiles();
        int j,k=0;
        long dsv=0;
        
        while(total<count1)
        {
            j=is.read();
            if(j==0)
            {
                System.out.println("Directory: "+files[k].toString());
                receive_file(files[k]);
                k++;
                total++;
            }
            else if(j==2)
            {
            }
            else
            {   
                dsv=len_f[(int)f];
                f++;
                System.out.println("File: "+files[k].toString());
                os=new FileOutputStream(files[k].toString());
                byte[] byteArray = new byte[1024];
                System.out.println("Total: "+dsv);
                while(dsv>byteArray.length)
                {
                    byteRead = is.read(byteArray, 0, byteArray.length);
                    System.out.println("byteRead: "+byteRead);
                    os.write(byteArray, 0, byteArray.length);
                    System.out.println("No. of Bytes Received : "+byteRead);
                    dsv-=byteRead;
                    os.flush();
                }
                byteRead = is.read(byteArray, 0,(int)dsv);
                os.write(byteArray, 0, byteRead);
                System.out.println("No. of Bytes Received : "+byteRead );

                os.flush();
                os.close();
                    
                k++;
                total++;
                    
            }
        }    
    }
    public static void execute() 
    {
        String path,temppath,rp;
        int pos1,i,j,k;
        long dsv;
        try
        {
            path=spath;
            pos1=path.lastIndexOf("\\");
            pos1++;
                
            oos=new ObjectOutputStream(Front.csocket.getOutputStream());
            oos.writeObject(path);
                
            File mainfile=new File(dpath+"\\"+path.substring(pos1));
            if(!mainfile.exists())
                mainfile.mkdir();
            temppath=dpath+"\\"+path.substring(pos1);        
                
            ois=new ObjectInputStream(Front.csocket.getInputStream());
            
            rp=(String)ois.readObject();
            count1=Long.parseLong(rp);
            System.out.println(count1);
            
            rp=(String)ois.readObject();
            count_f=Long.parseLong(rp);
            System.out.println(count_f);
            
            len_f = new long[(int)count_f];
            
            for(i=0;i<count_f;i++)
            {
                len_f[i]=(long)ois.readObject();
                System.out.println(len_f[i]);
            }
            
            for(i=0;i<count1;i++)
            {
                rp=(String)ois.readObject();
                
                File file=new File(temppath+"\\"+rp.substring(2));
                System.out.println(file.toString());
                if(!file.exists())
                {    
                    if(rp.toString().charAt(0)=='f')
                        file.createNewFile();
                    else if(rp.toString().charAt(0)=='d')
                        file.mkdir();
                }
            }
            
            total=0;
            f=0;
            is=Front.csocket.getInputStream();
            
            File[] files=mainfile.listFiles();
            k=0;
            while(total<count1)
            {
                j=(int)is.read();
                System.out.println(j);
                if(j==0)
                {
                    System.out.println("Directory: "+files[k].toString());
                    receive_file(files[k]);
                    k++;
                    total++;
                }
                else if(j==2)
                {
                }
                else if(j==1)
                {
                    dsv=len_f[(int)f];
                    f++;
                    System.out.println("File: "+files[k].toString());
                    os=new FileOutputStream(files[k].toString());
                    byte[] byteArray = new byte[1024];
                    System.out.println("Total: "+dsv);
                    while(dsv>byteArray.length)
                    {
                        byteRead = is.read(byteArray, 0, byteArray.length);
                        System.out.println("byteRead: "+byteRead);
                        os.write(byteArray, 0, byteArray.length);
                        System.out.println("No. of Bytes Received : "+byteRead);
                        dsv-=byteRead;
                        os.flush();
                    }
                    byteRead = is.read(byteArray, 0,(int)dsv);
                    os.write(byteArray, 0, byteRead);
                    System.out.println("No. of Bytes Received : "+byteRead );

                    os.flush();
                    os.close();
                    
                    k++;
                    total++;
                    
                }
            }
            Client_FS.val=true;
            
        } catch (IOException ex) {
            Logger.getLogger(Client_FS_Directory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client_FS_Directory.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }

    Client_FS_Directory(String spath, String dpath) 
    {
        this.spath=spath;
        this.dpath=dpath;
    }
}