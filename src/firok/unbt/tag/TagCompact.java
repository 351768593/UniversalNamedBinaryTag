package firok.unbt.tag;

import firok.unbt.exception.UnexpectedTagTypeException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 复合标签
 */
public class TagCompact extends TagBase
	implements Iterable<TagBase>
{
	public static final int TYPE = 0b0010_0000;

	public TagCompact(String name)
	{
		super(name);
	}
	public TagCompact() { super(); }

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
		else throw new UnexpectedTagTypeException("Invalid tag type");
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
		else throw new UnexpectedTagTypeException("Invalid tag type");
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
		return get(name) instanceof TagLong && ((TagLong) tag).getValue() == value;
	}

	// string methods
	public String getString(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagString) return ((TagString) tag).getValue();
		else throw new UnexpectedTagTypeException("Invalid tag type");
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

	// compact methods
	/**
	 * @return 当前标签栈
	 */
	private Collection<TagCompact> calcCompactStack()
	{
		Collection<TagCompact> ret = new ArrayList<>(this.tags.size() + 1);
		ret.add(this);
		for(TagBase tag : this.tags.values())
		{
			if(tag instanceof TagCompact)
				ret.addAll(((TagCompact) tag).calcCompactStack());
		}
		return ret;
	}
	public TagCompact getCompact(String name)
	{
		TagBase tag = get(name);
		if(tag instanceof TagCompact) return (TagCompact) tag;
		else throw new UnexpectedTagTypeException("Invalid tag type");
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

	@Override
	public boolean isArrayType()
	{
		return false;
	}

	@Override
	public void readData(UNBTFactory.ReadingContext context, DataInputStream dis) throws IOException
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
	public void writeData(UNBTFactory.WritingContext context, DataOutputStream dos) throws IOException
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
