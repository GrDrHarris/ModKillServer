import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;

public class Booter {
    public static void main(String[] args)
    {
        try
        {
            File settings = new File("kill.priorities");
            BufferedReader pin =new BufferedReader(new FileReader(settings));
            int portid=pin.read();
            int cachethreadscount=pin.read();
            ServerMain server = new ServerMain(portid,cachethreadscount);
            int modcount=pin.read();
            for(int i=1;i<=modcount;i++)
            {
                String modname = pin.readLine();
                URL modurl = new URL(modname);
                URLClassLoader myClassLoader = new URLClassLoader(new URL[] {modurl},Thread.currentThread().getContextClassLoader());
                Class<? extends ModFarther> modmain = (Class<? extends ModFarther>) myClassLoader.loadClass(modname+".ModMain");
                ModFarther modmainclass = (ModFarther) modmain.newInstance();
                server.addmod(modmainclass);
            }
            pin.close();
            server.start();
            Scanner in = new Scanner(System.in);
            while(true)
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {

        }
    }
}
