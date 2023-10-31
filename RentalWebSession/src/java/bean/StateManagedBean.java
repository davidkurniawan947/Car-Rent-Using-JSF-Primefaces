/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.StateManagerSB;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author louiv
 */
//@Named(value = "stateManagerBean")
@ManagedBean(name = "StateManagedBean")
//@ApplicationScoped
@SessionScoped
public class StateManagedBean {
    
    @EJB
    private StateManagerSB stateSB;
    
    /*
    private boolean notLogin;
    private boolean validLogin;
    private boolean isUser;
    private boolean isEmployee;
    private boolean isEmployeeValidLogin;
    
    private int productActiveIndex;
    
    private int empActiveIndex;
    
    private int profileActiveIndex;
    */
    
    private boolean notLogin = true;
    private boolean validLogin = false;
    private boolean isEmployee = false;
    private boolean isEmployeeValidLogin = false;
    private boolean isUser = false;
    
    private int productActiveIndex = 2;
    
    private int empActiveIndex = 0;
    
    private int profileActiveIndex = 0;
    /**
     * Creates a new instance of StateManagerBean
     */
    public StateManagedBean() {
    }
    
    /*
    public boolean isNotLogin() {
        return stateSB.isNotLogin();
    }

    public boolean isValidLogin() {
        return stateSB.isValidLogin();
    }

    public boolean isIsUser() {
        return stateSB.isIsUser();
    }

    public boolean isIsEmployee() {
        return stateSB.isIsEmployee();
    }

    public boolean isIsEmployeeValidLogin() {
        return stateSB.isIsEmployeeValidLogin();
    }

    public int getProductActiveIndex() {
        return stateSB.getProductActiveIndex();
    }

    public void setProductActiveIndex(int productActiveIndex) {
        this.productActiveIndex = productActiveIndex;
    }

    public int getEmpActiveIndex() {
        return stateSB.getEmpActiveIndex();
    }

    public void setEmpActiveIndex(int empActiveIndex) {
        this.empActiveIndex = empActiveIndex;
    }

    public int getProfileActiveIndex() {
        return stateSB.getProfileActiveIndex();
    }

    public void setProfileActiveIndex(int profileActiveIndex) {
        this.profileActiveIndex = profileActiveIndex;
    }

    */

    public boolean isNotLogin() {
        return notLogin;
    }

    public void setNotLogin(boolean notLogin) {
        this.notLogin = notLogin;
    }

    public boolean isValidLogin() {
        return validLogin;
    }

    public void setValidLogin(boolean validLogin) {
        this.validLogin = validLogin;
    }

    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isIsEmployeeValidLogin() {
        return isEmployeeValidLogin;
    }

    public void setIsEmployeeValidLogin(boolean isEmployeeValidLogin) {
        this.isEmployeeValidLogin = isEmployeeValidLogin;
    }

    public int getProductActiveIndex() {
        return productActiveIndex;
    }

    public void setProductActiveIndex(int productActiveIndex) {
        this.productActiveIndex = productActiveIndex;
    }

    public int getEmpActiveIndex() {
        return empActiveIndex;
    }

    public void setEmpActiveIndex(int empActiveIndex) {
        this.empActiveIndex = empActiveIndex;
    }

    public int getProfileActiveIndex() {
        return profileActiveIndex;
    }

    public void setProfileActiveIndex(int profileActiveIndex) {
        this.profileActiveIndex = profileActiveIndex;
    }
    
    public void verifyEmpLogin() throws IOException{
        if (!isEmployee){
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }
    
    public void verifyUserLogin() throws IOException{
        if (!isUser){
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        }
    }
}
