package me.leon.provoder;
import me.leon.rpc.RpcUtil;

/**
 * @author leon(liangzou0318@gmail.com)
 * @date 2014-10-12
 * @filaname ServiceProvider.java
 */
public class ServiceProvider {
	public static void provider(Object service,int port)
	{
		//System.out.println(service.getClass());
		RpcUtil.exporeService(service, port);
	}
}
