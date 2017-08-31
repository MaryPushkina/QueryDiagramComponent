/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.endpoint.DotEndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;

/**
 *
 * @author Мария
 */
@Named(value = "oneTabModel")
@Dependent
public class TabModel
{
    private final List<BlockModel> blocks;
    private final DefaultDiagramModel model;
    private String name;
    public TabModel()
    {
       blocks = new ArrayList<>();
       model = new DefaultDiagramModel();
  
    }

    public List<BlockModel> getBlocks()
    {
        return blocks;
    }
    
    public DiagramModel getModel()
    {
        return model;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public void createModel()
    {
       model.setMaxConnections(-1);
       DiagramElement elementA = new DiagramElement(new BlockModel("30px", "30px", "ElementA"));
       DiagramElement elementB = new DiagramElement(new BlockModel("30px", "150px", "ElementB"));
       elementA.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
       elementB.addEndPoint(new DotEndPoint(EndPointAnchor.BOTTOM));
       model.addElement(elementA);
       model.addElement(elementB);
       model.connect(new Connection(elementA.getEndPoints().get(0), elementB.getEndPoints().get(0))); 
    }
        public void buttonAction() {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
