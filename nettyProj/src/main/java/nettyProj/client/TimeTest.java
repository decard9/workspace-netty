package nettyProj.client;

public class TimeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimeClient tc = new TimeClient();
		tc.myClient.setSendMsg("1111ssldkjlkjkjafd");
		tc.setHost("localhost");
		tc.setPort(9000);
		System.out.println("test 1");
		try {
			System.out.println("test 2");
			tc.runClient();
			System.out.println("test 3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
