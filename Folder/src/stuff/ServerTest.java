package stuff;
import javax.swing.JFrame;

public class ServerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Server discord = new Server();
		
		
		discord.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		discord.startRun();

	}

}
