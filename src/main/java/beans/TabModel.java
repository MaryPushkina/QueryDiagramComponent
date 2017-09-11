/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
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

    private static int number = 0;
    private final List<BlockModel> blocks;
    private final DefaultDiagramModel model;
    private String name;
    private final List<ConnectionModel> connections;

    public TabModel()
    {
        blocks = new ArrayList<>();
        model = new DefaultDiagramModel();
        connections = new ArrayList<>();
    }

    public void addElement()
    {
        number++;
        blocks.add(new BlockModel("30px", "30px", "Element" + Integer.toString(number)));
        addConnection(blocks.get(blocks.size() - 1));
        int n = 0;

    }

    private void addConnection(BlockModel blocksA)
    {
        if (blocks.size() > 1)
        {
            connections.add(new ConnectionModel(blocks.get(blocks.size() - 2), blocksA));
            System.out.println("src=\"//http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js\""+"\n"
                    + "src=\"//cdnjs.cloudflare.com/ajax/libs/jsPlumb/1.4.1/jquery.jsPlumb-1.4.1-all-min.js\""+"\n"+
                    "if(document.getElementById(\""+blocksA.name+"\")){"+"\n"+
                    "jsPlumb.ready(function () {\n"
                    + "\n"
                    + "    jsPlumb.connect({\n"
                    + "        source: \""+blocks.get(blocks.size() - 2).name+"\",\n"
                    + "        target: \""+blocksA.name+"\",\n"
                    + "        endpoint: \"Blank\"\n"
                    + "    });\n"
                    + "\n"
                    + "    jsPlumb.draggable($(\".item\"));\n"
                    + "    jsPlumb.draggable($(\".item\"));\n"
                    + "});\n"
                    +"}");
            RequestContext.getCurrentInstance().execute(
                    "if(document.getElementById(\""+blocksA.name+"\")){"+"\n"+
                    "jsPlumb.ready(function () {\n"
                    + "\n"
                    + "    jsPlumb.connect({\n"
                    + "        source: \""+blocks.get(blocks.size() - 2).name+"\",\n"
                    + "        target: \""+blocksA.name+"\",\n"
                    + "        endpoint: \"Blank\"\n"
                    + "    });\n"
                    + "\n"
                    + "});\n"
                    +"}");

        }
        


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

    public void buttonAction()
    {
        addMessage("Welcome to Primefaces!!");
    }

    public void addMessage(String summary)
    {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
