package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * 整型数组
 */
public class TagFloatArray extends TagBase
{
	private float[] value;
	public float[] getValue()
	{
		return value;
	}
	public float getValue(int slot)
	{
		return this.value[slot];
	}
	public void setValue(float... value)
	{
		if(value == null) throw new IllegalArgumentException("Tag value cannot be null");
		this.value = new float[value.length];
		System.arraycopy(value, 0, this.value, 0, value.length);
	}
	public void setValue(int slot,float value)
	{
		this.value[slot] = value;
	}
	public int size()
	{
		return this.value.length;
	}

	protected TagFloatArray() { super(); }
	public TagFloatArray(String name, float[] value)
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
		float[] value = new float[length];
		for (int i = 0; i < length; i++)
		{
			value[i] = dis.readFloat();
		}
		this.value = value;
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		dos.writeInt(this.value.length);
		for(int i=0;i<this.value.length;i++)
		{
			dos.writeFloat(this.value[i]);
		}
	}
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagFloatArray)) return false;
		TagFloatArray tag = (TagFloatArray) obj;
		return Objects.equals(tag.getName(),this.getName()) && Arrays.equals(tag.getValue(),this.value);
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder("{float[]:").append(this.getName()).append("=[");
		for(float value : this.value) str.append(value).append(',');
		return str.append("]}").toString();
	}
}
