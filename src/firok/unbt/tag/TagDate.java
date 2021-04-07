package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class TagDate extends TagBase
{
	private Date value;
	public Date getValue()
	{
		return value;
	}
	public void setValue(Date value)
	{
		this.value = value;
	}

	public TagDate() { super(); }
	protected TagDate(String name, Date value)
	{
		super(name);
		this.value = value;
	}

	@Override
	public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		long time = dis.readLong();
		this.value = new Date(time);
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		long time = this.value.getTime();
		dos.writeLong(time);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagDate)) return false;
		TagDate tag = (TagDate) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.value.equals(value);
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{Date:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
