package com.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

import com.server.GameHandler;

public class Client {

	public static void main(String[] args) throws InterruptedException {

		Bootstrap b = new Bootstrap();

		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			b.group(workerGroup)
					.channel(NioSocketChannel.class) // (3)
					.handler(new ChannelInitializer<SocketChannel>() { // (4)
								@Override
								public void initChannel(SocketChannel ch)
										throws Exception {
									ch.pipeline().addLast(new StringDecoder());
									ch.pipeline().addLast(new ClientHandler());
								}
							});

			 //发起异步链接操作
		     ChannelFuture connect = b.connect("127.0.0.1", 10102);
		     
		     connect.channel().closeFuture().sync();
		     
		     System.out.println("guanbi");
		} finally {
			workerGroup.shutdownGracefully();
		}

	}
}
