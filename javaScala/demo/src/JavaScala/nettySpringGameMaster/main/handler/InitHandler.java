package _temp._netty._nettySpringGameMaster.main.handler;

import _temp._netty._nettySpringGameMaster.main.domain.GameRequest;
import _temp._netty._nettySpringGameMaster.main.domain.GameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project: demo
 * @Title: InitHandler.java
 * @Package: cpgame.demo.handler
 * @author: chenpeng
 * @email: 46731706@qq.com
 * @date: 2015年8月20日 下午2:27:11
 * @description:
 * @version:
 */
public class InitHandler implements GameHandler {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void execute(GameRequest request, GameResponse response) {
		this.logger.error(request.readString());
		response.write("I am ok!");
	}
}
