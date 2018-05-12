package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import myutil.Protocol;
import myutil.Result;

/**
 * ���������߳�
 * ������ͻ���ͨ��
 * @author Administrator
 *
 */
public class ServerThread implements Runnable{
	//�׽���
	public Socket socket;
	
	//���롢�����
	public DataInputStream dis=null;
	public DataOutputStream dos=null;
	
	//�û��ǳ�
	public String userName=null;
	
	//�û�������Ϣ��Map
	public Map<String, Object> thisMap=null;
	
	//��־�߳��Ƿ�����
	public boolean isLive=true;
	
	/**
	 * ������������߳�ʵ��
	 * ��ʼ�����롢�����
	 * @param socket �ͻ����׽���
	 */
	public ServerThread(Socket socket){
		this.socket=socket;
		try {
			dis=new DataInputStream(socket.getInputStream());
			dos=new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �߳���
	 */
	public void run() {
		while(isLive){
			//������Ϣ
			Result result = null;
			result = Protocol.getResult(dis);
			if(result!=null)
			//�����ʹ���
			handleType(result.getType(),result.getData());
		}
	}

	/**
	 * ������Ϣ����ִ����Ӧ����
	 * @param type ����
	 * @param data ��Ϣ����
	 */
	public void handleType(int type, byte[] data) {
		switch (type) {
		case 1:
			//�������ϣ���ȡ�����
			//�������û�ת����Ϣ
			for(int i=0;i<Server.clients.size();i++){
				System.out.println("message:"+new String(data));
				DataOutputStream  dos2=(DataOutputStream) Server.clients.get(i).get("dos");
				String msg=new String(data);
				Protocol.send(Protocol.TYPE_TEXT,(userName+"˵��"+msg).getBytes(),dos2);
			}
			break;
		case 2:
			//����������Ϣ��������������˵ļ�����
			userName=new String(data);
			Map<String,Object> map=new HashMap<>();
			map.put("dos",dos);
			map.put("user",userName);
			Server.clients.add(map);
			
			//֪ͨ�����û����˵�½������
			thisMap=map;
			for(int i=0;i<Server.clients.size();i++){
				DataOutputStream  dos2=(DataOutputStream) Server.clients.get(i).get("dos");
				Protocol.send(Protocol.TYPE_LOADSUCCESS, ("   ϵͳ��"+userName+"����������").getBytes(), dos2);
			}
			break;
		case 3:
			//��֪�����û�����Ҫ�˳�������
			for(int i=0;i<Server.clients.size();i++){
				DataOutputStream  dos2=(DataOutputStream) Server.clients.get(i).get("dos");
				Protocol.send(Protocol.TYPE_LOGOUTSUCCESS, ("   ϵͳ��"+userName+"�˳�������").getBytes(), dos2);
			}
			//ɾ�������б���ĸÿͻ�����Ϣ
			Server.clients.remove(thisMap);
			isLive=false;
			break;
		default:
			break;
		}
	}
}
