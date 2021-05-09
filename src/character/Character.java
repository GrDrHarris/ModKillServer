package character;

import card.*;
import skills.*;
import java.util.Stack;

public abstract class Character {
    private int maxblood;
    private int blood;
    private Card[] cards;
    private Stack<JudgeCard> judge;
    private Card[] armory;
    private int[] armoryskillid;
    private Skill[] skills;
}
