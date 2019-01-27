package nettyProj.server;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import nettyProj.client.TimeClient;


public class DiscardServerHandler extends ChannelInboundHandlerAdapter{
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		try {
			System.out.println( "server channelRead 1 " );
	        // Do something with msg
			 //2. 데이터 순신 이벤트 처리 메서드. 클라이언트로부터 데이터의 수신이 이루어졌을때 네티가 자동으로 호출하는 이벤트 메소드
		     
		    String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
		    //3. 수신된 데이터를 가지고 있는 네티의 바이트 버퍼 객체로 부터 문자열 객체를 읽어온다.
		    
		    String sendMsg = "서버에서 수신한 문자열 ["+readMessage +"]";
		    System.out.println(sendMsg);
		    /* 4.ctx는 채널 파이프라인에 대한 이벤트를 처리한다.
		     * 반드시 bytebuf 형식으로 write 할 것
		    */
		    ctx.channel().writeAndFlush(toByteBuf(sendMsg));

		}finally {
			// Discard the received data silently.
			((ByteBuf) msg).release(); // (3)
		}
    }
	
	// 채널 읽는 것을 완료했을 때 동작할 코드를 정의 합니다.
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush(); // 컨텍스트의 내용을 플러쉬합니다.
		ctx.close();
	};

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
    
	private ByteBuf toByteBuf(String temp1) {

		ByteBuf message = Unpooled.buffer(TimeClient.MESSAGE_SIZE);
		// 예제로 사용할 바이트 배열을 만듭니다.
		byte[] str = temp1.getBytes();
		// 예제 바이트 배열을 메시지에 씁니다.
		message.writeBytes(str);
		return message;
	}
	
}
