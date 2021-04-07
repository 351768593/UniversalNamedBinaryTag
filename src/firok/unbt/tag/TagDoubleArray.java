package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 整型数组
 */
public class TagDoubleArray extends TagBase
{
	private double[] value;
	public double[] getValue()
	{
		return value;
	}
	public double getValue(int slot)
	{
		return this.value[slot];
	}
	public void setValue(double... value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = new double[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	public void setValue(int slot,double value)
	{
		this.value[slot] = value;
	}
	public int size()
	{
		return this.value.length;
	}

	protected TagDoubleArray() { super(); }
	public TagDoubleArray(String name, double[] value)
	{
		super(name);
		setValue(value);
	}

	@Override
	public boolean isArrayType() { return true; }

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		int length = dis.readInt();
		double[] value = new double[length];
		for (int i = 0; i < length; i++)
		{
			value[i] = dis.readDouble();
		}
		this.value = value;
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value.length);
		for(int i=0;i<this.value.length;i++)
		{
			dos.writeDouble(this.value[i]);
		}
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagDoubleArray)) return false;
		TagDoubleArray tag = (TagDoubleArray) obj;
		return Objects.equals(tag.getName(),this.getName()) && Arrays.equals(tag.getValue(),this.value);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("{double[]:").append(this.getName()).append("=[");
		for(double value : this.value) str.append(value).append(',');
		return str.append("]}").toString();
	}
}
