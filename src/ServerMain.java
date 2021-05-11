import card.Card;
import character.Character;
import general.Player;
import skills.Skill;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

public class ServerMain {
    private int portid;
    private int cachethreadscount;
    private boolean exiting;
    private Vector<Skill> skills;
    private Vector<Character> characters;
    private Map<String,Integer> modnames;
    private Vector<String> modlist;
    private Vector<Card> cards;
    public ServerMain(int p,int c)
    {
        portid=p;
        cachethreadscount=c;
    }
    public void addmod(ModFarther mod)
    {
        mod.regskill(skills);
        mod.regcharacters(characters);
        mod.regcard(cards);
        modnames.put(mod.getname(),1);
        modlist.add(mod.getname());
    }
    public void start()
    {
        for(int i=0;i<cachethreadscount;i++)
        {
            Thread t = new Thread(new ClientServer());
            t.start();
        }
    }
    public void stop()
    {
        exiting=true;
    }
    private class ClientServer implements Runnable
    {
        InputStream in;
        OutputStream out;
        BufferedReader read;
        boolean modedcorrect;
        private Player P;
        private void renew()
        {
            Thread t = new Thread(new ClientServer());
            t.start();
        }
        private boolean judgemod() throws IOException //校验模组安装
        {
            String line;
            line = read.readLine();
            int clinetmodcount = Integer.getInteger(line);
            for (int i = 1; i <= clinetmodcount; i++) {
                line = read.readLine();
                if (!modnames.containsKey(line)) {
                    P = null;
                    return false;
                }
                P.addmod(line);
            }
            return true;
        }
        private boolean judgelogin(String name,String pass)
        {
            return false;
        }
        private void afterlogged()
        {

        }
        public void run()
        {
            try {
                while(true) {
                    ServerSocket s = new ServerSocket(portid);
                    Socket soc = s.accept();
                    in = soc.getInputStream();
                    out = soc.getOutputStream();
                    read = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String line;
                    line = read.readLine();
                    if(line.equals("000")){
                        if(exiting)
                            return;
                    }else if(line.equals("100"))
                    {
                        int size=modlist.size();
                        out.write((Integer.toString(size)+"\n").getBytes());
                        for(int i=0;i<size;i++)
                            out.write((modlist.elementAt(i)+"\n").getBytes());
                    }else if(line.equals("200"))
                    {
                        String uname = read.readLine();
                        String password = read.readLine();
                        if (!judgemod())
                        {
                            out.write("400\n".getBytes());
                            soc.close();
                        }
                        if (judgelogin(uname, password))
                        {
                            out.write("201\n".getBytes());
                            renew();
                            afterlogged();
                            return;
                        }
                        else
                            out.write("401\n".getBytes());
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
