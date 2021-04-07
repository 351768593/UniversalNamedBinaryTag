package firok.unbt;

/**
 * 通用带名二进制标签
 */
public class UniversalNamedBinaryTag
{
	public static final String name = "Universal Named Binary Tag";
	public static final String shortName = "unbt";
	public static final String author = "Firok";
	public static final String version = "0.2.0";

	public static final int TYPE_END            = 0b0001_1111;

	public static final int TYPE_INT            = 0b0000_0001;
	public static final int TYPE_LONG           = 0b0000_0010;
	public static final int TYPE_FLOAT          = 0b0000_0011;
	public static final int TYPE_DOUBLE         = 0b0000_0100;
	public static final int TYPE_BOOLEAN        = 0b0000_0101;
	@Deprecated // 并不准备实现
	public static final int TYPE_BYTE           = 0b0000_0110;
	public static final int TYPE_STRING         = 0b0000_1000;

	public static final int TYPE_BIG_INTEGER    = 0b0000_1001;
	public static final int TYPE_BIG_DECIMAL    = 0b0000_1010;
	public static final int TYPE_UTIL_DATE      = 0b0000_1100;

	public static final int TYPE_COMPACT        = 0b0010_0000;

	public static final int TYPE_INT_ARRAY      = 0b0100_0001;
	public static final int TYPE_LONG_ARRAY     = 0b0100_0010;
	public static final int TYPE_FLOAT_ARRAY    = 0b0100_0011;
	public static final int TYPE_DOUBLE_ARRAY   = 0b0100_0100;
	public static final int TYPE_BOOLEAN_ARRAY  = 0b0100_0101;
	public static final int TYPE_BYTE_ARRAY     = 0b0100_0110;
	public static final int TYPE_STRING_ARRAY   = 0b0100_1000;

}
