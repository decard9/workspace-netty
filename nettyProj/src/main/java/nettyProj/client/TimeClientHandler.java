package nettyProj.client;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class TimeClientHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) {
		//3.서버로부터 수신된 데이터가 있을 때 호출되는 메서드
		System.out.println("client channelRead1");
	     
	    String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());
	    //4.서버로 부터 수신된 데이터가 저장된 msg 객체에서 문자열 데이터 추출
	     
	    StringBuilder builder = new StringBuilder();
	    builder.append("클라이언트에서 수신한 문자열 [");
	    builder.append(readMessage);
	    builder.append("]");
	     
	    System.out.println(builder.toString());
	    
//	    ctx.close();

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	private ByteBuf message = null;
	private String sendMsg = "";

	public String getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;

		message = Unpooled.buffer(TimeClient.MESSAGE_SIZE);
		// 예제로 사용할 바이트 배열을 만듭니다.
		byte[] str = sendMsg.getBytes();
		// 예제 바이트 배열을 메시지에 씁니다.
		message.writeBytes(str);
	}

	// 채널이 활성화 되면 동작할 코드를 정의합니다.
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 메시지를 쓴 후 플러쉬합니다.
		System.out.println("channelActive 1");
		ctx.writeAndFlush(message);
//		ctx.write(message);
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//5.수신된 데이터를 모두 읽었을때 호출되는 이벤트 메서드
//	    ctx.close();//6.서버와 연결된 채널을 닫음
		ctx.flush();
		ctx.close();
	    //6.1 이후 데이터 송수신 채널은 닫히게 되고 클라이언트 프로그램은 종료됨
	}


}
