
import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



public class Server_RD_Control extends Thread
{
    Robot robot = null;
    
    public Server_RD_Control(Robot robot) 
    {
        this.robot = robot;
        start(); 
    }

    public void run()
    {
        Scanner ds = null;
        try 
        {
            System.out.println("Preparing InputStream");
            ds = new Scanner(Front.ssocket.getInputStream());

            while(true)
            {
                System.out.println("Waiting for command");
                int command = ds.nextInt();
                System.out.println("New command: " + command);
                switch(command)
                {
                    case -1:
                        robot.mouseMove(ds.nextInt(), ds.nextInt());
                    break;
                    case -2:
                        robot.mousePress(ds.nextInt());
                    break;
                    case -3:
                        robot.mouseRelease(ds.nextInt());
                    break;
                    case -4:
                        robot.keyPress(ds.nextInt());
                    break;
                    case -5:
                        robot.keyRelease(ds.nextInt());
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}