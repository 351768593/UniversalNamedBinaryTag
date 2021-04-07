package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Objects;

public class TagBigInteger extends TagBase
{
	private BigInteger value;
	public BigInteger getValue()
	{
		return value;
	}
	public void setValue(BigInteger value)
	{
		this.value = value;
	}

	public TagBigInteger() { super(); }
	protected TagBigInteger(String name, BigInteger value)
	{
		super(name);
		this.value = value;
	}

	@Override
	public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		byte[] cache = new byte[length];
		dis.read(cache);
		this.value = new BigInteger(cache);
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		byte[] cache = this.value.toByteArray();
		dos.writeInt(cache.length);
		dos.write(cache);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagBigInteger)) return false;
		TagBigInteger tag = (TagBigInteger) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.value.compareTo(value)==0;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{BigInteger:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
