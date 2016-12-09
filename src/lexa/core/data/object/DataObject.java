/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.data.object;

import lexa.core.data.DataSet;

/**
 *
 * @author william
 */
public interface DataObject
{
    
    DataSet toData();
    void fromData(DataSet data);
    
}
