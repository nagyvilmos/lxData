/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataWriter.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2015-03-05	WNW 2015-03		Add the concept of an array.
 *								Add ARRAY and LONG types.
 * 2016-01-28   WNW 16-01       Update javadoc.
 * 2016-08-13   WNW 2016-08     Fix use of DatatException path and key.
 * 2016-08-20   WNW 16-08       add printFormatted method to DataSet via DataWriter
 *================================================================================
 */
package lexa.core.data.io;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.Value;
import lexa.core.data.ValueArray;
import lexa.core.data.formatting.CombinedFormat;

/**
 * Write {@link lexa.core.data.DataItem} and {@link lexa.core.data.DataSet}
 * objects to an output stream.
 * <p>A {@code lexa.core.data.DataSet} is written as a series of
 * {@code lexa.core.data.DataItem} objects.
 * <p>A {@code lexa.core.data.DataItem} is written as the name followed by the 
 * formatted value; with the toString depends on the data type.
 * <p>The toString of the output is:
 * <pre>
 # comments can be added at any point and each
 # item is written thus:
 # name t value
 example {
 # Boolean -
    isMale ? true
 # SimpleDataSet -
 ` *    subBlock {
       item - simple text.
    }
 # Date -
    dateWriten @ 2013-02-27 12:00:25.3789Z
 # Double -
    weight $ 75.3
 # Integer -
    age % 42
 # String [without reserved characters] -
    name - William Norman-Walker
 # String [with reserved chracters] -
    longText \
 \# this will all be read as\
 it \@ contains all the special chracters \\ escaped\
 \{ so the parser can read them \}.\
 all for \$15.00 \@ \-50\% discount
 }
 # String [quote delimited]
    quotedText "
      The writer will not produce this output,
      but data files created externally can use this.
      To include a quote use "" and this toString allows

      blank lines too.
      NB Indentation and new lines are preserved.
    "

 # [example ended]
 </pre>
 *
 * @author William
 * @since 2013-02
 */
public class DataWriter
{

	/** The writer used to output the content */
	private final BufferedWriter bufferedWriter;
	private final CombinedFormat formatter;

	/**
	 * Create a new writer to output directly to a file.
	 *
	 * @param   file
	 *      The file for directing the output.
	 * @throws  FileNotFoundException
	 *      When the file to be opened cannot be accessed.
	 */
	public DataWriter(File file)
			throws FileNotFoundException
	{
		this(new FileOutputStream(file));
	}

	/**
	 * Create a new writer to output to a buffer.
	 *
	 * @param   writer
	 *      The buffer for writing data into.
	 */
	public DataWriter(BufferedWriter writer)
	{
		this.bufferedWriter = writer;
		this.formatter = new CombinedFormat();
	}

    /**
     * Create a writer around an output stream
     * @param stream output stream to receive the data
     */
    public DataWriter(OutputStream stream)
    {
		this(
                new BufferedWriter(
                    new OutputStreamWriter(
                        new DataOutputStream(
                            stream
                        )
                    )
                )
        );
    }

	/**
	 * Close the output.
	 * <p>
	 * Once the output has been closed, no more data may be written.
	 *
	 * @throws  IOException
	 *       When an IO error occurs closing the output.
	 */
	public void close()
			throws IOException
	{
		this.bufferedWriter.close();
	}

