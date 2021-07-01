package KillEvent;

import card.Card;
import lib.Room;

abstract public class CardUseEvent extends KillEvent{
    Card card;
    Object[] additions;
    public CardUseEvent(Card c,Object[] a)
    {
        card=c;
        additions=a;
    }
    abstract public void deal(Room r);
}
