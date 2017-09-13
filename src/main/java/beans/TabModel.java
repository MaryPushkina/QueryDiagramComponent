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

    private String numberDeleteElement;

    private static int number = 0;
    private final List<BlockModel> blocks;
    private final DefaultDiagramModel model;
    private String name;
    private int x = 0;
    private int y = 0;
    private List<ConnectionModel> connections;

    public TabModel()
    {
        blocks = new ArrayList<>();
        model = new DefaultDiagramModel();
        connections = new ArrayList<>();
    }

    public String getNumberDeleteElement()
    {
        return numberDeleteElement;
    }

    public void setNumberDeleteElement(String numberDeleteElement)
    {
        this.numberDeleteElement = numberDeleteElement;
    }

    public void addElement()
    {
        number++;
        x+=30;
        y+=30;
        String xStr = Integer.toString(x)+"px";
        String yStr = Integer.toString(y)+"px";
        blocks.add(new BlockModel(xStr, yStr, "Element" + Integer.toString(number)));

        if (number > 1)
        {
            connections.add(new ConnectionModel(blocks.get(blocks.size() - 2), blocks.get(blocks.size() - 1)));
            drawingConnections();

        }
        int n = 0;

    }

    private List<BlockModel> findConnectionsElement(BlockModel block)
    {
        List<BlockModel> removeBlocks = new ArrayList<>();
        removeBlocks.add(block);
        List<ConnectionModel> copyConnections = new ArrayList<>(connections);
        for (ConnectionModel connection : copyConnections)
        {
            if ((block.equals(connection.endBlockModel)) || (block.equals(connection.startBlockModel)))
            {
                connections.remove(connection);
                if (block.equals(connection.startBlockModel))
                {
                    removeBlocks.add(connection.endBlockModel);
                }
            }
        }
        return removeBlocks;
    }

    public void deleteElement()
    {
        int k = Integer.parseInt(numberDeleteElement) - 1;
        removejs(blocks.get(k));
        if ((numberDeleteElement != null) && (k <= blocks.size()))
        {
            List<BlockModel> removeBlocks = findConnectionsElement(blocks.get(k));

            drawingConnections();
            for (BlockModel bl : removeBlocks)
            {
                blocks.remove(bl);
            }
        }
    }

    private void removejs(BlockModel blockA)
    {
        System.out.println("jsPlumb.remove(\"" + blockA.name + "\"); ");
        RequestContext.getCurrentInstance().execute("jsPlumb.deleteEveryEndpoint();");
        RequestContext.getCurrentInstance().execute(
                "jsPlumb.remove(\"" + blockA.name + "\"); \n"
        );
    }

    private void drawingConnections()
    {
        int k = 0;

        for (ConnectionModel connection : connections)
        {
            addConnection(connection.endBlockModel, connection.startBlockModel);
        }
    }

    //добавить новую связь
    private void addConnection(BlockModel blocksA, BlockModel blocksB)
    {

        System.out.println("src=\"//http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js\"" + "\n"
                + "src=\"//cdnjs.cloudflare.com/ajax/libs/jsPlumb/1.4.1/jquery.jsPlumb-1.4.1-all-min.js\"" + "\n"
                + "if(document.getElementById(\"" + blocksA.name + "\")){" + "\n"
                + "jsPlumb.ready(function () {\n"
                + "\n"
                + " var firstInstance = jsPlumb.getInstance();"
                + "    firstInstance.connect({\n"
                + "        source: \"" + blocksB.name + "\",\n"
                + "        target: \"" + blocksA.name + "\",\n"
                + "        anchor: [\"Left\",\"Right\"],\n"
                + "        endpoint: \"Blank\"\n"
                + "    });\n"
                + "\n"
                + "    jsPlumb.draggable($(\".item\"));\n"
                + "    jsPlumb.draggable($(\".item\"));\n"
                + "});\n"
                + "}");

        RequestContext.getCurrentInstance().execute(
                "jsPlumb.setSuspendDrawing(true);"+ "\n"
                + "if(document.getElementById(\"" + blocksA.name + "\")){" + "\n"
                + "jsPlumb.ready(function () {\n"
                + "\n"
                + " var firstInstance = jsPlumb.getInstance();"
                + "   firstInstance.connect({\n"
                + "        source: \"" + blocksB.name + "\",\n"
                + "        target: \"" + blocksA.name + "\",\n"
                + "        anchor: [\"Left\",\"Right\"],\n"
                + "        endpoint: \"Blank\"\n"
                + "    });\n"
                + "\n"
                + "});\n"
                + "}"
                +"jsPlumb.setSuspendDrawing(false, true);"
        );
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
