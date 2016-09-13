/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigObject.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-09-01   WNW 16-01       Create seperate package and classes for config
 *================================================================================
 */
package lexa.core.data.config;

import lexa.core.data.exception.DataException;

/**
 * A data object that is used for configuration.
 * 
 * @author william
 * @since 2016-09
 */
public interface ConfigObject
{
    /**
     * Close the object having read all of the required configuration
     * @throws DataException when all of the configuration has not been read.
     */
    void close() throws DataException;
    
    /**
     * Indicates if the object has been read.
     * @return  {@code true} if the object has been read, 
     *          otherwise returns {@code false}.
     */
    boolean isRead();
    
    /**
     * Reset the read flag of the object
     * <br>
     * Resets the flag so that {@link ConfigObject#isRead() isRead()}
     * returns {@code false}
     */
    void reset();
}
