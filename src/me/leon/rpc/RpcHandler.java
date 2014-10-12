package me.leon.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @author leon(liangzou0318@gmail.com)
 * @date 2014-10-11
 * @filaname RpcHandler.java
 */
/**
 * invocationHander��ʵ�֣�����ķ�����ִ��ʱͨ��Զ�̷��ʷ����ṩ����
 *  ����ִ�еĽ������
 **/
public class RpcHandler implements InvocationHandler {

	private String ip;
	private int port;
	
	public RpcHandler(String ip, int port)
	{
		this.ip=ip;
		this.port=port;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		Socket socket = new Socket(ip,port);
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		Object result = null;
		try
		{
			try
			{
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeUTF(method.getName());
				oos.writeObject(method.getParameterTypes());
				oos.writeObject(args);
				oos.flush();
				try
				{
				    ois =new ObjectInputStream(socket.getInputStream());
					result = ois.readObject();
				}
				finally
				{
					ois.close();
				}
			}
			finally
			{
				oos.close();
			}
		}
		finally
		{
			socket.close();
		}
		return result;
	}

}
