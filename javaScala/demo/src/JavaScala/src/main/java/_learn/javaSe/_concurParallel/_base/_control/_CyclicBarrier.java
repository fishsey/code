package _learn.javaSe._concurParallel._base._control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class _CyclicBarrier
{
    public static void main(String[] args)
    {
        int nHorses = 5; //the number of horse
        int pause = 200; //the time sleep for each round
        new _CyclicBarrier(nHorses, pause);
    }


    static final int FINISH_LINE = 14;//the steps needed for win
    private List<Horse> horses = new ArrayList<Horse>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;
    
    public _CyclicBarrier(int nHorses, final int pause)
    {
        barrier = new CyclicBarrier(nHorses, new Runnable()
        {
            public void run()//when all horse invoke await(), then barrierAction action invoked
            {
                //output the length of win
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < FINISH_LINE; i++)
                    s.append("=");
                System.out.println(s);

                //output the path of each horse
                for (Horse horse : horses)
                    System.out.println(horse.tracks());

                //judge if has horse has win the game
                for (Horse horse : horses)
                {
                    if (horse.getStrides() >= FINISH_LINE)
                    {
                        System.out.println(horse + "won!");
                        exec.shutdownNow();
                        return;
                    }
                }
                try
                {
                    TimeUnit.MILLISECONDS.sleep(pause);//sleep
                } catch (InterruptedException e)
                {
                    System.out.println("barrier-action sleep interrupted");
                }
            }
        });

        for (int i = 0; i < nHorses; i++)
        {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
        }
    }

    static class Horse implements Runnable
    {
        private static int counter = 0;
        private final int id = counter++;
        private int strides = 0;
        private static Random rand = new Random(47);
        private static CyclicBarrier barrier;

        public Horse(CyclicBarrier b)
        {
            barrier = b;
        }

        public int getStrides()
        {
            return strides;
        }

        public void run()
        {
            try
            {
                while (!Thread.interrupted())
                {
                    //move forward
                    strides += rand.nextInt(3)+1; // Produces 1, 2 or 3
                    //wait other task invoke wait()
                    //after all tasks invoke wait() and barrierAction finished, then return from blocking
                    barrier.await();
                }
            } catch (InterruptedException e)
            {
            } catch (BrokenBarrierException e)
            {
                throw new RuntimeException(e);
            }
        }

        public String toString()
        {
            return "Horse: " + id + " ";
        }

        public String tracks()
        {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < getStrides(); i++)
                s.append("*");
            s.append(id);
            return s.toString();
        }
    }


}


