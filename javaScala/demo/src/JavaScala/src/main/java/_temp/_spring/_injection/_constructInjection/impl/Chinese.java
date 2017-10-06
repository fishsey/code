package _temp._spring._injection._constructInjection.impl;

import _temp._spring._injection._constructInjection.Axe;
import _temp._spring._injection._constructInjection.Person;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class Chinese implements Person
{
	private Axe axe;
	public Chinese(Axe axe)
	{
		this.axe = axe;
	}
	public void useAxe()
	{
		System.out.println(axe.chop());
	}
}
