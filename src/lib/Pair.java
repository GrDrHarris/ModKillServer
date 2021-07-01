package lib;

public class Pair<T,U> {
    private T first;
    private U second;
    public Pair(T f,U s)
    {
        first=f;
        second=s;
    }
    public T getfirst()
    {
        return first;
    }
    public U getsecond()
    {
        return second;
    }
    public void setfist(T f)
    {
        first=f;
    }
    public void setsecond(U s)
    {
        second=s;
    }
}
