package org.tont.core.netty;

import java.nio.ByteOrder;

import org.tont.proto.GameMsgEntity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class RevMsgDecoder extends LengthFieldBasedFrameDecoder{

	/**
	 * @param byteOrder
	 * @param maxFrameLength
	 *            �ֽ���󳤶�,���ڴ˳������׳��쳣
	 * @param lengthFieldOffset
	 *            ��ʼ���㳤��λ��,����ʹ��0������õ��ʼ
	 * @param lengthFieldLength
	 *            �������������ֽ���
	 * @param lengthAdjustment
	 *            ���Ȳ���,��������������ʹ��2���ֽ�.��Ҫ��ԭ�����ȼ����2
	 * @param initialBytesToStrip
	 *            ��ʼ���㳤����Ҫ�������ֽ���
	 * @param failFast
	 */
	public RevMsgDecoder(ByteOrder byteOrder, int maxFrameLength,
			int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip, boolean failFast) {
		super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength,
				lengthAdjustment, initialBytesToStrip, failFast);
	}
	
	public RevMsgDecoder () {
		this(ByteOrder.BIG_ENDIAN, 100000, 0, 4, 2, 4, true);
	}
	
	/**
	 * ���ݹ��췽���Զ�����ճ��,���.Ȼ����ô�decode
	 * */
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {

		ByteBuf frame = (ByteBuf) super.decode(ctx, byteBuf);
		if (frame == null) {
			return null;
		}

		short msgCode = frame.readShort();// �ȶ�ȡ�����ֽ�������
		
		GameMsgEntity msg = new GameMsgEntity();
		if (msgCode > 200) {	//��ȡpid��token
			int pid = frame.readInt();
			String token = frame.readBytes(64).toString();
			msg.setPid(pid);
			msg.setToken(token);
		}
		
		byte[] data = new byte[frame.readableBytes()];// ��������Ϊʵ������
		frame.readBytes(data);
		
		msg.setMsgCode(msgCode);
		msg.setData(data);
		
		frame.release();
		return msg;
	}

}
