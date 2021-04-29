package firok.unbt.tag;

import firok.unbt.exception.UnexpectedTagTypeException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 复合标签
 */
public class TagCompact extends TagBase
	implements Iterable<TagBase>, ICompactStack
{
	public TagCompact(String name)
	{
		super(name);
	}
	protected TagCompact() { super(); }

	private Map<String,TagBase> tags = new LinkedHashMap<>();

	public TagBase get(String name)
	{
		return tags.get(name);
	}
	public void set(TagBase tag)
	{
		String name = tag.getName();
		tags.put(name,tag);
	}
	public boolean has(String name)
	{
		return tags.containsKey(name);
	}
	public void remove(String name)
	{
		tags.remove(name);
	}
	public Collection<String> keys()
	{
		return tags.keySet();
	}
	public int size()
	{
		return tags.size();
	}

	// int methods
	public int getInt(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagInt) return ((TagInt) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setInt(String name,int value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagInt) ((TagInt) tag).setValue(value);
		else set(new TagInt(name,value));
	}
	public boolean hasInt(String name)
	{
		return get(name) instanceof TagInt;
	}
	public boolean hasInt(String name,int value)
	{
		TagBase tag = get(name);
		return tag instanceof TagInt && ((TagInt) tag).getValue() == value;
	}

	// long methods
	public long getLong(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagLong) return ((TagLong) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setLong(String name,long value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagLong) ((TagLong) tag).setValue(value);
		else set(new TagLong(name,value));
	}
	public boolean hasLong(String name)
	{
		return get(name) instanceof TagLong;
	}
	public boolean hasLong(String name,long value)
	{
		TagBase tag = get(name);
		return tag instanceof TagLong && ((TagLong) tag).getValue() == value;
	}

	// float methods
	public float getFloat(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagFloat) return ((TagFloat) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setFloat(String name,float value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagFloat) ((TagFloat) tag).setValue(value);
		else set(new TagFloat(name,value));
	}
	public boolean hasFloat(String name)
	{
		return get(name) instanceof TagFloat;
	}
	public boolean hasFloat(String name,float value)
	{
		TagBase tag = get(name);
		return tag instanceof TagFloat && ((TagFloat) tag).getValue() == value;
	}

	// double methods
	public double getDouble(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDouble) return ((TagDouble) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setDouble(String name,double value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDouble) ((TagDouble) tag).setValue(value);
		else set(new TagDouble(name,value));
	}
	public boolean hasDouble(String name)
	{
		return get(name) instanceof TagDouble;
	}
	public boolean hasDouble(String name,double value)
	{
		TagBase tag = get(name);
		return tag instanceof TagDouble && ((TagDouble) tag).getValue() == value;
	}

	// string methods
	public String getString(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagString) return ((TagString) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setString(String name,String value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagString) ((TagString) tag).setValue(value);
		else set(new TagString(name,value));
	}
	public boolean hasString(String name)
	{
		return get(name) instanceof TagString;
	}
	public boolean hasString(String name,String value)
	{
		TagBase tag = get(name);
		return tag instanceof TagString && ((TagString) tag).getValue().equals(value);
		// yes, tag value cannot be null, so this is safe
	}

	// BigInteger methods
	public BigInteger getBigInteger(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagBigInteger) return ((TagBigInteger) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setDouble(String name,BigInteger value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagBigInteger) ((TagBigInteger) tag).setValue(value);
		else set(new TagBigInteger(name,value));
	}
	public boolean hasBigInteger(String name)
	{
		return get(name) instanceof TagBigInteger;
	}
	public boolean hasBigInteger(String name,BigInteger value)
	{
		TagBase tag = get(name);
		return tag instanceof TagBigInteger && ((TagBigInteger) tag).getValue().compareTo(value)==0;
	}

	// BigDecimal methods
	public BigDecimal getBigDecimal(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagBigDecimal) return ((TagBigDecimal) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public void setBigDecimal(String name,BigDecimal value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagBigDecimal) ((TagBigDecimal) tag).setValue(value);
		else set(new TagBigDecimal(name,value));
	}
	public boolean hasBigDecimal(String name)
	{
		return get(name) instanceof TagBigDecimal;
	}
	public boolean hasBigDecimal(String name,BigDecimal value)
	{
		TagBase tag = get(name);
		return tag instanceof TagBigDecimal && ((TagBigDecimal) tag).getValue().compareTo(value)==0;
	}

	// Date methods
	public Date getDate(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDate) return ((TagDate) tag).getValue();
		else throw new UnexpectedTagTypeException();
	}
	public long getDateTimestamp(String name)
	{
		return getDate(name).getTime();
	}
	public void setDate(String name,Date value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDate) ((TagDate) tag).setValue(value);
		else set(new TagDate(name,value));
	}
	public void setDateTimestamp(String name,long value)
	{
		setDate(name,new Date(value));
	}
	public boolean hasDate(String name)
	{
		return get(name) instanceof TagDate;
	}
	public boolean hasDate(String name,long value)
	{
		TagBase tag = get(name);
		return tag instanceof TagDate && ((TagDate) tag).getValue().getTime() == value;
	}
	public boolean hasDate(String name,Date value)
	{
		return hasDate(name,value.getTime());
	}

	// compact methods
	public Collection<TagCompact> calcCompactStack()
	{
		Collection<TagCompact> ret = new ArrayList<>(this.tags.size() + 1);
		ret.add(this);
		for(TagBase tag : this.tags.values())
		{
			if(tag instanceof ICompactStack)
				ret.addAll(((ICompactStack) tag).calcCompactStack());
		}
		return ret;
	}
	public TagCompact getCompact(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagCompact) return (TagCompact) tag;
		else throw new UnexpectedTagTypeException();
	}
	public void setCompact(TagCompact value)
	{
		Collection<TagCompact> compactStack = value.calcCompactStack();
		if(compactStack.contains(this)) throw new IllegalArgumentException("Circular references found");
		tags.put(value.getName(),value);
	}
	public boolean hasCompact(String name)
	{
		return get(name) instanceof TagCompact;
	}

	// int[] methods
	public int[] getIntArray(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagIntArray) return ((TagIntArray)tag).getValue().clone();
		else throw new UnexpectedTagTypeException();
	}
	public void setIntArray(String name,int[] value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagIntArray) ((TagIntArray)tag).setValue(value);
		else set(new TagIntArray(name,value));
	}
	public boolean hasIntArray(String name)
	{
		return get(name) instanceof TagIntArray;
	}
	public boolean hasIntArray(String name,int[] value)
	{
		TagBase tag = get(name);
		return tag instanceof TagIntArray && Arrays.equals(((TagIntArray)tag).getValue(),value);
	}
	// long[] methods
	public long[] getLongArray(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagLongArray) return ((TagLongArray)tag).getValue().clone();
		else throw new UnexpectedTagTypeException();
	}
	public void setLongArray(String name,long[] value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagLongArray) ((TagLongArray)tag).setValue(value);
		else set(new TagLongArray(name,value));
	}
	public boolean hasLongArray(String name)
	{
		return get(name) instanceof TagLongArray;
	}
	public boolean hasLongArray(String name,long[] value)
	{
		TagBase tag = get(name);
		return tag instanceof TagLongArray && Arrays.equals(((TagLongArray)tag).getValue(),value);
	}
	// float[] methods
	public float[] getFloatArray(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagFloatArray) return ((TagFloatArray)tag).getValue().clone();
		else throw new UnexpectedTagTypeException();
	}
	public void setFloatArray(String name,float[] value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagFloatArray) ((TagFloatArray)tag).setValue(value);
		else set(new TagFloatArray(name,value));
	}
	public boolean hasFloatArray(String name)
	{
		return get(name) instanceof TagFloatArray;
	}
	public boolean hasFloatArray(String name,float[] value)
	{
		TagBase tag = get(name);
		return tag instanceof TagFloatArray && Arrays.equals(((TagFloatArray)tag).getValue(),value);
	}
	// double[] methods
	public double[] getDoubleArray(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDoubleArray) return ((TagDoubleArray)tag).getValue().clone();
		else throw new UnexpectedTagTypeException();
	}
	public void setDoubleArray(String name,double[] value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagDoubleArray) ((TagDoubleArray)tag).setValue(value);
		else set(new TagDoubleArray(name,value));
	}
	public boolean hasDoubleArray(String name)
	{
		return get(name) instanceof TagDoubleArray;
	}
	public boolean hasDoubleArray(String name,double[] value)
	{
		TagBase tag = get(name);
		return tag instanceof TagDoubleArray && Arrays.equals(((TagDoubleArray)tag).getValue(),value);
	}

	// byte[] methods
	public byte[] getByteArray(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagByteArray) return ((TagByteArray) tag).getValue().clone();
		else throw new UnexpectedTagTypeException();
	}
	public void setByteArray(String name,byte[] value)
	{
		TagBase tag = get(name);
		if(tag instanceof TagByteArray) ((TagByteArray) tag).setValue(value);
		else set(new TagByteArray(name,value));
	}
	public boolean hasByteArray(String name)
	{
		return get(name) instanceof TagByteArray;
	}
	public boolean hasByteArray(String name,byte[] value)
	{
		TagBase tag = get(name);
		return tag instanceof TagByteArray && Arrays.equals(((TagByteArray) tag).getValue(),value);
	}


	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTReadingContext context, DataInputStream dis) throws IOException
	{
		this.tags.clear();
		TagBase tag;
		while(true)
		{
			tag = UNBTFactory.read(dis,context);
			if(tag == TagEnd.INSTANCE) break;

			this.set(tag);
		}
	}

	@Override
	public void writeData(UNBTWritingContext context, DataOutputStream dos) throws IOException
	{
		for(TagBase tag : tags.values())
		{
			UNBTFactory.write(dos,context,tag);
		}
		UNBTFactory.write(dos,context,TagEnd.INSTANCE);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof TagCompact))
			return false;

		TagCompact tag2 = (TagCompact) obj;
		if(this.tags.size() != tag2.tags.size()) return false;

		for(TagBase tagInner1 : this)
		{
			String tagName = tagInner1.getName();
			TagBase tagInner2 = tag2.get(tagName);
			if(tagInner2 == null || !tagInner1.equals(tagInner2))
				return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder ret = new StringBuilder("{compact:");
		for(TagBase tag : this)
		{
			ret.append(tag.toString()).append(',');
		}
		ret.append("}");
		return ret.toString();
	}

	@Override
	public Iterator<TagBase> iterator()
	{
		return tags.values().iterator();
	}
}
