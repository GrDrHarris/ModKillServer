package KillEvent;

import lib.Room;
import people.Person;

abstract public class KillEvent implements Comparable<KillEvent>{

    private int majorpriority;
    private int minorpriority;
    private int id;
    public void setid(int i)
    {
        if(id==0)
            id=i;
    }
    @Override
    public int compareTo(KillEvent s) {
        if(s.majorpriority==this.majorpriority)
        {
            if(s.minorpriority==this.minorpriority)
                return s.id-this.id;
            return s.minorpriority-this.minorpriority;
        }
        return s.majorpriority-this.majorpriority;
    }
    abstract public void deal(Room r);
}
