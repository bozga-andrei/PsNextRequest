package be.smals.psnextrequest.bean;

import be.smals.psnextrequest.entity.Project;
import be.smals.psnextrequest.entity.User;
import org.primefaces.model.DualListModel;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name = "projectSessionBean")
@SessionScoped
public class ProjectSessionBean implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 3702250746683304286L;

    private Project selectedProject;

    private DualListModel<User> pickListTeamMembers;

    public ProjectSessionBean() {
        if (pickListTeamMembers == null) {
            pickListTeamMembers = new DualListModel<User>();
        }
    }


    public Project getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(Project selectedProject) {
        this.selectedProject = selectedProject;
    }

    public DualListModel<User> getPickListTeamMembers() {
        return pickListTeamMembers;
    }

    public void setPickListTeamMembers(DualListModel<User> pickListTeamMembers) {
        this.pickListTeamMembers = pickListTeamMembers;
    }


}
