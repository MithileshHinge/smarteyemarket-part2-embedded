import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioPlaying extends Thread{
	public AudioPlaying(){}
	@Override
	public void run() {
		try {
		    FileInputStream fis = new FileInputStream("C://Users//Home//Desktop//warning.mp3");
			//FileInputStream fis = new FileInputStream("//home//nuc//Desktop//warning.mp3");
			Player playMP3;
			playMP3 = new Player(fis);
			 playMP3.play();
			 fis.close();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       

	}
}
