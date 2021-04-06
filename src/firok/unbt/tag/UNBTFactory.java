package firok.unbt.tag;

import firok.unbt.exception.UnexpectedMetaTypeException;
import firok.unbt.exception.UnexpectedTagTypeException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UNBTFactory
{
	public static class ReadingContext
	{
		/**
		 * 标签名称相关映射
		 */
		final Map<Integer,String> mappingTagName;

		/**
		 * 需要置入缓存池的字符串
		 */
		final Collection<String> internTagName;

		/**
		 * @param sync 是否采用同步模式
		 */
		public ReadingContext(boolean sync)
		{
			if(sync)
			{
				mappingTagName = new ConcurrentHashMap<>();
			}
			else
			{
				mappingTagName = new HashMap<>();
			}

			internTagName = Collections.emptyList();
		}

		/**
		 * @param mappingTagName 给定名称映射
		 */
		public ReadingContext(Map<Integer,String> mappingTagName)
		{
			this.mappingTagName = mappingTagName;

			internTagName = Collections.emptyList();
		}

		/**
		 * @param tagType 读取到的标签类型数据
		 * @return 构建的标签结果
		 * @implNote 如果需要扩展更多类型标签, 需要在子类覆写此方法
		 */
		public TagBase constructTag(final int tagType)
		{
			switch (tagType)
			{
				case TagEnd.TYPE: return TagEnd.INSTANCE;

				case TagInt.TYPE: return new TagInt();
				case TagLong.TYPE: return new TagLong();
				case TagString.TYPE: return new TagString();
				case TagCompact.TYPE: return new TagCompact();
				default: return null;
			}
		}

		public final String computeString(byte[] bytes)
		{
			String ret = new String(bytes,StandardCharsets.UTF_8);
			if(internTagName.contains(ret))
				ret = ret.intern();
			return ret;
		}
	}
	public static class WritingContext
	{
		private int ref = -1;
		private Map<String,Integer> mappingNameRef = new HashMap<>();
		private Map<String,byte[]> mappingNameCache = new HashMap<>();

		public synchronized int calcStringRef(String tagName)
		{
			Integer ret = mappingNameRef.get(tagName);
			if(ret == null)
			{
				mappingNameRef.put(tagName, ref);
				mappingNameCache.put(tagName,tagName.getBytes(StandardCharsets.UTF_8));
				ret = ref;
				ref--;
			}
			return ret;
		}

		public synchronized byte[] calcStringBytes(String tagName)
		{
			return mappingNameCache.get(tagName);
		}

		/**
		 * @param tag 标签数据
		 * @return 解构的标签类型数据
		 * @implNote 如果需要扩展更多类型标签, 需要在子类覆写此方法
		 */
		public int deconstructTag(TagBase tag)
		{
			Class<? extends TagBase> classTag = tag.getClass();
			if(classTag == TagEnd.class) return TagEnd.TYPE;
			else if(classTag == TagInt.class) return TagInt.TYPE;
			else if(classTag == TagLong.class) return TagLong.TYPE;
			else if(classTag == TagString.class) return TagString.TYPE;
			else if(classTag == TagCompact.class) return TagCompact.TYPE;
			else return -1; // that shouldn't be
		}
	}

	public static TagBase read(DataInputStream dis) throws IOException
	{
		return read(dis, new ReadingContext(false));
	}
	public static TagBase read(DataInputStream dis, ReadingContext context) throws IOException
	{
		/*
			0 - ready for tag header,
				next byte should tell us tag type.
				0b0100_0001 data tag
				0b0100_0010 meta tag: string ref
					the value will be put in reading context
				0b0100_0000 end tag
					this type is only used to stop reading a compact tag.

			1 - now we are reading meta tag: string ref.
				first there should be 4 bytes to indicate the ref number
				second there should be 4 bytes to indicate how much bytes we should read for the string
				finally we need to read the data bytes of that string

			10 - now we are reading data tag,
				and next byte should be tag type.
				that value will be as a key to find a real tag type

			* */
		int status = 0;

		WHILE_STREAM: while(dis.available() > 0)
		{
			SWITCH_STATUS: switch (status)
			{
				case 0: // reading header
				{
					byte headerByte = dis.readByte();

					SWITCH_HEADER: switch (headerByte)
					{
						case 0b0100_0001: // data tag
						{
							status = 10;
							break SWITCH_HEADER;
						}
						case 0b0100_0010: // meta tag: string ref
						{
							int tagNameRef = dis.readInt();
							int tagNameLen = dis.readInt();
							if(tagNameRef >= 0)
							{
								throw new IllegalArgumentException("Ref of tag name must be lesser than zero");
							}
							else if(tagNameLen <= 0)
							{
								throw new IllegalArgumentException("Length of tag name must be greater than zero");
							}
							else // tagNameRef < 0 && tagNameLen > 0
							{
								// read tag name
								byte[] cache = new byte[tagNameLen];
								dis.read(cache);
								String tagName = context.computeString(cache);

								context.mappingTagName.put(tagNameRef,tagName);
							}

							break SWITCH_HEADER;
						}
						case 0b0100_0000: // end tag
						{
							break SWITCH_HEADER;
						}
						default:
						{
							// how could it be?
							throw new UnexpectedMetaTypeException();
						}
					}
					break SWITCH_STATUS;
				}
				case 10: // reading data tag
				{
					// figure out tag type and do original initialization
					int tagType = dis.readInt();
					TagBase tag = context.constructTag(tagType);
					if(tag == TagEnd.INSTANCE) return tag;
					if(tag == null)
					{
						throw new UnexpectedTagTypeException("Invalid tag type");
					}

					// read tag name
					int tagNameRelatedBytes = dis.readInt();
					if(tagNameRelatedBytes == 0) // why you want this?
					{
						tag.setName("");
					}
					else if(tagNameRelatedBytes > 0) // directly read tag data
					{
						byte[] cache = new byte[tagNameRelatedBytes];
						dis.read(cache);

						String tagName = context.computeString(cache);
						tag.setName(tagName);
					}
					else // < 0 // find a ref
					{
						String tagName = context.mappingTagName.get(tagNameRelatedBytes);
						if(tagName == null)
						{
							throw new IllegalArgumentException("Invalid tag name ref");
						}
						tag.setName(tagName);
					}

					// finally, read the data tag needs
					tag.readData(context,dis);

					// yes, yes, yes
					return tag;
				}
			}
		}
		return null;
	}

	public static void write(DataOutputStream dos, WritingContext context, TagBase tag) throws IOException
	{
		// get tag type
		int tagType = context.deconstructTag(tag);
		if(tagType <= 0) throw new UnexpectedTagTypeException("Invalid tag type");

		// only need is to write a tag type for TagEnd
		if(tag == TagEnd.INSTANCE)
		{
			dos.writeByte(0b0100_0001);
			dos.writeInt(tagType);
			return;
		}

		// write tag name, or name ref
		String tagName = tag.getName();
		int tagNameRef = context.calcStringRef(tagName);
		byte[] tagNameBytes = context.calcStringBytes(tagName);
		// if it is a name ref, write it first
		if(tagNameRef < 0)
		{
			dos.writeByte(0b0100_0010);
			dos.writeInt(tagNameRef);
			dos.writeInt(tagNameBytes.length);
			dos.write(tagNameBytes);
		}

		// tag header
		dos.writeByte(0b0100_0001);
		dos.writeInt(tagType);

		// write tag name
		dos.writeInt(tagNameRef);
		// = 0 // length 0 ?
		if(tagNameRef > 0) // > 0 // write directly
		{
			byte[] cache = context.calcStringBytes(tagName);
			dos.write(cache);
		}

		tag.writeData(context, dos);
	}
}
