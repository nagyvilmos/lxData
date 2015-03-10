/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataException.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2009
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
 *================================================================================
 */
package lexa.core.data.exception;

/**
 * An exception within a {@link lexa.core.data.DataSet} or {@link lexa.core.data.DataItem}
 * @author William
 * @since 2009-07
 */
public class DataException
		extends Exception
{

	/** The path where the exception occurred */
	private final String path;
	/** The key where the exception occurred */
	private final String key;

	/**
	 * Create a new exception with default text.
	 * <p>
	 * This would be equivalent to using {@code DataException("An exception has occurred")}
	 *
	 * @see #DataException(String)
	 */
	public DataException()
	{
		this("An exception has occurred");
	}

	/**
	 * Create a new exception with a message.
	 * <p>
	 * This would be equivalent to using {@code DataException(message, null)}
	 *
	 * @param message a message to describe the exception.
	 * @see #DataException(String, String)
	 */
	public DataException(String message)
	{
		this(message, null);
	}

	/**
	 * Create a new exception with a message and path.
	 * <p>
	 * This would be equivalent to using {@code DataException(message, path, null)}
	 *
	 * @param message a message to describe the exception.
	 * @param path the path within the {@link lexa.core.data.DataSet} where the exception occurred.
	 * @see #DataException(String, String, String)
	 */
	public DataException(String message, String path)
	{
		this(message, path, null);
	}

	/**
	 * Create a new exception with a message, path and key.
	 *
	 * @param message a message to describe the exception.
	 * @param path the path within the {@link lexa.core.data.DataSet} where the exception occurred.
	 * @param key the key to the {@link lexa.core.data.DataItem} where the exception occurred.
	 */
	public DataException(String message, String path, String key)
	{
		this(message, path, key, null);
	}
	/**
	 * Create a new exception with a message, path and key.
	 *
	 * @param message a message to describe the exception.
	 * @param path the path within the {@link lexa.core.data.DataSet} where the exception occurred.
	 * @param key the key to the {@link lexa.core.data.DataItem} where the exception occurred.
	 * @param throwable the cause of the exception
	 */
	public DataException(String message, String path, String key, Throwable throwable)
	{
		super(message,throwable);
		this.path = path == null || "".equals(path) ?
				null :
				path;
		this.key = key == null || "".equals(key) ?
				null :
				key;
	}

	/**
	 * Get the key of the {@link lexa.core.data.DataItem} where the exception occurred.
	 * @return The key of the exception or {@code null} if omitted.
	 */
	public String getKey()
	{
		return this.key;
	}

	/**
	 * Get the path within the {@link lexa.core.data.DataSet} where the exception occurred.
	 * @return The path of the exception or {@code null} if omitted.
	 */
	public String getPath()
	{
		return this.path;
	}

	/**
	 * Get a string to describe the exception.
	 * <p>
	 * The format of the description is "{@code <message> [key: <key>] [path: <path>]"}
	 *
	 * @return A formated string describing the exception.
	 * @see Exception#toString()
	 */
	@Override
	public String toString()
	{
		return this.getMessage() +
				(this.key == null ?
				"" :
				" [key:=" + this.key) + "]" +
				(this.path == null ?
				"" :
				" [path:=" + this.path) + "]";
	}
}
