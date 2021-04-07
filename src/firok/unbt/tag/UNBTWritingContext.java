package firok.unbt.tag;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import firok.unbt.UniversalNamedBinaryTag;

public class UNBTWritingContext
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
		if(classTag == TagEnd.class) return UniversalNamedBinaryTag.TYPE_END;

		else if(classTag == TagInt.class) return UniversalNamedBinaryTag.TYPE_INT;
		else if(classTag == TagLong.class) return UniversalNamedBinaryTag.TYPE_LONG;
		else if(classTag == TagFloat.class) return UniversalNamedBinaryTag.TYPE_FLOAT;
		else if(classTag == TagDouble.class) return UniversalNamedBinaryTag.TYPE_DOUBLE;
		else if(classTag == TagString.class) return UniversalNamedBinaryTag.TYPE_STRING;

		else if(classTag == TagBigInteger.class) return UniversalNamedBinaryTag.TYPE_BIG_INTEGER;
		else if(classTag == TagBigDecimal.class) return UniversalNamedBinaryTag.TYPE_BIG_DECIMAL;
		else if(classTag == TagDate.class) return UniversalNamedBinaryTag.TYPE_UTIL_DATE;

		else if(classTag == TagCompact.class) return UniversalNamedBinaryTag.TYPE_COMPACT;

		else if(classTag == TagIntArray.class) return UniversalNamedBinaryTag.TYPE_INT_ARRAY;
		else if(classTag == TagLongArray.class) return UniversalNamedBinaryTag.TYPE_LONG_ARRAY;
		else if(classTag == TagFloatArray.class) return UniversalNamedBinaryTag.TYPE_FLOAT_ARRAY;
		else if(classTag == TagDoubleArray.class) return UniversalNamedBinaryTag.TYPE_DOUBLE_ARRAY;

//		else if(classTag == TagBooleanArray.class) return UniversalNamedBinaryTag.TYPE_BOOLEAN_ARRAY;
		else if(classTag == TagByteArray.class) return UniversalNamedBinaryTag.TYPE_BYTE_ARRAY;
		else return -1; // that shouldn't be
	}
}