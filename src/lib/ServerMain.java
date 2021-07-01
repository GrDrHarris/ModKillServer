package lib;

import java.util.HashMap;

public class ServerMain {
    HashMap<String,Hall> halls;
    public ServerMain()
    {
        halls=new HashMap<>();
    }

    public Hall requesthall(String id)
    {
        return halls.get(id);
    }
    private String getrandstring(int digs)
    {
        StringBuilder str=new StringBuilder();
        for(int i=1;i<=digs;i++)
        {
            str.append((int)Math.floor(10*Math.random()));
        }
        return str.toString();
    }
    public String Bulidhall(Player p,int exp)
    {
        String s=getrandstring(10);
        while(halls.containsKey(s))
            s=getrandstring(10);
        Hall hall=new Hall(p,exp,s);
        halls.put(s,hall);
        return s;
    }
    public void removehall(String id)
    {
        Hall hall=halls.get(id);
        if(hall!=null)
        {
            hall.erease();
        }
        halls.remove(id);
    }
}
