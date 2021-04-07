package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 整型数组
 */
public class TagByteArray extends TagBase
{
	private byte[] value;
	public byte[] getValue()
	{
		return value;
	}
	public byte getValue(int slot)
	{
		return this.value[slot];
	}
	public void setValue(byte... value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = new byte[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	public void setValue(int slot,byte value)
	{
		this.value[slot] = value;
	}
	public int size()
	{
		return this.value.length;
	}

	protected TagByteArray() { super(); }
	public TagByteArray(String name, byte[] value)
	{
		super(name);
		setValue(value);
	}

	@Override
	public boolean isArrayType() { return true; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		byte[] cache = new byte[length];
		dis.read(cache);
		this.value = cache;
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value.length);
		dos.write(this.value);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagByteArray)) return false;
		TagByteArray tag = (TagByteArray) obj;
		return Objects.equals(tag.getName(),this.getName()) && Arrays.equals(tag.getValue(),this.value);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("{byte[]:").append(this.getName()).append("=[");
		for(float value : this.value) str.append(value).append(',');
		return str.append("]}").toString();
	}
}
