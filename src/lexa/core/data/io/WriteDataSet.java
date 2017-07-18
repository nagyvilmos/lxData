/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * WriteDataSet.java (lxData)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2017
 *==============================================================================
 */

package lexa.core.data.io;

import java.io.IOException;
import lexa.core.data.DataSet;
import lexa.core.data.exception.DataException;

/**
 *
 * @author  willaimnw
 * @since   2017-07
 */
public interface WriteDataSet extends AutoCloseable
{
    /**
     * Write a {@link lexa.core.data.DataSet} to the file.
     * <p>
     * Each {@link lexa.core.data.DataSet} is written to the output
     * using {@link #write(lexa.core.data.DataItem)}.
     *
     * @param   data
     *          The {@link lexa.core.data.DataSet} to be written to the output.
     * @throws  IOException
     *          When an IO error occurs writing to the output.
     * @throws  DataException
     *          When a data exception occurs
     */
    void write(DataSet data)
        throws IOException,
            DataException;
}
