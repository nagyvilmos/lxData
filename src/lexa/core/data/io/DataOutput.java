/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataOutput.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-08-13   WNW 2016-08     Fix use of DatatException path and key.
 *================================================================================
 */
package lexa.core.data.io;

import java.io.DataOutputStream;
import java.io.IOException;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.exception.DataException;

/**
 * This writes things in a binary form rather than text
 * @author william
 * @todo Check this still works!
 */
public class DataOutput
{
	private final DataOutputStream stream;
	public DataOutput(DataOutputStream stream)
	{
		this.stream = stream;
	}
	
	public void write(DataSet data)
			throws IOException, DataException
	{
		this.writeSet(data);
		this.flush();
	}
	private void writeSet(DataSet data)
			throws IOException, DataException
	{
		this.stream.writeInt(data.size());
		for (DataItem item : data)
		{
			this.writeItem(item);
		}
	}
	
	private void writeItem(DataItem item)
			throws IOException, DataException
	{
		this.stream.writeUTF(item.getKey());
		this.stream.writeChar(item.getType().getTypeChar());
		switch (item.getType())
		{
			case BOOLEAN :
			{
				this.stream.writeBoolean(item.getBoolean());
				break;
			}
			case DATA_SET :
			{
				this.writeSet(item.getDataSet());
				break;
			}
			case DATE :
			{
				this.stream.writeLong(item.getDate().getTime());
				break;
			}
			case DOUBLE :
			{
				this.stream.writeDouble(item.getDouble());
				break;
			}
			case INTEGER :
			{
				this.stream.writeInt(item.getInteger());
				break;
			}
			case NULL :
			{
				// nothing doing
				break;
			}
			case STRING :
			{
				this.stream.writeUTF(item.getString());
				break;
			}

			default :
			{
				throw new DataException ("Cannot encode object " + item.toString(), null, item.getKey());
			}
		}
	}
	public void flush() throws IOException
	{
		this.stream.flush();
	}
	public void close() throws IOException
	{
		this.stream.close();
	}
}