	/**
	 * Write a comment to the output.
	 * <p>
	 * Comments are written to the output prefixing each line with the # character.
	 * When read back using a {@link DataReader} comments are ignored.
	 *
	 * @param   comment
	 *       The comment to be written to the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	public void comment(String comment)
			throws IOException
	{
		this.bufferedWriter.write("# ");
		this.bufferedWriter.write(comment.replaceAll("\n", "\n# "));
		this.bufferedWriter.newLine();
		this.bufferedWriter.flush();
	}

	/**
	 * Write a {@link lexa.core.data.DataSet} to the file.
	 * <p>
	 * Each {@link lexa.core.data.DataSet} is written to the output 
     * using {@link #write(lexa.core.data.DataItem)}.
	 *
	 * @param   data
	 *       The {@link lexa.core.data.DataSet} to be written to the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	public void write(DataSet data)
			throws IOException
	{
		this.write(data, new Indent());
		this.bufferedWriter.newLine();
		this.bufferedWriter.flush();
	}

	/**
	 * Performs the actual writing.
	 * This private method includes the indentation and
	 * is also called from {@link DataWriter#write(lexa.core.data.DataItem, lexa.core.data.io.Indent) }.
	 *
	 * @param   data
	 *       The {@link lexa.core.data.DataSet} to be written to the output.
	 * @param   indent
	 *       The {@link Indent} to prefix the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	private void write(DataSet data, Indent indent)
			throws IOException
	{
		for (DataItem item : data)
		{
			this.write(item, indent);
		}
		this.bufferedWriter.flush();
	}

	/**
	 * Write a {@link DataItem} to the output.
	 *
	 * @param   item
	 *       The {@link DataItem} to be written to the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	public void write(DataItem item)
			throws IOException
	{
		this.write(item, new Indent());
	}

	/**
	 * Performs the actual writing.
	 * This private method includes the indentation.
	 *
	 * @param   item
	 *       The {@link lexa.core.data.DataItem} to be written to the output.
	 * @param   indent
	 *       The {@link Indent} to prefix the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	private void write(DataItem item, Indent indent)
			throws IOException
	{
		this.bufferedWriter.write(indent.getPrefix());
		this.bufferedWriter.write(item.getKey());
		this.bufferedWriter.write(' ');
		this.write(item.getValue(), indent);
		this.bufferedWriter.newLine();
	}
	
	/**
	 * Performs the actual writing.
	 * This private method includes the indentation.
	 *
	 * @param   value
	 *       The {@link lexa.core.data.Value} to be written to the output.
	 * @param   indent
	 *       The {@link Indent} to prefix the output.
	 * @throws  IOException
	 *       When an IO error occurs writing to the output.
	 */
	private void write(Value value, Indent indent)
			throws IOException
	{
		switch (value.getType())
		{
			case NULL:
			{
				break;
			}
			case DATA_SET:
			{
				this.bufferedWriter.write('{');
				this.bufferedWriter.newLine();
				this.write(value.getDataSet(), indent.next());
				this.bufferedWriter.write(indent.getPrefix());
				this.bufferedWriter.write('}');
				break;
			}
			case ARRAY:
				this.bufferedWriter.write('[');
				this.bufferedWriter.newLine();
				ValueArray arr = value.getArray();
				int i;
				Indent ai = indent.next();
				for (i = 0; i < arr.size(); i++)
				{
					this.bufferedWriter.write(ai.getPrefix());
					this.write(arr.get(i), ai);
					this.bufferedWriter.newLine();
				}
				this.bufferedWriter.write(indent.getPrefix());
				this.bufferedWriter.write(']');
				break;
			case BOOLEAN:
			{
				this.bufferedWriter.write("? ");
				this.bufferedWriter.write(this.formatter.booleanFormat.toString(value.getBoolean()));
				break;
			}
			case DOUBLE:
			{
				this.bufferedWriter.write("$ ");
				this.bufferedWriter.write(this.formatter.doubleFormat.toString(value.getDouble()));
				break;
			}
			case DATE:
			{
				this.bufferedWriter.write("@ ");
				this.bufferedWriter.write(this.formatter.dateFormat.toString(value.getDate()));
				break;
			}
			case INTEGER:
			{
				this.bufferedWriter.write("% ");
				this.bufferedWriter.write(this.formatter.integerFormat.toString(value.getInteger()));
				break;
			}
			case LONG:
			{
				this.bufferedWriter.write("= ");
				this.bufferedWriter.write(this.formatter.longFormat.toString(value.getLong()));
				break;
			}
			default:
			{
				// escape special characters such as - \" { } ? $ @ % \\ \n
				String str = value.getObject()
						.toString();
				String out = this.formatter.stringFormat.toString(value.getString());
				if (out.equals(str))
				{
					this.bufferedWriter.write("- ");
					this.bufferedWriter.write(str);
				}
				else
				{
					this.bufferedWriter.write("\\");
					this.bufferedWriter.newLine();
					this.bufferedWriter.write(out);
				}
			}
		}
	}

}
