/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataWriter.java
 *--------------------------------------------------------------------------------
 * Author:  Indent.java
 * Created: October 2014
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-01-28   WNW 16-01       Update javadoc.
 *================================================================================
 */
package lexa.core.data.io;

/**
 * Provide indentation for the writer; the indent is formatted as tabs.
 */
class Indent
{
    /** level of the indent */
	private final int level;
    /** the indent characters as tabs*/
	private final char[] prefix;

	/**
	 * Create a 0 level indentation - 0 characters deep.
	 */
	Indent()
	{
		this(0);
	}

	/**
	 * Create an Indent.
	 *
	 * @param   level
	 *      The level of the indent.
	 */
	private Indent(int level)
	{
		this.level = level;
		this.prefix = new char[level];
		for (int c = 0;
				c < this.prefix.length;
				c++)
		{
			prefix[c] = '\t';
		}
	}

    /**
     * get the indent prefix.
     * <p>The prefix is a stream of tab characters equal to the indent level.
     * @return the indent prefix
     */
	char[] getPrefix()
	{
		return this.prefix;
	}

	/**
	 * Create an indent at the next level.
	 *
	 * @return
	 *      The next level of Indent
	 */
	Indent next()
	{
		return new Indent(this.level + 1);
	}
}
