package firok.unbt.tag;

import firok.unbt.exception.UnexpectedMetaTypeException;
import firok.unbt.exception.UnexpectedTagTypeException;

import java.io.*;

public class UNBTFactory
{
	public static TagBase read(DataInputStream dis) throws IOException
	{
		return read(dis, new UNBTReadingContext(false));
	}

	/**
	 * @param dis 输入流
	 * @param context 反序列化上下文
	 * @return 标签数据, 如果流中只有元数据则返回null
	 * @throws IOException 读取发生错误
	 */
	public static TagBase read(DataInputStream dis, UNBTReadingContext context) throws IOException
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

	/**
	 * 将一个标签写入至输出流
	 * @param dos 输出流
	 * @param context 序列化上下文
	 * @param tag 本次需要序列化的标签数据
	 * @throws IOException 序列化发生错误
	 */
	public static void write(DataOutputStream dos, UNBTWritingContext context, TagBase tag) throws IOException
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

	public static void write(File file,TagBase tag) throws IOException
	{
		writePlain(file, tag);
	}
	private static void writePlain(File file,TagBase tag) throws IOException
	{
		try(
				OutputStream os = new FileOutputStream(file);
				DataOutputStream dos = new DataOutputStream(os);
		) {
			UNBTFactory.write(dos,new UNBTWritingContext(),tag);
			dos.flush();
		}
	}

	public static TagBase read(File file) throws IOException
	{
		return readPlain(file);
	}
	public static TagBase readPlain(File file) throws IOException
	{
		try(
				InputStream is = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(is);
		){
			return UNBTFactory.read(dis);
		}
	}
}
