import card.Card;
import character.Character;
import general.Player;
import skills.Skill;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

public class ServerMain {
    private int portid;
    private int cachethreadscount;
    private Vector<Skill> skills;
    private Vector<Character> characters;
    private Map<String,Integer> modnames;
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
    }
    public void start()
    {
        for(int i=0;i<cachethreadscount;i++)
        {
            Thread t = new Thread(new ClientServer());
            t.start();
        }
    }
    private class ClientServer implements Runnable
    {
        private Player P;
        private void renew()
        {
            Thread t = new Thread(new ClientServer());
            t.start();
        }
        public void run()
        {
            try {
                ServerSocket s = new ServerSocket(portid);
                Socket soc = s.accept();
                renew();
                P=new Player();
                InputStream in = soc.getInputStream();
                OutputStream out = soc.getOutputStream();
                BufferedReader read=new BufferedReader(new InputStreamReader(in,"UTF-8"));
                String line;
                StringBuilder sb = new StringBuilder();
                //校验模组安装
                line = read.readLine();
                int clinetmodcount = Integer.getInteger(line);
                for(int i=1;i<=clinetmodcount;i++)
                {
                    line = read.readLine();
                    if(!modnames.containsKey(line))
                    {
                        out.write("400".getBytes());
                        soc.close();
                        return;
                    }
                    P.addmod(line);
                }
                //登录

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
               renew();
            }
        }
    }
}
