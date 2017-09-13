/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.awt.event.ActionEvent;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Мария
 */
@Named(value = "tabViewModel")
@SessionScoped
public class TabsViewModel implements Serializable
{

    private List<TabModel> tabs;
    private Integer activeTabIndex = -1;

    @PostConstruct
    public void init()
    {
        tabs = new ArrayList<>();
        TabModel tab1 = new TabModel();
        tab1.setName("tab1");
        tab1.createModel();
        TabModel tab2 = new TabModel();
        tab2.createModel();
        tab2.setName("tab2");
        tabs.add(tab1);
        tabs.add(tab2);

    }

    public List<TabModel> getTabs()
    {
        return tabs;
    }

    public Integer getActiveTabIndex()
    {
        return activeTabIndex;
    }

    public void setActiveTabIndex(Integer activeTabIndex)
    {
        this.activeTabIndex = activeTabIndex;
    }
    public void updateComponent(String compId){ 
     RequestContext.getCurrentInstance().update(compId);
}

}
