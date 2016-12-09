/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data.object;

import java.util.HashMap;
import java.util.Map;
import lexa.core.data.DataSet;

/**
 *
 * use the DataObjectField to annotate fields for data
 * @author william
 */
public class ReflectionDataObject
        implements DataObject
{
    private static final Map<String,DataObjectLoader> loaders =
            new HashMap();

    private static DataObjectLoader loader(String className)
    {
        if (!ReflectionDataObject.loaders.containsKey(className))
        {
            ReflectionDataObject.loaders.put(className, new DataObjectLoader(className));
        }
        return ReflectionDataObject.loaders.get(className);
    }

    private final DataObjectLoader loader;

    public ReflectionDataObject()
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

    private class DataObjectLoader
    {

        private DataObjectLoader(String className)
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
