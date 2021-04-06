package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TagLong extends TagBase
{
	public static final int TYPE = 0b0000_0010;

	private long value;
	public long getValue()
	{
		return value;
	}
	public void setValue(long value)
	{
		this.value = value;
	}

	public TagLong(String name, long value)
	{
		super(name);
		this.value = value;
	}
	public TagLong() { super(); }

	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTFactory.ReadingContext context, DataInputStream dis)
			throws IOException
	{
		this.value = dis.readLong();
	}

	@Override
	public void writeData(UNBTFactory.WritingContext context, DataOutputStream dos)
			throws IOException
	{
		dos.writeLong(this.value);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagLong)) return false;
		TagLong tag = (TagLong) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.getValue() == value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{int:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
