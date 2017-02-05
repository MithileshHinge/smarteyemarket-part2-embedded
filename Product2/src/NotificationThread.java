
/*import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class NotificationThread extends Thread {
	
	static int port_note = 6667;
	static ServerSocket serverSocket_note;
	static Socket socket_note;
	public static OutputStream out_note;
	public static InputStream in_note;
	public static boolean continue_sending = true;
	
	public static boolean notify = false;
	public static boolean warn_level1 = false;
	public static boolean warn_level2 = false;
	
	public static boolean ThreadStopped = true;
	public static boolean Bg_changed = false;
	public NotificationThread(){
		try {
			serverSocket_note = new ServerSocket(port_note);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("problem2"));
		}
		
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(0, 10000);
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(Bg_changed){
				
			}
			if (notify) {
				System.out.println("Face is detected......................");
				//SendMail.sendmail_notif=true;
				try {
					Main.alert1given = true;
					while(continue_sending){
						socket_note = serverSocket_note.accept();
						out_note = socket_note.getOutputStream();
						in_note = socket_note.getInputStream();
						out_note.write(1);
						out_note.flush();
						int p = in_note.read();
						socket_note.close();
						if(p==9){
						continue_sending = false;
						}
					}
						continue_sending = true;
					 System.out.println(String.format(".....................................................................................connected level 1"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob2"));
					e.printStackTrace();
				}
				notify = false;
			}
			if (warn_level1) {
				System.out.println("alert level 1...................");
				try {
					while(continue_sending){
						socket_note = serverSocket_note.accept();
						out_note = socket_note.getOutputStream();
						in_note = socket_note.getInputStream();
						out_note.write(2);
						out_note.flush();
						int p = in_note.read();
						socket_note.close();
						if(p==9){
						continue_sending = false;
						}
					}
					
					continue_sending = true;
					 System.out.println(String.format(".....................................................................................connected level 1"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob3"));
					e.printStackTrace();
				}
				warn_level1 = false;
			}
			if (warn_level2) {
				System.out.println("alert level 2...................");
				//SendMail.sendmail_notif=true;
				try {
					socket_note = serverSocket_note.accept();
					out_note = socket_note.getOutputStream();
					out_note.write(3);
					out_note.flush();
					socket_note.close();
					System.out.println(String.format("..................................................................................connected level 2"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob4"));
					e.printStackTrace();
				}
				warn_level2 = false;
			}
			ThreadStopped = false;
		}
	}
}*/

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class NotificationThread extends Thread {

	static int port_note = 6667;
	static int port_frame = 6669;
	static ServerSocket serverSocket_note, serverSocket_frame;
	static Socket socket_note, socket_frame;
	static OutputStream out_note, out_frame;
	public InputStream in_note;
	public byte p;
	public int myNotifId;
	public boolean sendNotif = false;
	public boolean continue_sending = false;
	public BufferedImage notifFrame;

	public NotificationThread() {
		try {
			serverSocket_note = new ServerSocket(port_note);
			serverSocket_frame = new ServerSocket(port_frame);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("problem2"));
		}

	}

	@Override
	public void run() {
		while (true) {
			if (sendNotif) {
				try {
					while (continue_sending) {
						socket_note = serverSocket_note.accept();
						out_note = socket_note.getOutputStream();
						in_note = socket_note.getInputStream();
						out_note.write(p);
						out_note.flush();
						if (p == Main.BYTE_FACEFOUND_VDOGENERATING || p == Main.BYTE_ALERT1) {
							System.out.println("1st notif sent..........................");
							
							socket_frame = serverSocket_frame.accept();
							out_frame = socket_frame.getOutputStream();
							ImageIO.write(notifFrame, "jpg", out_frame);
							socket_frame.close();
						}
						if (p == Main.BYTE_FACEFOUND_VDOGENERATED || p == Main.BYTE_ALERT2)
							System.out.println("2nd vdo generated notif sent.......................");
						DataOutputStream dout_note = new DataOutputStream(out_note);
						dout_note.writeInt(myNotifId);
						dout_note.flush();
						sendNotif = false;
						int q = in_note.read();
						if (q == 9)
							continue_sending = false;
						socket_note.close();
					}
					continue_sending = true;
					// System.out.println(String.format("connected"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob2"));
					e.printStackTrace();
				}
			} else {
				try {
					Thread.sleep(0, 10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
