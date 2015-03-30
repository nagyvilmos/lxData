/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexa.core.data.io;

import java.io.*;
import java.util.Date;
import lexa.core.data.*;
import lexa.core.data.exception.DataException;

/**
 *
 * @author william
 */
public class DataInput
{
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

	private final DataInputStream stream;
	public DataInput(DataInputStream stream)
	{
		this.stream = stream;
	}
	
	public SimpleDataSet read()
			throws IOException, DataException
	{
		int items = this.stream.readInt();
		SimpleDataSet data = new SimpleDataSet();
		for (int i = 0;
				i < items;
				i++)
		{
			data.put(this.readItem());
		}
		return data;
	}

	private SimpleDataItem readItem()
			throws IOException, DataException
	{
		String key = this.stream.readUTF();
		ValueType vt = ValueType.toType(this.stream.readChar());
		Object value = null;
		switch (vt)
		{
			case BOOLEAN :
			{
				value = this.stream.readBoolean();
				break;
			}
			case DATA_SET :
			{
				value = this.read();
				break;
			}
			case DATE :
			{
				value = new Date(this.stream.readLong());
				break;
			}
			case DOUBLE :
			{
				value = this.stream.readDouble();
				break;
			}
			case INTEGER :
			{
				value = this.stream.readInt();
				break;
			}
			case NULL :
			{
				// nothing doing
				break;
			}
			case STRING :
			{
				value = this.stream.readUTF();
				break;
			}
			default :
			{
				throw new DataException ("Cannot decode object " + key + vt.getTypeChar());
			}
		}
		return new SimpleDataItem(key, value);
	}
	public void close() throws IOException
	{
		this.stream.close();
	}
}
