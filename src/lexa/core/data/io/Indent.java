/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data.io;

/**
 * Provide indentation or the witter.
 * An indent of {@link INDENT_SIZE} characters per level.
 */
class Indent
{

	private final int level;
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
