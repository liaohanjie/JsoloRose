package com.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
/**
 * 连接池
 * @author -琴兽-
 *
 */
public class ClientPool {

	/**
	 * 会话队列
	 */
	private BlockingQueue<Channel> channels = new LinkedBlockingDeque<>();
	
	/**
	 * 会话关闭future集合
	 */
	private BlockingQueue<ChannelFuture> closeFutures = new LinkedBlockingDeque<>();
	
	
	/**
	 * 获取一个Channel
	 * @return
	 * @throws InterruptedException
	 */
	public Channel nextChannels() throws InterruptedException{
		return channels.poll(5, TimeUnit.SECONDS);
	}
	
	/**
	 * 返回channel
	 * @param channel
	 * @throws InterruptedException
	 */
	public void giveBackChannel(Channel channel) throws InterruptedException{
		channels.put(channel);
	}
	
	
	/**
	 * 加入关闭future
	 * @param channelFuture
	 * @throws InterruptedException 
	 */
	public void addCloseFuture(ChannelFuture channelFuture) throws InterruptedException{
		closeFutures.put(channelFuture);
	}
	
	/**
	 * 等待关闭
	 * @throws InterruptedException 
	 */
	public void sync() throws InterruptedException{
		ChannelFuture channelFuture;
		while((channelFuture=closeFutures.poll()) != null){
			channelFuture.sync();
		}
	}
	
	
	/**
	 * 写数据
	 * @param msg
	 */
	public void write(Object msg){
		try {
			//获取channel
			Channel channel = nextChannels();
			//确保连接没有断开
			if(channel != null && channel.isActive()){
				//写数据
				channel.writeAndFlush(msg);
				//归还
				giveBackChannel(channel);
			}else{
				throw new RuntimeException("get channel time out!!!");
			}
		} catch (Exception e) {
			throw new RuntimeException("write  fail !!!");
		}
	}
	
}
