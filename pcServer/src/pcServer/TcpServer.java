package pcServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    /**
     * 电脑服务器端，监听端口号9998,接收消息之后，再回复消息
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	System.out.println("开始监听");
        while (true) {
            ServerSocket ss = new ServerSocket(9998);// 监听9998端口
            Socket socket = ss.accept();
            //等待客户端连上，并等待接收数据
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(dis.readUTF()); //打印出客户端发来的数据
            //回复消息给客户端
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("您好，客户端！");
            ss.close();//通信完之后要关闭，不然下次会报错
            dos.close();
            dis.close();
        }
    }

}