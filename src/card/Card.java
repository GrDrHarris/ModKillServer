package card;

import KillEvent.CardUseEvent;

import java.util.concurrent.BlockingQueue;

public abstract class Card {
    private int typ;
    private int point;
    private String name;
    public Card(int t,int p)
    {
        typ=t;
        point=p;
    }
    public int gettype()
    {
        return typ;
    }
    public int getpoint()
    {
        return point;
    }
    abstract public void getinfo(BlockingQueue<String> in);
    abstract public CardUseEvent getevent();
    @Override
    public String toString()
    {
        return name+" "+typ+" "+point+"\n";
    }
    //todo move all functions into Carduesevent
}
