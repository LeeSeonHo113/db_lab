package site.metacoding.chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChatClientProgram extends JFrame{
   // JPanel �⺻ ���̾ƿ� = FlowLayout
   JPanel northPanel, southPanel;
   TextArea chatList; // JTextArea ���� �ֳ�...
   ScrollPane scroll;
   JTextField txtHost, txtPort, txtMsg;
   JButton btnConnect, btnSend;
   
   public ChatClientProgram() {
      setTitle("MyChat1.0");
      setSize(400, 500);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      
      northPanel = new JPanel();
      southPanel = new JPanel();
      
      chatList = new TextArea(10, 30);
      chatList.setBackground(Color.ORANGE);
      chatList.setForeground(Color.BLUE);
      scroll = new ScrollPane();
      scroll.add(chatList); // ��ũ�ѿ� textarea �ޱ�
      
      txtHost = new JTextField(20); // ������ 20
      txtHost.setText("127.0.0.1");
      txtPort = new JTextField(5);
      txtPort.setText("5000");
      
      txtMsg = new JTextField(25); // ������ 20
      
      btnConnect = new JButton("Connect");
      btnSend = new JButton("Send");
      
      northPanel.add(txtHost);
      northPanel.add(txtPort);
      northPanel.add(btnConnect);
      
      southPanel.add(txtMsg);
      southPanel.add(btnSend);
      
      add(northPanel, BorderLayout.NORTH);
      add(scroll, BorderLayout.CENTER);
      add(southPanel, BorderLayout.SOUTH);
      
      initListener();
      
      setVisible(true);
   }
   
   private void initListener() {
      btnConnect.addActionListener((e)->{
         String host = txtHost.getText();
         String port = txtPort.getText();
         System.out.println(host+"������ "+port+"��Ʈ�� �����մϴ�.");
      });
      
      btnSend.addActionListener((e)->{
         String msg = txtMsg.getText();
         chatList.append(msg+"\n");
         txtMsg.setText(""); // ����
         txtMsg.requestFocus(); // Ŀ�� �α�
      });
   }
   
   public static void main(String[] args) {
      new ChatClientProgram();
   }
}