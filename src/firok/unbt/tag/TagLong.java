package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TagLong extends TagBase
{
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
	protected TagLong() { super(); }

	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis)
			throws IOException
	{
		this.value = dis.readLong();
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos)
			throws IOException
	{
		dos.writeLong(this.value);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagLong)) return false;
		TagLong tag = (TagLong) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.value == value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{int:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
