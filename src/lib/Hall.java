package lib;

import people.Person;

public class Hall {
    private Room room;
    private Player[] players;//players[0] is the host
    private int playercnt;
    private int expectcnt;
    private String id;
    public Hall(Player host,int exp,String i)
    {
        players=new Player[exp];
        players[0]=host;
        expectcnt=exp;
        id=i;
    }
    public synchronized boolean tryadd(Player p)
    {
        if(playercnt==expectcnt)
            return false;
        else{
            players[playercnt]=p;
            playercnt++;
            return true;
        }
    }
    public synchronized void quit(Player p)
    {
        for(int i=1;i<playercnt;i++)
        {
            if(players[i]==p)
            {
                for(int j=i+1;j<playercnt;j++)
                {
                    players[j-1]=players[j];
                }
            }
        }
        playercnt--;
    }
    public String getinfoemation()
    {
        StringBuilder str=new StringBuilder();
        str.append("host:"+players[0].toString()+"\nguests:\n");
        for(int i=1;i<playercnt;i++)
            str.append(players[i].toString()+"\n");
        return str.toString();
    }
    public void erease()
    {
        for(int i=0;i<playercnt;i++)
        {
            players[i].kicked();
        }
    }
    public String getid()
    {
        return id;
    }
}
