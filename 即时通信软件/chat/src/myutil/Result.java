package myutil;

/**
 * ��װһ����Ϣ������һ�ν����Ľ��
 */
public class Result {
		//��Ϣ����
		private int type;
		
		//��Ϣ�ܳ���
		private int totalLen;
		
		//��Ϣ����
		private byte[] data;
		
		//����Ϣ���������ֹ���һ����Ϣʵ��
		public Result(int type, int totalLen, byte[] data) {
			super();
			this.type = type;
			this.totalLen = totalLen;
			this.data = data;
		}
		
		//������setter��getter����
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public int getTotalLen() {
			return totalLen;
		}
		public void setTotalLen(int totalLen) {
			this.totalLen = totalLen;
		}
		public byte[] getData() {
			return data;
		}
		public void setData(byte[] data) {
			this.data = data;
		}
}
