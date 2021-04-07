package firok.unbt.tag;

import firok.unbt.UniversalNamedBinaryTag;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UNBTReadingContext
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
	public UNBTReadingContext(boolean sync)
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
	public UNBTReadingContext(Map<Integer,String> mappingTagName)
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
			case UniversalNamedBinaryTag.TYPE_END: return TagEnd.INSTANCE;

			case UniversalNamedBinaryTag.TYPE_INT: return new TagInt();
			case UniversalNamedBinaryTag.TYPE_LONG: return new TagLong();
			case UniversalNamedBinaryTag.TYPE_FLOAT: return new TagFloat();
			case UniversalNamedBinaryTag.TYPE_DOUBLE: return new TagDouble();
			case UniversalNamedBinaryTag.TYPE_STRING: return new TagString();

			case UniversalNamedBinaryTag.TYPE_BIG_INTEGER: return new TagBigInteger();
			case UniversalNamedBinaryTag.TYPE_BIG_DECIMAL: return new TagBigDecimal();
			case UniversalNamedBinaryTag.TYPE_UTIL_DATE: return new TagDate();

			case UniversalNamedBinaryTag.TYPE_COMPACT: return new TagCompact();

			case UniversalNamedBinaryTag.TYPE_INT_ARRAY: return new TagIntArray();
			case UniversalNamedBinaryTag.TYPE_LONG_ARRAY: return new TagLongArray();
			case UniversalNamedBinaryTag.TYPE_FLOAT_ARRAY: return new TagFloatArray();
			case UniversalNamedBinaryTag.TYPE_DOUBLE_ARRAY: return new TagDoubleArray();

//			case UniversalNamedBinaryTag.TYPE_BOOLEAN_ARRAY: return new TagBooleanArray();
			case UniversalNamedBinaryTag.TYPE_BYTE_ARRAY: return new TagByteArray();
			default: return null;
		}
	}

	public final String computeString(byte[] bytes)
	{
		String ret = new String(bytes, StandardCharsets.UTF_8);
		if(internTagName.contains(ret))
			ret = ret.intern();
		return ret;
	}
}
