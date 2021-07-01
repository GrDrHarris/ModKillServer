package KillEvent;

import card.TechCard;

abstract public class TechCardUseEvent extends CardUseEvent{
    public TechCardUseEvent(TechCard c,Object[] additions)
    {
        super(c,additions);
    }
}
