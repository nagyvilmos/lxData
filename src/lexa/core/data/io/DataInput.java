/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataInput.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2015-03-30	WNW 2015-03		Add the concept of an array.
 * 2016-01-27	WNW	16-01       Remove the superfluose clone() method.
 * 2016-01-28   WNW 16-01       Update javadoc.
 *================================================================================
 */
package lexa.core.data.io;

import java.io.*;
import java.util.Date;
import lexa.core.data.*;
import lexa.core.data.exception.DataException;

/**
 * Read {@link DataItem} and {@link DataSet} objects from an input stream.
 * <p>A {@code DataSet} is read as a series of {@code DataItem} objects.
 * <p>A {@code DataItem} is read as the name followed by the value.
 *
 * @author William
 * @since 2013-02
 * #todo Check this still works!
 */
public class DataInput
        implements ReadDataSet
{
	/** an input stream to read the data from */
	private final DataInputStream stream;

	/**
	Create an input reader
	@param stream the stream to read the data from
	*/
	public DataInput(DataInputStream stream)
	{
		this.stream = stream;
	}

	/**
	 * Create an input reader
	 * @param   file
     *          the file to read the data from
     * @throws  FileNotFoundException
     *          when the file cannot be found
     */
	public DataInput(File file) throws FileNotFoundException
	{
		this(
                new DataInputStream(
                        new FileInputStream(file)
                )
        );
	}
	/**
	Read a data set from the input stream
	@return a data set
	@throws IOException  when an IO problem occurs
	@throws DataException when the data cannot be decoded.
	*/
    @Override
	public DataSet read()
			throws IOException, DataException
	{
		int items = this.stream.readInt();
		DataSet data = new ArrayDataSet();
		for (int i = 0;
				i < items;
				i++)
		{
			data.put(this.readItem());
		}
		return data;
	}

    private DataArray readArray() throws IOException,
            DataException
    {
        DataArray va = new ArrayDataArray();
        long count = this.stream.readInt();
        for (int i = 0; i < count; i++)
        {
            va.add(this.readValue());
        }
        return va;
    }

	private DataItem readItem()
			throws IOException, DataException
	{
		String key = this.stream.readUTF();
		Object value = this.readValue();

		return new ArrayDataItem(key, value);
	}
	private Object readValue()
			throws IOException, DataException
	{
		DataType vt = DataType.toType(this.stream.readChar());
		switch (vt)
		{
			case ARRAY :
			{
                return this.readArray();
			}
			case BOOLEAN :
			{
				return this.stream.readBoolean();
			}
			case DATA_SET :
			{
				return this.read();
			}
			case DATE :
			{
				return new Date(this.stream.readLong());
			}
			case DOUBLE :
			{
				return this.stream.readDouble();
			}
			case INTEGER :
			{
				return this.stream.readInt();
			}
			case LONG :
			{
				return this.stream.readLong();
			}
            case NULL :
			{
				return null;
			}
			case STRING :
			{
				return this.stream.readUTF();
			}
		}
        throw new DataException ("Cannot decode object " + vt.getTypeChar());
	}
	/**
	Close the stream
	@throws IOException when unable to close the stream
	*/
    @Override
	public void close() throws IOException
	{
		this.stream.close();
	}
}
