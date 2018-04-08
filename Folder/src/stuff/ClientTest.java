package stuff;
import javax.swing.JFrame;

public class ClientTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Client ice;
		ice = new Client("localhost"); //127.0.0.1
		ice.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ice.startRun();
		
		

	}

}
