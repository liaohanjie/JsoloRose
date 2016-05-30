package com.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
/**
 * 消息处理
 * @author -琴兽-
 *
 */
public class ServerHandler extends SimpleChannelInboundHandler<String>{

	/**
	 * 接收消息
	 */
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {

		System.out.println("服务端消息:"+msg);
		
	}
}
