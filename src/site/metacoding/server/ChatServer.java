package site.metacoding.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;


public class ChatServer {
	
	Vector<ClientThread> vc;
	
	public ChatServer() {
		vc = new Vector<ClientThread>();
		ServerSocket server = null;
		try {
			 server = new ServerSocket(8008);
		} catch (IOException e) {
			System.err.println("Error in ChatServer");
			e.printStackTrace();
			System.exit(1);//1�� ���������� ����
		}
		System.out.println("***************************");
		System.out.println("Ŭ���̾�Ʈ�� ������ ��ٸ��� �ֽ��ϴ�.");
		System.out.println("***************************");
		try {
			while(true){
				Socket sock = server.accept();
				ClientThread ct = 
						new ClientThread(sock);
				ct.start();
				vc.add(ct);
			}
		} catch (Exception e) {
			System.err.println("Error in Socket");
			e.printStackTrace();
			System.exit(1);
		}
	}
	//���ӵ� ��� Client���� �޼��� ����
	public void sendAllClient(String msg){
		for (int i = 0; i < vc.size(); i++) {
			ClientThread ct = vc.get(i);
			ct.sendMessage(msg);
		}
	}

	class ClientThread extends Thread{
		
		ChatServer cs;
		Socket sock;
		BufferedReader in;
		PrintWriter out;
		String user;
		
		public ClientThread(Socket sock) {
			this.sock = sock;
			try {
				in = new BufferedReader(
						new InputStreamReader(
								sock.getInputStream()));
				out = new PrintWriter(
						sock.getOutputStream(),true);
				System.out.println(sock+" ���ӵ�");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				//Client�� ���� ó�� ���� �޼���
				out.println("����Ͻ� ���̵� �Է��ϼ���.");
				user = in.readLine();
				sendAllClient("["+user
						+"]�Բ��� ������ �ϼ̽��ϴ�.");
				String data = null;
				while((data=in.readLine())!=null){
					sendAllClient("["+user+"]"+data);
				}
				in.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(sock+" ������");
			}
		}
		//Client���� �޼��� ����.
		public void sendMessage(String msg){
			out.println(msg);
		}
	}
	
	public static void main(String[] args) {
		new ChatServer();
	}
}