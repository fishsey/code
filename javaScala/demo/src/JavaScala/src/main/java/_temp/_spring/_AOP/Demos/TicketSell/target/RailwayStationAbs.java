package _temp._spring._AOP.Demos.TicketSell.target;

/**
 * RailwayStation 实现 TicketService
 * Created by louis on 2016/4/14.
 */
public class RailwayStationAbs extends TicketServiceAbs
{

    public void sellTicket()
    {
        System.out.println("售票............");
    }

    public void inquire()
    {
        System.out.println("问询..............");
    }

    public void withdraw()
    {
        System.out.println("退票.............");
    }
}
