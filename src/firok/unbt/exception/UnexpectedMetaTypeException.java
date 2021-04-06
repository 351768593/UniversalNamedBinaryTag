package firok.unbt.exception;

/**
 * 无法识别的标签元类型异常
 */
public class UnexpectedMetaTypeException
	extends RuntimeException
{
	public UnexpectedMetaTypeException()
	{
	}

	public UnexpectedMetaTypeException(String message)
	{
		super(message);
	}

	public UnexpectedMetaTypeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnexpectedMetaTypeException(Throwable cause)
	{
		super(cause);
	}
}
