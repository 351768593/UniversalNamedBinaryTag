package firok.unbt.exception;

/**
 * 无法识别的标签类型异常
 */
public class UnexpectedTagTypeException
	extends RuntimeException
{
	public UnexpectedTagTypeException()
	{
		this("Invalid tag type");
	}

	public UnexpectedTagTypeException(String message)
	{
		super(message);
	}

	public UnexpectedTagTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnexpectedTagTypeException(Throwable cause)
	{
		super(cause);
	}
}
