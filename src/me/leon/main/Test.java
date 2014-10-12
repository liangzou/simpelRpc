package me.leon.main;

import me.leon.client.ClinetCall;
import me.leon.provoder.ServiceProvider;
import me.leon.rpc.RpcUtil;
import me.leon.service.CodingService;
import me.leon.service.Impl.CodingServiceImpl;

/**
 * @author leon(liangzou0318@gmail.com)
 * @date 2014-10-12
 * @filaname Test.java
 */
public class Test
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		// 首先暴露服务
		new Thread()
		{
			public void run()
			{
				ServiceProvider.provider(new CodingServiceImpl(), 8091);
			}
		}.start();

		try
		{
			Thread.sleep(2000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ClinetCall.start("127.0.0.1", 8091);

	}

}
