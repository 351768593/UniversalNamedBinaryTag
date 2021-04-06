package firok.unbt.tag;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

class UNBTFactoryTest
{
	static final File dir = new File("./test_data");
	static
	{
		dir.mkdirs();
	}
	public static File getFile(String name)
	{
		return new File(dir,name+".unbt");
	}

	private static void write(File file,TagBase tag) throws IOException
	{
		try(
				OutputStream os = new FileOutputStream(file);
				DataOutputStream dos = new DataOutputStream(os);
		) {
			UNBTFactory.write(dos,new UNBTFactory.WritingContext(),tag);
			dos.flush();
		}
	}
	private static TagBase read(File file) throws IOException
	{
		try(
				InputStream is = new FileInputStream(file);
				DataInputStream dis = new DataInputStream(is);
		){
			return UNBTFactory.read(dis);
		}
	}

	private static void TagIntTest(String name,int value) throws IOException
	{
		File file = getFile(name);
		TagInt tag1 = new TagInt(name,value);
		write(file,tag1);
		TagInt tag2 = (TagInt) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void TagIntTest() throws IOException
	{
		TagIntTest("int1",123);
		TagIntTest("int2",1234567890);
		TagIntTest("int3",987654321);
		TagIntTest("int4",0);
		TagIntTest("int5",-123);
		TagIntTest("int6",-362819312);
		TagIntTest("int7",-999999);
	}

	private static void TagLongTest(String name,long value) throws IOException
	{
		File file = getFile(name);
		TagLong tag1 = new TagLong(name,value);
		write(file,tag1);
		TagLong tag2 = (TagLong) read(file);
		Assertions.assertEquals(tag1, tag2);
	}

	@Test
	public void TagLongTest() throws IOException
	{
		TagLongTest("long1",123);
		TagLongTest("long2",Long.MIN_VALUE);
		TagLongTest("long3",9876543210L);
		TagLongTest("long4",0);
		TagLongTest("long5",-123);
		TagLongTest("long6",-92938161432L);
		TagLongTest("long7",Long.MAX_VALUE);
	}

	private static void TagStringTest(String name,String value) throws IOException
	{
		File file = getFile(name);
		TagString tag1 = new TagString(name,value);
		write(file,tag1);
		TagString tag2 = (TagString) read(file);
		Assertions.assertEquals(tag1, tag2);
	}
	@Test
	public void TagStringTest() throws IOException
	{
		TagStringTest("str1","");
		TagStringTest("str2","123 abc");
		TagStringTest("str3","你我他 123");
		TagStringTest("str4","2168930729143712945628137219-3472198456123839213-721954612y343890217319asbdjsabdowadiaw2-我我我");
		TagStringTest("str5","ejwoandkwadwapdnkfbnc\uD83D\uDC9A💢☪🕉🕉💥☯💨✝🥠🥠🧈🍚🦪🍣🥨");
	}

	private static void TagCompactTest(String name) throws IOException
	{
		File file = getFile(name);
		Random rand = new Random();
		TagCompact tag1 = new TagCompact(name);
		for(int i=0;i<5;i++)
		{
			TagBase tag2add;
			String tagName2add = "tag"+i;
			switch(rand.nextInt(4))
			{
				case 0:
					tag2add = new TagInt(tagName2add,rand.nextInt());
					break;
				case 1:
					tag2add = new TagLong(tagName2add,rand.nextLong());
					break;
				case 2:
					tag2add = new TagString(tagName2add,"value~~"+rand.nextLong());
					break;
				default: case 3:
					TagCompact tagNew = new TagCompact(tagName2add);
					tagNew.set(new TagInt("inner"+i+1,rand.nextInt()));
					tagNew.set(new TagLong("inner"+i+2,rand.nextLong()));
					tagNew.set(new TagString("inner"+i+3,"value~~"+rand.nextInt()));
					tag2add = tagNew;
					break;
			}

			tag1.set(tag2add);
		}
		write(file,tag1);

		TagCompact tag2 = (TagCompact) read(file);
		Assertions.assertEquals(tag1,tag2);
	}

	@Test
	public void TagCompactTest() throws IOException
	{
		TagCompactTest("compact1");
		TagCompactTest("compact2");
		TagCompactTest("compact3");
		TagCompactTest("compact4");
		TagCompactTest("compact5");
	}
}