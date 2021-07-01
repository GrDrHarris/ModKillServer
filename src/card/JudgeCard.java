package card;

import lib.Room;
import people.Person;

public abstract class JudgeCard extends Card {
    public JudgeCard(int t,int p)
    {
        super(t,p);
    }
    //please always call judgedwapper unless the judger is useful
    public void judgedwapper(Room r,Person p,Card c)
    {
        judged(r,p,c);
        r.returncard(this);
        r.returncard(c);
    }
    abstract public void judged(Room r,Person p,Card c);

}
