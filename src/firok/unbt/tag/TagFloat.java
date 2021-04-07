package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * 浮点数类型标签
 */
public class TagFloat extends TagBase
{
	private float value;
	public float getValue()
	{
		return value;
	}
	public void setValue(float value)
	{
		this.value = value;
	}

	protected TagFloat() { super(); }
	public TagFloat(String name,float value)
	{
		super(name);
		this.value = value;
	}

	@Override
	public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		this.value = dis.readFloat();
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeFloat(this.value);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagFloat)) return false;
		TagFloat tag = (TagFloat) obj;
		return Objects.equals(tag.getName(),this.getName()) &&
				(
						(tag.value == value) ||
						(Float.isNaN(tag.value) && Float.isNaN(value))
				);
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{float:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
