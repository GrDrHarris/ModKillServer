import card.*;
import skills.*;
import java.util.Vector;

abstract public class ModFarther {
    abstract public void regcharacters(Vector<character.Character> list);
    abstract public void regcard(Vector<Card> list);
    abstract public void regskill(Vector<Skill> list);
    abstract public String getname();
}
