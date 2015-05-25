package org.imageupload.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imageupload.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Georgia Papp
 */

public class LoginPage {

    private static final Log LOG = LogFactory.getLog(LoginPage.class);



    AuthenticationManager authenticationManager;



    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() throws java.io.IOException, ServletException {

        CustomUserDetailsService customUserDetailsService = new CustomUserDetailsService();

       // FacesContext.getCurrentInstance().getExternalContext().redirect("/j_spring_security_check?j_username=" + userName + "&j_password=" + password);

        try {
            Authentication request = new UsernamePasswordAuthenticationToken(this.getUserName(),this.getPassword());

            Authentication result = authenticationManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);


            return "/index.xhtml";

        } catch (AuthenticationException e) {


            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                                                 "Bad credentials!",
                                                                                 null));


            userName = null;
            password = null;
        }


        return null;

    }


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void logout(ActionEvent event) throws IOException {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/j_spring_security_logout");
    }
}
