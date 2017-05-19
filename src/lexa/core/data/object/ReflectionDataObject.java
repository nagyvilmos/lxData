/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * ReflectionDataObject.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: December 2016
 *==============================================================================
 */
package lexa.core.data.object;

import java.util.HashMap;
import java.util.Map;
import lexa.core.data.DataSet;

/**
 * Support for {@link DataObject} via reflection.
 * Use {@link DataObjectField} to annotate fields for serialisation
 * @author william
 * @since 2016-12
 */
public class ReflectionDataObject
        implements DataObject
{
    private static final Map<String,DataObjectLoader> loaders =
            new HashMap<>();

    private static DataObjectLoader loader(String className)
    {
        if (!ReflectionDataObject.loaders.containsKey(className))
        {
            ReflectionDataObject.loaders.put(className, new DataObjectLoader(className));
        }
        return ReflectionDataObject.loaders.get(className);
    }

    private final DataObjectLoader loader;

    /**
     * ReflectionDataObject
     */
    protected ReflectionDataObject()
    {
        this.loader = ReflectionDataObject.loader(this.getClass().getCanonicalName());
    }

    @Override
    public DataSet toData()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void fromData(DataSet data)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class DataObjectLoader
    {

        private DataObjectLoader(String className)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
