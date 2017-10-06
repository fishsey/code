package _temp._netty._nettySpringGameMaster.main.handler;

import _temp._netty._nettySpringGameMaster.main.domain.GameRequest;
import _temp._netty._nettySpringGameMaster.main.domain.GameResponse;

/** 
* @project:		demo
* @Title:		GameHandler.java
* @Package:		cpgame.demo.handler
  @author: 		chenpeng
* @email: 		46731706@qq.com
* @date:		2015年8月20日 下午2:25:51 
* @description:
* @version:
*/

public abstract interface GameHandler
{
  public abstract void execute(GameRequest paramGameRequest, GameResponse paramGameResponse);
}
