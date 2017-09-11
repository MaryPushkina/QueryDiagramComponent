/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Мария
 */
@Named(value = "connectionModel")
@SessionScoped
public class ConnectionModel implements Serializable
{
    BlockModel startBlockModel;
    BlockModel endBlockModel;

    /**
     * Creates a new instance of ConnectionModel
     */
    public ConnectionModel()
    {
    }

    public ConnectionModel(BlockModel startBlockModel, BlockModel endBlockModel)
    {
        this.startBlockModel = startBlockModel;
        this.endBlockModel = endBlockModel;
    }

    public BlockModel getStartBlockModel()
    {
        return startBlockModel;
    }

    public BlockModel getEndBlockModel()
    {
        return endBlockModel;
    }
    
    public void connectionRendering()
    {}
}
