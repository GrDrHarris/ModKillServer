package KillEvent;

import card.Card;
import lib.Room;
import people.Person;

public class JudgingEvent extends KillEvent{
    Card judger;
    Person target;
    public JudgingEvent(Card j,Person t)
    {
        judger=j;
        target=t;
    }
    public Card seejudger()
    {
        return judger;
    }
    public void resetjudger(Card j)
    {
        judger=j;
    }
    @Override
    public void deal(Room r) {

    }
}
