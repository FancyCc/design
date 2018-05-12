package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ��������
 * ������ܿͻ��˵�����
 * ���ͻ��˵����ӽ��������������̴߳���
 */
public class Server {
	//ά���ͻ��˵�������Ϣ
	public static List<Map<String,Object>> clients=new ArrayList<>();
	
	//������
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(30000);
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(new ServerThread(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
