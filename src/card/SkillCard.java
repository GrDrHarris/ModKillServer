package card;

import lib.Room;
import people.Person;
import skills.Skill;

public abstract class SkillCard extends Card{
    private int id;
    private boolean placed;
    public SkillCard(int t,int p)
    {
        super(t,p);
    }

    abstract public Skill getSkill();
    public void setid(int x)
    {
        id=x;
    }
    public int getid()
    {
        return id;
    }
}

