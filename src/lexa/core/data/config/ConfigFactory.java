/*
 *==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ConfigFactory.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: may 2016
 *==============================================================================
 */
package lexa.core.data.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import lexa.core.data.BaseFactory;
import lexa.core.data.DataFactory;
import lexa.core.data.DataSet;
import lexa.core.data.io.DataReader;

/**
 * Factory for the config data sets
 * @author william
 * @since 2017-05
 */
public class ConfigFactory
        extends BaseFactory
{
    /**
     * Handle to the  instance of the {@link ConfigFactory} for the root
     * of a configuration tree.
     */
    public static ConfigFactory factory = new ConfigFactory(null);

    private final static String ROOT_NAME = "[root]";

    private final ConfigFactory parent;
    private final String path;
    private Integer index;
    private ConfigFactory(String path)
    {
        this(null, path, null);
    } // keep it single, keep it simple
    private ConfigFactory(ConfigFactory parent, String path, Integer index)
    {
        this.parent = parent;
        this.path = path;
        this.index = index;
    } // keep it single, keep it simple

    @Override
    public boolean checkFactory(DataFactory factory)
    {
        if (super.checkFactory(factory))
        {
            return true;
        }
        if (factory == null || !this.getClass().isAssignableFrom(factory.getClass()))
        {
            return false;
        }
        // finally it's a ConfigFactory, we need to check if it's a child of this,
        // if so it's fine
        return ((ConfigFactory)factory).parent == this;
    }
    @Override
    public ConfigDataArray getDataArray()
    {
        return new ConfigDataArray(this);
    }

    @Override
    public ConfigDataItem getDataItem(String key, Object value)
    {
        return new ConfigDataItem(this, key, this.getDataValue(value));
    }

    @Override
    public ConfigDataSet getDataSet()
    {
        // just an empty data set
        return new ConfigDataSet(this, null);
    }

    @Override
    public ConfigDataValue getDataValue(Object object)
    {
        return new ConfigDataValue(this, object);
    }

    /**
     * get the path in the configuration to an object for this factory
     * @return the object's path
     */
    public String getPath()
    {
        if (this.parent == null ||
                (this.parent.path == null && this.parent.index == null))
        {
            if (this.path == null)
            {
                // the root
                return ConfigFactory.ROOT_NAME;
            }
            // a node of the root
            return this.path;
        }
        if (this.path != null)
        {
            return this.parent.getPath() + '.' + this.path;
        }
        if (this.index != null)
        {
            return this.parent.getPath() +':' + Integer.toString(this.index);
        }
        return ConfigFactory.ROOT_NAME;
    }

    ConfigFactory getChild(String path)
    {
        return new ConfigFactory(this,path,null);
    }

    ConfigFactory getChild(int index)
    {
        return new ConfigFactory(this, null, index);
    }

    void setIndex(int index)
    {
        this.index = index;
    }

    @Override
    public String toString()
    {
        return this.getClass().getSimpleName() + '.' + this.getPath();
    }

    public static ConfigDataSet loadFromFile(String fileName)
            throws FileNotFoundException,
                IOException
    {
        return ConfigFactory.loadFromFile(
                new File(fileName)
        );
    }
    public static ConfigDataSet loadFromFile(File file)
            throws FileNotFoundException,
                IOException
    {
        ConfigFactory configFactory = new ConfigFactory(
                '[' + file.getName() + ']'
        );
        DataReader reader = new DataReader(file);
        DataSet data = reader.read();
        reader.close();
        return (ConfigDataSet)configFactory.convert(data);
    }
}
