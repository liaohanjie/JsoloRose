package com.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {

	public static void main(String[] args)  {
		
		ServerBootstrap b = new ServerBootstrap();
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new StringDecoder());
                	 ch.pipeline().addLast(new StringEncoder());
                     ch.pipeline().addLast(new GameHandler());
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true) // (6)
             .childOption(ChannelOption.TCP_NODELAY, true); // (6)

            ChannelFuture f = b.bind(10102); // (7)
            
            f.sync();
             
            System.out.println("start");

            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        
	}

}