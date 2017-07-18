/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ReadDataSet.java (lxData)
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
public interface ReadDataSet
        extends AutoCloseable
{
    /**
     * Read the input into a {@link DataSet}.
     *
     * @return  A {@link DataSet} read from the input, null if empty.
     * @throws  IOException
     *          When an IO error occurs writing to the output.
     * @throws  DataException
     *          When a data exception occurs
     */
    DataSet read()
        throws IOException,
            DataException;
}
