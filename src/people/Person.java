package people;

import KillEvent.JudgingEvent;
import card.Card;
import card.JudgeCard;
import card.SkillCard;
import lib.Nettool;
import lib.Player;
import lib.Room;
import skills.Skill;
import lib.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Person {
    private int life;
    private int maxlife;
    private Vector<Card> handcards;
    private Stack<JudgeCard> judgecards;
    private Vector<Pair<Skill,Integer>> skills;
    private int id;
    private Vector<Pair<SkillCard,Integer>> skillcards;
    private int cid;
    private String name;
    private int gettingdcnt;
    private boolean recoverget=true;
    private Person nxtp;
    private boolean useable=true;
    private Player pl;
    private OutputStream out;
    private BlockingQueue<String> in;

    public Person(OutputStream o, BlockingQueue<String> i)
    {
        out=o;
        in=i;
    }

    public Person nxtplayer()
    {
        return nxtp;
    }
    public void setnxtplayer(Person n)
    {
        nxtp=n;
    }

    public String getname()
    {
        return name;
    }

    public boolean decendlife(int x)//return false if it causes person dying
    {
        life-=x;
        if(life<=0)
            return false;
        else
            return true;
    }
    public boolean decendlife()
    {
        return decendlife(1);
    }

    public boolean decendmaxlife(int x)//return false if x>=maxlife
    {
        if(x>=maxlife)
            return false;
        maxlife-=x;
        if(life>maxlife)
            life=maxlife;
        return true;
    }
    public boolean decendmaxlife()
    {
        return decendmaxlife(1);
    }

    public int addlife(int x)//return how much of x is not added to life
    {
        if(life+x<=maxlife){
            life+=x;
            return 0;
        }
        else{
            x-=(maxlife-life);
            life=maxlife;
            return x;
        }
    }
    public int addlife()
    {
        return addlife(1);
    }

    public void addmaxlife(int x)//doesn`t add life by auto
    {
        maxlife+=x;
    }
    public void addmaxlife()
    {
        addmaxlife(1);
    }

    public void nxtstage(Room r,int s)
    {
        switch (s)
        {
            case 0: enterstarting(r);
                break;
            case 1: enterjudging(r);
                break;
            case 2: entergetting(r);
                break;
            case 3: enterusing(r);
                break;
            case 4: enterabandoning(r);
                break;
            case 5: enterending(r);
                break;
        }
    }

    private void enterstarting(Room r){};

    private void enterjudging(Room r)
    {
        while(!judgecards.empty())
        {
            JudgeCard c=judgecards.pop();
            Card judger=r.getcard();
            JudgingEvent j=new JudgingEvent(judger,this);
            r.announceevent(j);
            //please rewrite this in case of such skills:get your judger
            c.judgedwapper(r,this,j.seejudger());
        }
    }
    public void addjudgecard(JudgeCard c)
    {
        judgecards.push(c);
    }

    private void entergetting(Room r)
    {
        for(int i=1;i<=gettingdcnt+2;i++)
            handcards.add(r.getcard());
        if(recoverget)
            gettingdcnt=0;
    }
    public void setgettingdcnt(int dc)
    {
        gettingdcnt=dc;
    }

    private void enterusing(Room r)
    {
        if(!useable)
            return;
        try{
           sendhandcards();
           while(true)
           {
               String ins=in.poll(10000, TimeUnit.MILLISECONDS);
               if(ins==null||ins=="302")
                   break;
                int id=Integer.getInteger(in.poll(200,TimeUnit.MILLISECONDS));
                Card current=handcards.remove(id);
                r.returncard(current);
                current.getinfo(in);
                r.announceevent(current.getevent());
           }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public int addskillcard(SkillCard c)//return the id of the skillcard
    {
        id++;
        skills.add(new Pair(c.getSkill(),id));
        c.setid(id);
        cid++;
        skillcards.add(new Pair(c,cid));
        return cid;
    }
    public void serusable(boolean u)
    {
        useable=u;
    }
    private void sendhandcards()throws IOException
    {
        Nettool.send(out,"301".getBytes());
        for(int i=0;i<handcards.size();i++)
        {
            Nettool.send(out,(handcards.get(i).toString()+"\n").getBytes());
        }
    }

    private void enterabandoning(Room r)
    {

    }

    private void enterending(Room r){}


    public void addpoints(int dp)//calling this method signs the end of a game
    {
        pl.endwith(dp);
    }
}
