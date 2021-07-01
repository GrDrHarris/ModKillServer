package lib;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Player {
    private InputStream in;
    private OutputStream out;
    private Socket soc;
    private boolean gaming;
    private boolean failed;
    private Scanner reader;
    private ServerMain server;
    private String name;
    private Hall hall;

    public Player(Socket s, File history,String name,ServerMain server)throws IOException
    {
        soc=s;
        in=s.getInputStream();
        out=s.getOutputStream();
        reader=new Scanner(in,"UTF-8");
    }
    public void run()throws IOException
    {
        while(reader.hasNextLine())
        {
            String line=reader.nextLine();
            if(line=="201")
            {
                String roomid=reader.nextLine();
                Hall h=server.requesthall(roomid);
                if(h==null)
                    Nettool.send(out,"241".getBytes());
                else
                {
                    boolean suc=h.tryadd(this);
                    if(suc){
                        hall=h;
                        Nettool.send(out,"211".getBytes());
                        Nettool.send(out,hall.getinfoemation().getBytes());
                    }
                    else {
                        Nettool.send(out,"242".getBytes());
                    }
                }
            }
            if(line=="202")
            {
                if(hall!=null)
                    Nettool.send(out,hall.getinfoemation().getBytes());
            }
            if(line=="203")
            {
                if(hall!=null)
                {
                    hall.quit(this);
                }
            }
            if(line=="204")
            {
                int exp=reader.nextInt();
                server.Bulidhall(this,exp);
            }
            if(line=="205")
            {
                server.removehall(hall.getid());
            }
        }
    }
    public void endwith(int dp)//just temp
    {
        String s;
        if(dp>0)
            s="501\n";
        else
            s="502\n";
        try{
            Nettool.send(out,s.getBytes());
        }catch(IOException e)
        {
            e.printStackTrace();
            try{
                soc.close();
            }catch (IOException ee)
            {
                ee.printStackTrace();
            }
            finally {
                failed=true;
            }
        }
        gaming=false;
    }
    public void kicked()
    {
        try{
            Nettool.send(out,"244".getBytes());
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        hall=null;
    }
    @Override
    public String toString()
    {
        return name;
    }
}
