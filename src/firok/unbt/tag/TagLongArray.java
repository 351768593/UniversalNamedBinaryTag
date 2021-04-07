package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class TagLongArray extends TagBase
{
	private long[] value;
	public long[] getValue()
	{
		return value;
	}
	public long getValue(int slot)
	{
		return this.value[slot];
	}
	public void setValue(long... value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = new long[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	public void setValue(int slot,long value)
	{
		this.value[slot] = value;
	}
	public int size()
	{
		return this.value.length;
	}

	protected TagLongArray() { super(); }
	public TagLongArray(String name,long[] value)
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
		long[] value = new long[length];
		for (int i = 0; i < length; i++)
		{
			value[i] = dis.readLong();
		}
		this.value = value;
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value.length);
		for(int i=0;i<this.value.length;i++)
		{
			dos.writeLong(this.value[i]);
		}
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagLongArray)) return false;
		TagLongArray tag = (TagLongArray) obj;
		return Objects.equals(tag.getName(),this.getName()) && Arrays.equals(tag.getValue(),this.value);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("{long[]:").append(this.getName()).append("=[");
		for(long value : this.value) str.append(value).append(',');
		return str.append("]}").toString();
	}
}
