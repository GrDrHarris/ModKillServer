package general;

import java.util.Vector;

public class Player {
    private Character cha;
    private Vector<String> modlist;
    public Character getCharacter()
    {
        return cha;
    }
    public void addmod(String name)
    {
        modlist.add(name);
    }
}
