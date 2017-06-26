/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * DataFactoryItem.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: May 2017
 *==============================================================================
 */
package lexa.core.data;

/**
 * Interface for any item that uses a {@link DataFactory};
 * @author william
 */
public interface DataFactoryItem
{

    /**
     * Get the factory used to create this type of class
     * @return the factory used to create this type of {@link DataSet}
     */
    public DataFactory factory();
}
