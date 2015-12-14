package com.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class GameHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void messageReceived(ChannelHandlerContext arg0, String arg1)
			throws Exception { 
		System.out.println(arg1);
		
		
		arg0.channel().writeAndFlush("flash");
		arg0.writeAndFlush("hi");
		
	}

}
