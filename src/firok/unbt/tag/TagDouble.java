package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;

public class TagDouble extends TagBase
{
	private double value;
	public double getValue()
	{
		return value;
	}
	public void setValue(double value)
	{
		this.value = value;
	}

	public TagDouble() { super(); }
	protected TagDouble(String name,double value)
	{
		super(name);
		this.value = value;
	}

	@Override
	public boolean isArrayType() { return false; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		this.value = dis.readDouble();
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeDouble(this.value);
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagDouble)) return false;
		TagDouble tag = (TagDouble) obj;
		return Objects.equals(tag.getName(),this.getName()) &&
				(
						(tag.value == value) ||
						(Double.isNaN(tag.value) && Double.isNaN(value))
				);
	}

	@Override
	public String toString()
	{
		return new StringBuilder("{double:").append(this.getName()).append('=').append(this.value).append("}").toString();
	}
}
