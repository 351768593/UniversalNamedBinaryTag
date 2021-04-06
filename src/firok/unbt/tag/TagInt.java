package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TagInt extends TagBase
{
	public static final int TYPE = 0b0000_0001;

	int value;
	public int getValue()
	{
		return value;
	}
	public void setValue(int value)
	{
		this.value = value;
	}

	protected TagInt(String name,int value)
	{
		super(name);
		this.value = value;
	}
	protected TagInt() { super(); }

	@Override public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTFactory.ReadingContext context, DataInputStream dis)
			throws IOException
	{
		this.value = dis.readInt();
	}

	@Override
	public void writeData(UNBTFactory.WritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagInt)) return false;
		TagInt tag = (TagInt) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.getValue() == value;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{long:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
