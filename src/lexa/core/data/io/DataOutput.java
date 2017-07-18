/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * DataOutput.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *==============================================================================
 */
package lexa.core.data.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import lexa.core.data.DataArray;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.DataValue;
import lexa.core.data.exception.DataException;

/**
 * This writes things in a binary form rather than text
 * @author william
 * #TODO Check this still works!
 */
public class DataOutput
        implements WriteDataSet
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
     * Create a binary writer for datasets
     * @param   file
     *          the file for the data.
     * @throws  FileNotFoundException
     *          when the file cannot be found
     */
    public DataOutput(File file)
            throws FileNotFoundException
	{
		this(
                new DataOutputStream(
                        new FileOutputStream(file)
                )
        );
	}
    /**
     * Write a {@link DataSet} to the stream
     * When completed the stream is flushed to ensure all data is written.
     * @param data the {@link DataSet} to be written
     * @throws IOException when a problem occurs writing
     * @throws DataException when a problem occurs serialising
     */
    @Override
    public void write(DataSet data)
			throws IOException, DataException
	{
		this.writeSet(data);
		this.flush();
	}

    private void writeArray(DataArray array)
        throws IOException,
            DataException
    {
        this.stream.writeInt(array.size());
        for (DataValue value : array)
        {
            this.writeValue(value);
        }
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
        this.writeValue(item.getValue());
    }

    private void writeValue(DataValue value)
			throws IOException, DataException
	{
		this.stream.writeChar(value.getType().getTypeChar());
		switch (value.getType())
		{
            case ARRAY :
            {
                this.writeArray(value.getArray());
                return;
            }
			case BOOLEAN :
			{
				this.stream.writeBoolean(value.getBoolean());
				return;
			}
			case DATA_SET :
			{
				this.writeSet(value.getDataSet());
				return;
			}
			case DATE :
			{
				this.stream.writeLong(value.getDate().getTime());
				return;
			}
			case DOUBLE :
			{
				this.stream.writeDouble(value.getDouble());
				return;
			}
			case INTEGER :
			{
				this.stream.writeInt(value.getInteger());
				return;
			}
			case LONG :
			{
				this.stream.writeLong(value.getLong());
				return;
			}
			case NULL :
			{
				// nothing doing
				return;
			}
			case STRING :
			{
				this.stream.writeUTF(value.getString());
				return;
			}
		}
        throw new DataException ("Cannot encode object " + value.toString(), null, null);
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
