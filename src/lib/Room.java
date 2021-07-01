package lib;

import KillEvent.KillEvent;
import card.Card;
import people.Person;

import java.util.Queue;
import java.util.TreeSet;

public class Room {
    private Person[] players;//players[0] is the first to operate
    private Card[] abandentcards;
    private Queue<Card> cardstack;
    private TreeSet<KillEvent> events;
    private KillEvent currentevent;
    private int randcnt=1000;
    private int cardcnt;
    private int eventid;
    private boolean gameended;
    private int playercnt;

    public Room(Person[] list,int pc,Card[] cds,int cc)
    {
        players=list;
        for(int i=0;i<pc-1;i++)
            players[i].setnxtplayer(players[i+1]);
        players[pc-1].setnxtplayer(players[0]);
        playercnt=pc;
        for(int i=0;i<cc;i++)
            abandentcards[i]=cds[i];
        cardcnt=cc;
        initcards();
    }
    private void initcards()
    {
        for (int i = 1; i <= randcnt; i++) {
            int u = (int) (Math.random() * cardcnt);
            int v = (int) (Math.random() * cardcnt);
            Card tmp=abandentcards[u];
            abandentcards[u]=abandentcards[v];
            abandentcards[v]=tmp;
        }
        for(int i=0;i<cardcnt;i++)
            cardstack.add(abandentcards[i]);
        cardcnt=0;
    }
    public Card getcard()
    {
        if(cardstack.isEmpty()) {
            initcards();
        }
        return cardstack.poll();
    }
    public void returncard(Card c)
    {
        abandentcards[cardcnt]=c;
        cardcnt++;
    }
    public void announceevent(KillEvent e)
    {
        e.setid(nxteventid());
        events.add(e);
        if(events.first()!=currentevent)
            currentevent=events.first();
        dealevent();
    }
    public int nxteventid()
    {
        eventid++;
        return eventid;
    }
    public void end(int[] dp)
    {
        gameended=true;
        for(int i=0;i<playercnt;i++)
        {
            players[i].addpoints(dp[i]);
        }
    }
    private void dealevent()
    {
        while(currentevent!=null)
        {
            currentevent.deal(this);
            if(gameended)
                return;
            events.pollFirst();
            currentevent=events.first();
        }
    }
    public void run()
    {
        Person currentplayer=players[0];
        while(!gameended)
        {
            for(int i=0;i<=5;i++)
            {
                currentplayer.nxtstage(this,i);
                if(gameended)
                    return;
                dealevent();
                if(gameended)
                    return;
            }
            currentplayer=currentplayer.nxtplayer();
        }
    }
}
