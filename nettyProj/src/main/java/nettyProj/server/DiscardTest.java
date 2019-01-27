package nettyProj.server;

public class DiscardTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DiscardServer ds = new DiscardServer(9000);
		try {
			ds.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
