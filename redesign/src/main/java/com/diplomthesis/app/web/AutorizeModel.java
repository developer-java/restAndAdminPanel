package com.diplomthesis.app.web;

import com.diplomthesis.app.ejb.AutorizeConroller;
import com.diplomthesis.app.exception.EmplySessionException;
import com.diplomthesis.app.persistence.SystemUser;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean
@SessionScoped
public class AutorizeModel {

    private SystemUser user;
    private String login;
    private String pass;
    @EJB
    private AutorizeConroller autorizeConroller;

    public String goToSign(){
        if(login!=null && pass!=null){
            user = autorizeConroller.getUser(login,pass);
            if(user==null){
                //TODO User is npt found!
                return null;
            }
        }else{
            // TODO write message in Frontend, then is incorrect pass or login
            return null;
        }
        return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/welcome/index.xhtml?faces-redirect=true";
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void redirect() throws EmplySessionException {
        if(getUser()==null){
            throw new EmplySessionException();
        }
    }
    public void red() throws IOException {
        if(getUser()!=null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("/welcome/index.xhtml?faces-redirect=true");
        }
    }
}
