package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 字符串类型
 */
public class TagString extends TagBase
{
	public static final int TYPE = 0b0000_0100;
	private String value;

	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = value;
	}

	protected TagString(String name, String value)
	{
		super(name);
		this.value = value;
	}
	protected TagString()
	{
		super();
	}

	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTFactory.ReadingContext context, DataInputStream dis) throws IOException
	{
		int valueRelatedBytes = dis.readInt();
		if(valueRelatedBytes == 0) this.value = "";
		else if(valueRelatedBytes > 0) // read string
		{
			byte[] cache = new byte[valueRelatedBytes];
			dis.read(cache);
			String value = context.computeString(cache);
			this.value = value;
		}
		else // < 0 // find ref
		{
			String value = context.mappingTagName.get(valueRelatedBytes);
			if(value == null) throw new IllegalArgumentException("Invalid tag name ref");
			this.value = value;
		}
	}

	@Override
	public void writeData(UNBTFactory.WritingContext context, DataOutputStream dos) throws IOException
	{
		// currently we will only write value directly
		byte[] cache = this.value.getBytes(StandardCharsets.UTF_8);
		dos.writeInt(cache.length);
		if(cache.length > 0) dos.write(cache);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagString)) return false;
		TagString tag = (TagString) obj;
		return Objects.equals(tag.getName(),this.getName()) && tag.getValue().equals(value);
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{string:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
