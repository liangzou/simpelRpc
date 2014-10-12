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
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeUTF(method.getName());
		oos.writeObject(method.getParameterTypes());
		oos.writeObject(args);
		oos.flush();
		
		ObjectInputStream ois =new ObjectInputStream(socket.getInputStream());
		Object result = ois.readObject();
		ois.close();
		return result;
	}

}
