package _learn.designPattern._constructPattern;

/**
 * Created by root on 5/5/17.
 */
public class FacadeMain
{
    public static void main(String[] args)
    {
        Computer computer = new Computer();
        computer.start();
        computer.shutDown();
    }
}

class Computer
{

    private CPU cpu;
    private Memory memory;
    private Disk disk;

    public Computer()
    {
        cpu = new CPU();
        memory = new Memory();
        disk = new Disk();
    }

    public void start()
    {
        System.out.println("Computer start begin >>>");
        cpu.start();
        disk.start();
        memory.start();
        System.out.println("Computer start end >>>");
    }

    public void shutDown()
    {
        System.out.println("Computer shutDown begin <<<");
        cpu.shutDown();
        disk.shutDown();
        memory.shutDown();
        System.out.println("Computer shutDown end <<<");
    }
}

class CPU
{

    public void start()
    {
        System.out.println("cpu is start...");
    }

    public void shutDown()
    {
        System.out.println("CPU is shutDown...");
    }
}

class Disk
{
    public void start()
    {
        System.out.println("Disk is start...");
    }

    public void shutDown()
    {
        System.out.println("Disk is shutDown...");
    }
}

class Memory
{

    public void start()
    {
        System.out.println("Memory is start...");
    }

    public void shutDown()
    {
        System.out.println("Memory is shutDown...");
    }
}





