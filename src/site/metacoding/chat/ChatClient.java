package site.metacoding.chat;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient extends MyFrame
implements ActionListener, Runnable{
	
	Button connect, send;
	TextField hostTxt, chatTxt;
	TextArea ta;
	BufferedReader in;
	PrintWriter out;
	int port = 8008;
	String user;
	
	public ChatClient() {
		super(250,300);
		setTitle("MyChat1.0");
		Panel p1 = new Panel();
		p1.add(hostTxt = new TextField("127.0.0.1",20));
		p1.add(connect = new Button("Connect"));
		add(p1,BorderLayout.NORTH);
		ta = new TextArea(10,30);
		ta.setBackground(Color.ORANGE);
		ta.setForeground(Color.BLUE);
		ScrollPane s = new ScrollPane();
		s.add(ta);
		s.setPreferredSize(new Dimension(240,190));
		
		add(s, "Center");
		
		Panel p3 = new Panel();
		p3.add(chatTxt = new TextField(20));
		p3.add(send = new Button("보내기"));
		add(p3,BorderLayout.SOUTH);
		connect.addActionListener(this);
		chatTxt.addActionListener(this);
		send.addActionListener(this);
		setResizable(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==connect){
			connect(hostTxt.getText());
			connect.setEnabled(false);
			hostTxt.setEditable(false);
		}else if(e.getSource()==chatTxt||e.getSource()==send){
			if(user==null){
				user = chatTxt.getText();
				setTitle(getTitle() + " [" + user+"]");
				ta.setText("");
			}
			//서버로 메세지를 보낸다.
			out.println(chatTxt.getText());
			chatTxt.setText("");
			chatTxt.requestFocus();
		}
	}

	@Override
	public void run() {
		try {
			while(true){
				ta.append(in.readLine()+"\n");
				//포커스는 텍스트 필드로 전환
				ta.transferFocus();
			}
		} catch (Exception e) {
			System.err.println("Error in run");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void connect(String host){
		try {
			Socket sock = new Socket(host, port);
			in = new BufferedReader(
					new InputStreamReader(
							sock.getInputStream()));
			out = new PrintWriter(
					sock.getOutputStream(),true);
		} catch (Exception e) {
			System.err.println("Error in ChatClient");
			e.printStackTrace();
			System.exit(1);
		}
		new Thread(this).start();
	}
	
	public static void main(String[] args) {
		new ChatClient();
	}
}