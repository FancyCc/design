package client;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import myutil.Protocol;
import myutil.Result;

/**
 * �ͻ�����Ϣ�߳�
 * ���Խ��շ�������Ϣ
 * @author Administrator
 *
 */
public class ClientThread extends Thread {
	private Socket socket;//�׽���
	private DataInputStream dis;//������
	
	//��ʼ���׽�����������
	public ClientThread(Socket socket) {
		this.socket=socket;
		try {
			dis=new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			//������Ϣ
			Result result =  Protocol.getResult(dis);
			if(result!=null)
			//������Ϣ���ʹ���
			handleType(result.getType(),result.getData());
		}
	}
	
	/**
	 * ������Ϣ�����Ͷ���Ϣ����
	 * @param type ��Ϣ����
	 * @param data ��Ϣ����
	 */
	private void handleType(int type, byte[] data) {
		SimpleDateFormat df=new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
		String time=df.format(new Date());
		switch (type) {
		case 1:
			//�ı�
			String[] args=new String(data).split("˵��");
			
			View.area.append("  "+args[0]+"("+time+")\n  "+args[1]+"\n");
			break;
		case 4:
			View.area.append("  "+new String(data)+"\n");
			break;
		case 5:
			View.area.append("  "+new String(data)+"\n");
		default:
			break;
		}
		View.area.select(View.area.getText().length(), View.area.getText().length());
	}
}
