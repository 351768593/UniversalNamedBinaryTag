package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class TagBigDecimal extends TagBase
{
	private BigDecimal value;
	public BigDecimal getValue()
	{
		return value;
	}
	public void setValue(BigDecimal value)
	{
		this.value = value;
	}

	public TagBigDecimal() { super(); }
	protected TagBigDecimal(String name, BigDecimal value)
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
		String text = new String(cache,StandardCharsets.UTF_8);
		this.value = new BigDecimal(text);
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		String text = this.value.toString();
		byte[] cache = text.getBytes(StandardCharsets.UTF_8);
		dos.writeInt(cache.length);
		dos.write(cache);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagBigDecimal)) return false;
		TagBigDecimal tag = (TagBigDecimal) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.value.compareTo(value)==0;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{BigDecimal:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
