/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * DataOutput.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *==============================================================================
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
 * #TODO Check this still works!
 */
public class DataOutput
{
	private final DataOutputStream stream;

    /**
     * Create a binary writer for datasets
     * @param stream the binary stream for the data.
     */
    public DataOutput(DataOutputStream stream)
	{
		this.stream = stream;
	}

    /**
     * Write a {@link DataSet} to the stream
     * When completed the stream is flushed to ensure all data is written.
     * @param data the {@link DataSet} to be written
     * @throws IOException when a problem occurs writing
     * @throws DataException when a problem occurs serialising
     */
    public void write(DataSet data)
			throws IOException, DataException
	{
		this.writeSet(data);
		this.flush();
	}

	/**
     * Write a {@link DataSet} to the stream
     * This is the internal method that does not flash the stream
     * @param data the {@link DataSet} to be written
     * @throws IOException when a problem occurs writing
     * @throws DataException when a problem occurs serialising
     */
    private void writeSet(DataSet data)
			throws IOException, DataException
	{
		this.stream.writeInt(data.size());
		for (DataItem item : data)
		{
			this.writeItem(item);
		}
	}

	/**
     * Write a {@link DataItem} to the stream
     * This is the internal method that does not flash the stream
     * @param item the {@link DataItem} to be written
     * @throws IOException when a problem occurs writing
     * @throws DataException when a problem occurs serialising
     */
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

    /**
     * Flush the stream
     * @throws IOException when a problem occurs flushing
     */
    public void flush() throws IOException
	{
		this.stream.flush();
	}

    /**
     * Close the stream
     * @throws IOException when a problem occurs closing
     */
    public void close() throws IOException
	{
		this.stream.close();
	}
}
