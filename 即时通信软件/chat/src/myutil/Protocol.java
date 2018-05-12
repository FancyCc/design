package myutil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Э�鹤����
 * ��װ����Ϣ�������Լ������յķ���
 * @author Administrator
 *
 */
public class Protocol {
	// text
	public static final int TYPE_TEXT = 1;
	
	// ��¼
	public static final int TYPE_LOAD = 2;
	
	// �˳�
	public static final int TYPE_LOGOUT = 3;
	
	//��¼�ɹ�
	public static final int TYPE_LOADSUCCESS = 4;
	
	//�˳��ɹ�
	public static final int TYPE_LOGOUTSUCCESS = 5;

	/**
	 * ��������з�����Ϣ
	 * @param type ��Ϣ����
	 * @param bytes ��Ϣ����
	 * @param dos �����
	 */
	public static void send(int type, byte[] bytes, DataOutputStream dos){
		int totalLen = 1 + 4 + bytes.length;
		try {
			//���ζ�ȡ��Ϣ����������
			dos.writeByte(type);
			dos.writeInt(totalLen);
			dos.write(bytes);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������н�����Ϣ
	 * @param dis ������
	 * @return ����֮��Ľ��
	 */
	public static Result getResult(DataInputStream dis) {
		byte type;
		try {
			//����ȡ����Ϣ����������
			type = dis.readByte();
			int totalLen = dis.readInt();
			byte[] bytes = new byte[totalLen - 4 - 1];
			dis.readFully(bytes);
			//���ؽ������
			return new Result(type & 0xFF, totalLen, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}