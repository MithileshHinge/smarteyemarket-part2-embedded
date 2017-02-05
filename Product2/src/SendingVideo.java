import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SendingVideo extends Thread {
	
	private int port = 6668;
	private ServerSocket ssVdo;
	private int beginIndex = Main.outputFilename4android.length();
	
	HashMap<Integer, String> notifId2filepaths = new HashMap<>();
	
	public SendingVideo(){
		try {
			ssVdo = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Socket s = ssVdo.accept();
				InputStream sIn = s.getInputStream();
				DataInputStream dIn = new DataInputStream(sIn);
				OutputStream sOut = s.getOutputStream();
				int notifId = dIn.readInt();
				String filepath = notifId2filepaths.get(Integer.valueOf(notifId));
				String filename = filepath.substring(beginIndex);
				DataOutputStream dOut = new DataOutputStream(sOut);
				dOut.writeInt(filename.length());
				dOut.flush();
				sIn.read();
				sOut.write(filename.getBytes());
				sOut.flush();
				sIn.read();
				if (filepath != null){
					sendVideo(sOut, filepath);
				}
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void sendVideo(OutputStream out, String filepath){
		
		FileInputStream fileIn = null;
		try {
			File file = new File(filepath);
			fileIn = new FileInputStream(file);
			byte[] buffer = new byte[16 * 1024];
			int count;
			while ((count = fileIn.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileIn != null) fileIn.close();
				if (out != null) out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
