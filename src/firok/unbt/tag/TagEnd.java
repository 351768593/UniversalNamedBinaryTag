package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * 终止标记
 */
public final class TagEnd extends TagBase
{
	public static final int TYPE = 0b0001_1111;
	public static final int VALUE = 0b0101_0101;
	private TagEnd() { super(); }
	public static final TagEnd INSTANCE = new TagEnd();

	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTFactory.ReadingContext context, DataInputStream dis) throws IOException
	{
		byte value = dis.readByte();
		if(value != VALUE) throw new IllegalArgumentException("Invalid tag end");
	}

	@Override
	public void writeData(UNBTFactory.WritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeByte(VALUE);
	}
}
