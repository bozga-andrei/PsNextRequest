package be.smals.psnextrequest.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;


@ManagedBean(name = "pageControllerBean")
@RequestScoped
public class PageController {

    private String pageName;


    public String getPageName() {
        this.pageName = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }


}
