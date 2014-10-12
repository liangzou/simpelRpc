package me.leon.client;

import me.leon.rpc.RpcUtil;
import me.leon.service.CodingService;

/**
 * @author leon(liangzou0318@gmail.com)
 * @date 2014-10-11
 * @filaname ClinetCall.java
 */
public class ClinetCall {
	public static void start(String ip,int port)
	{
		CodingService service = RpcUtil.getRemoteService(CodingService.class, ip, port);
		service.printHelloWorld("leon");
	}
}
