package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import myutil.Protocol;

/**
 * ��װ�ͻ����������ͨ�ŵ�ϸ��
 */
public class Client {

	//�׽���
	Socket socket;
	
	//�����
	DataOutputStream dos = null;

	/**
	 * ���ӷ���������ʼ�������
	 * �����ͻ����̸߳�����Ϣ�Ľ���
	 * @param address ������IP��ַ
	 * @param port �������˿ں�
	 */
	public void conn(String address, int port) {
		try {
			socket = new Socket(address, port);
			dos = new DataOutputStream(socket.getOutputStream());
			new ClientThread(socket).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��¼
	 * @param user �û��ǳ�
	 */
	public void load(String user) {
		Protocol.send(Protocol.TYPE_LOAD,user.getBytes(), dos);
	}

	/**
	 * ������Ϣ
	 * @param msg ��Ϣ����
	 */
	public void sendMsg(String msg) {
		Protocol.send(Protocol.TYPE_TEXT, msg.getBytes(), dos);
	}

	/**
	 * �˳�
	 */
	public void logout(){
		Protocol.send(Protocol.TYPE_LOGOUT, "logout".getBytes(), dos);
	}
	
	/**
	 * �رտͻ��ˣ��ͷŵ���Դ
	 */
	public void close() {
		// ������������˳�����
		Protocol.send(Protocol.TYPE_LOGOUT, new String("logout").getBytes(), dos);
		// �ر���Դ
		try {
			if (dos != null)
				dos.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
