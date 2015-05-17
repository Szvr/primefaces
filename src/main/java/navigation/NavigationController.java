package navigation;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;

/**
 * @Author Csaba Szever
 */
@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable{

    public String moveToHomePage() {
        return "index";
    }

    public String moveToGallery() {
        return "gallery";
    }

    public String moveToCamera() {
        return "camera";
    }
}
