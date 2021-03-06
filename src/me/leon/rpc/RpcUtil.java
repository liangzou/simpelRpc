package me.leon.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author leon(liangzou0318@gmail.com)
 * @date 2014-10-11
 * @filaname RpcUtil.java
 */
/*
 * 远程过程调用的实现类，提供了暴露服务以及获取服务
 * */
public class RpcUtil
{
	public static <T> void exporeService(final T interfaceNames, int port)
	{
		if (interfaceNames == null || port < 0)
		{
			throw new IllegalArgumentException("参数错误");
		} else
		{
			ServerSocket server = null;
			// Socket socker = null;
			// System.out.println(interfaceNames.getClass().getName());
			try
			{
				server = new ServerSocket(port);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			while (true)
			{
				try
				{
					final Socket socket = server.accept();
					new Thread()
					{
						public void run()
						{
							try
							{
								ObjectInputStream ois = null;
								ObjectOutputStream oos = null;
								try
								{
									try
									{
										ois = new ObjectInputStream(
												socket.getInputStream());
										String methodName = ois.readUTF();
										Class<?>[] parameterTypes = (Class<?>[]) ois
												.readObject();
										Object[] arguments = (Object[]) ois
												.readObject();
										try
										{
											// System.out.println(interfaceNames.getClass().getName());
											Method method = interfaceNames
													.getClass().getMethod(
															methodName,
															parameterTypes);
											Object result = method.invoke(
													interfaceNames, arguments);
											oos = new ObjectOutputStream(
													socket.getOutputStream());
											oos.writeObject(result);
										}

										finally
										{
											oos.close();
										}
									} catch (Exception e)
									{
										e.printStackTrace();
									} finally
									{
										ois.close();
									}
								} finally
								{
									socket.close();
								}
							}

							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}.start();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static <T> T getRemoteService(Class<T> interfaceName, String ip,
			int port)
	{
		if (interfaceName == null)
			return null;
		else
		{
			return (T) Proxy.newProxyInstance(interfaceName.getClassLoader(),
					new Class<?>[]
					{ interfaceName }, new RpcHandler(ip, port));
		}
	}
}
