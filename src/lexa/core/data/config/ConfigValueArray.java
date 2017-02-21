/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ConfigValue.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2016-09-01   WNW 16-01       Create seperate package and classes for config
 * 2016-09-14   WNW 16-09       Add path to all config objects
 *================================================================================
 */
package lexa.core.data.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lexa.core.data.BaseValueArray;
import lexa.core.data.exception.DataException;
import lexa.core.data.DataValue;
import lexa.core.data.DataArray;

/**
 *
 * @author william
 */
public class ConfigValueArray
        extends BaseValueArray
        implements ConfigObject
{

    private final String path;
    private final List<ConfigValue> values;
    private final Set<Integer> invalidGets;
    private boolean read;

    ConfigValueArray(String path, DataArray array)
    {
        this.path = path;
        this.values = new ArrayList();
        for (DataValue value : array)
        {
            this.values.add(new ConfigValue(this.path, this.values.size(), value));
        }
        this.invalidGets = new HashSet();
        this.read = false;
    }

    @Override
    public DataArray add(int index, Object object)
    {
		throw new UnsupportedOperationException(
				"Cannot change the content of a config data array");
    }

    @Override
    public DataArray add(int index, DataValue value)
    {
		throw new UnsupportedOperationException(
				"Cannot change the content of a config data array");
    }

    @Override
    public synchronized ConfigValue get(int index)
    {
        if (index < 0 || index >= this.size())
        {
            this.invalidGets.add(index);
        }
        return this.values.get(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DataValue remove(int index)
    {
		throw new UnsupportedOperationException(
				"Cannot change the content of a config data array");
    }

    @Override
    public int size()
    {
        return this.values.size();
    }

    @Override
    public void close()
            throws DataException
    {
        if (!this.isRead())
        {
			StringBuilder cannotClose = new StringBuilder("Cannot close config");
            int index = 0;
			for (ConfigValue value : this.values)
			{
				if (!value.isRead())
				{
					cannotClose.append(", ")
							.append(index)
                            .append(" unread");
				}
                index++;
			}
            for (Integer badGet : this.invalidGets)
            {
                cannotClose.append(", ")
                        .append(badGet)
                        .append(" out of bounds");
            }
			throw new DataException(cannotClose.toString(), this.path);
        }
    }

    @Override
    public boolean isRead()
    {
        if (!this.read)
        {
            boolean checkRead = (this.invalidGets.isEmpty());
            if (!this.values.stream().noneMatch((value) -> (!value.isRead())))
            {
                checkRead = false;
            }
            this.read=checkRead;
        }
        return this.read;
    }

    @Override
    public void reset()
    {
        this.invalidGets.clear();
        this.values.stream().forEach((value) ->
        {
            value.reset();
        });
        this.read=false;
    }
    @Override
    public String getPath()
    {
        return this.path;
    }
}
