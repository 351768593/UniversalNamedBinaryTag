package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TagInt extends TagBase
{
	int value;
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}

	public TagInt(String name,int value)
	{
		super(name);
		this.value = value;
	}
	protected TagInt() { super(); }

	@Override public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis)
			throws IOException
	{
		this.value = dis.readInt();
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagInt)) return false;
		TagInt tag = (TagInt) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.value == value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{long:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
