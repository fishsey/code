package _learn.designPattern._actionsPattern;

/**
 * Created by root on 4/23/17.
 */
public class MemoSingleMain
{
    public static void main(String args[])
    {
        //originator shows current state
        Originator originator = new Originator();
        originator.setState("状态1");
        System.out.println("初始状态:" + originator.getState());

        //caretaker keep history state
        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("状态2");
        System.out.println("改变后状态:" + originator.getState());


        originator.restoreMemento(caretaker.getMemento());
        System.out.println("恢复后状态:" + originator.getState());

    }
}


class Originator
{
    private String state = "";

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public Memento createMemento()
    {
        return new Memento(this.state);
    }

    public void restoreMemento(Memento memento)
    {
        this.setState(memento.getState());
    }
}

class Memento
{
    private String state = "";

    public Memento(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }
}

class Caretaker
{
    private Memento memento;

    public Memento getMemento()
    {
        return memento;
    }

    public void setMemento(Memento memento)
    {
        this.memento = memento;
    }
}
