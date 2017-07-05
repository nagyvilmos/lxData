/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ConfigDataValue.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2016
 *==============================================================================
 */
package lexa.core.data.config;

import java.util.HashSet;
import java.util.Set;
import lexa.core.data.BaseDataArray;
import lexa.core.data.exception.DataException;
import lexa.core.data.DataValue;
import lexa.core.data.DataArray;

/**
 *
 * @author william
 */
public class ConfigDataArray
        extends BaseDataArray
        implements ConfigObject
{
    private final Set<Integer> invalidGets;
    private boolean read;

    ConfigDataArray(ConfigFactory factory)
    {
        super(factory);
        this.invalidGets = new HashSet<>();
        this.read = false;
    }

    ConfigDataArray(ConfigFactory factory, DataArray array)
    {
        this(factory);
        super.addAll(array);
    }

    @Override
    public synchronized ConfigDataValue get(int index)
    {
        if (index < 0 || index >= this.size())
        {
            this.invalidGets.add(index);
        }
        return (ConfigDataValue)super.get(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ConfigFactory configFactory()
    {
        return (ConfigFactory)this.factory();
    }

    @Override
    public DataArray add(int index, DataValue value)
    {
        super.add(index, this.configFactory().getChild(index).convert(value));
        // move any subsequent indexes forward
        for (int i = index+1;
                i < this.size();
                i++)
        {
            this.get(i).configFactory().setIndex(i);
        }
        return this;
    }

    @Override
    public void close()
            throws DataException
    {
        if (this.isRead())
        {
            return;
        }
        StringBuilder cannotClose = new StringBuilder("Cannot close config");
        int index = 0;
        for (DataValue value : this)
        {
            if (!((ConfigDataValue)value).isRead())
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
        throw new DataException(cannotClose.toString(), this.getPath());
    }

    @Override
    public boolean isRead()
    {
        if (!this.read)
        {
            boolean checkRead = (this.invalidGets.isEmpty());
            if (checkRead)
            {
                for (DataValue value : this)
                {
                    if (!((ConfigDataValue)value).isRead())
                    {
                        checkRead = false;
                        break;
                    }
                }
            }
            this.read=checkRead;
        }
        return this.read;
    }

    @Override
    public void reset()
    {
        for (DataValue value : this)
        {
            ((ConfigDataValue)value).reset();
        }
        this.invalidGets.clear();
        this.read=false;
    }

    public DataValue remove(int index)
    {
        DataValue removed = super.remove(index);
        // update the indexes on the factories.
        for (int i = index;
                i < this.size();
                i++)
        {
            this.get(i).configFactory().setIndex(i);
        }
        return removed;
    }

    @Override
    public String getPath()
    {
        return this.configFactory().getPath();
    }
}
