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
@Named(value = "diagramElement")
@SessionScoped
public class DiagramElement extends org.primefaces.model.diagram.Element
{
    private BlockModel block;

    /**
     * Creates a new instance of DiagramElement
     */
    public DiagramElement()
    {
    }

    public DiagramElement(BlockModel block)
    {
        super(block);
        this.block = block;
    }

    public BlockModel getBlock()
    {
        return block;
    }
    
    
}
