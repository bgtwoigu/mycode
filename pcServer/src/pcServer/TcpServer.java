package pcServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    /**
     * ���Է������ˣ������˿ں�9998,������Ϣ֮���ٻظ���Ϣ
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	System.out.println("��ʼ����");
        while (true) {
            ServerSocket ss = new ServerSocket(9998);// ����9998�˿�
            Socket socket = ss.accept();
            //�ȴ��ͻ������ϣ����ȴ���������
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(dis.readUTF()); //��ӡ���ͻ��˷���������
            //�ظ���Ϣ���ͻ���
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("���ã��ͻ��ˣ�");
            ss.close();//ͨ����֮��Ҫ�رգ���Ȼ�´λᱨ��
            dos.close();
            dis.close();
        }
    }

}