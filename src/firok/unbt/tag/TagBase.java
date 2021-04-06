package firok.unbt.tag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 标签类型基类
 * <br>
 * 每一种标签都需要有一个处于<em>正数int空间</em>内的TYPE, <br>
 * 用于序列化和反序列化时的类型判断 <br>
 * <br>
 * 我们强烈不推荐使用位于<code>0b0_0000_0000</code>至<code>0b1_0000_0000</code>空间内的数字<br>
 * 这部分数字是预留给库内置类型的
 */
public abstract class TagBase
{
	private String name;

	/**
	 * @param name 标签名称
	 */
	protected TagBase(String name)
	{
		this.setName(name);
	}

	/**
	 * 此构造器不会初始化标签名称, 默认预留给工厂方法使用
	 */
	protected TagBase() { }

	final void setName(String name)
	{
		if(name == null || name.length() == 0) throw new IllegalArgumentException("Tag name cannot be empty");
		if(name.startsWith("##")) throw new IllegalArgumentException("Tag name cannot start with \"##\"");
		this.name = name;
	}
	public final String getName()
	{
		if(name == null)
			throw new UnsupportedOperationException("The name of this tag has not been initialized");

		return this.name;
	}

	/**
	 * @return 当前标签是否为数组类型
	 */
	public abstract boolean isArrayType();

	/**
	 * 子类应当实现这个方法, 用于从反序列化输入流中获取自身的值
	 * @param context 反序列化上下文
	 * @param dis 反序列化输入流
	 * @throws IOException 反序列化错误
	 */
	public abstract void readData(UNBTFactory.ReadingContext context, DataInputStream dis) throws IOException;

	/**
	 * 子类应当实现这个方法, 用于向序列化输出流中输出自身的值
	 * @param context 序列化上下文
	 * @param dos 序列化输出流
	 * @throws IOException 序列化错误
	 */
	public abstract void writeData(UNBTFactory.WritingContext context, DataOutputStream dos) throws IOException;
	/**
	 * 这个接口是对通用序列化过程的封装
	 * @param context 序列化上下文
	 * @param dos 序列化输出流
	 * @throws IOException 序列化错误
	 */
	public void write(UNBTFactory.WritingContext context, DataOutputStream dos, boolean useRef)
			throws IOException
	{
		if(useRef)
		{
			// yes, this ref should be lesser than zero
			int tagNameRef = context.calcStringRef(name);
			dos.writeInt(tagNameRef);
		}
		else
		{
			byte[] tagNameBytes = name.getBytes(StandardCharsets.UTF_8);
			dos.writeInt(tagNameBytes.length);
			dos.write(tagNameBytes);
		}
		this.writeData(context, dos);
	}

	@Override
	public final int hashCode()
	{
		return name == null ? 1 : name.hashCode();
	}
}
