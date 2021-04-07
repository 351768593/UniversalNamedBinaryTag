package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 整型数组
 */
public class TagIntArray extends TagBase
{
	private int[] value;
	public int[] getValue()
	{
		return value;
	}
	public int getValue(int slot)
	{
		return this.value[slot];
	}
	public void setValue(int... value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = new int[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	public void setValue(int slot,int value)
	{
		this.value[slot] = value;
	}
	public int size()
	{
		return this.value.length;
	}

	protected TagIntArray() { super(); }
	public TagIntArray(String name,int[] value)
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
		int[] value = new int[length];
		for (int i = 0; i < length; i++)
		{
			value[i] = dis.readInt();
		}
		this.value = value;
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value.length);
		for(int i=0;i<this.value.length;i++)
		{
			dos.writeInt(this.value[i]);
		}
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagIntArray)) return false;
		TagIntArray tag = (TagIntArray) obj;
		return Objects.equals(tag.getName(),this.getName()) && Arrays.equals(tag.getValue(),this.value);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("{int[]:").append(this.getName()).append("=[");
		for(int value : this.value) str.append(value).append(',');
		return str.append("]}").toString();
	}
}
