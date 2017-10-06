package _temp._spring._AOP.Demos.TicketSell;

import _temp._spring._AOP.Demos.TicketSell.target.RailwayStation;
import _temp._spring._AOP.Demos.TicketSell.target.TicketService;

/**
 * Created by louis on 2016/4/15.
 */
public class Proxy implements TicketService
{

    private RailwayStation railwayStation;

    @Override
    public void sellTicket()
    {

    }

    @Override
    public void inquire()
    {

    }

    @Override
    public void withdraw()
    {

    }
}
