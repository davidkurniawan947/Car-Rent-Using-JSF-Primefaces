/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.ejb.Singleton;
import javax.ejb.Stateful;

/**
 *
 * @author louiv
 */
@Singleton
//@Stateful
public class StateManagerSB {
    
    private boolean notLogin = true;
    private boolean validLogin = false;
    private boolean isEmployee = false;
    private boolean isEmployeeValidLogin = false;
    private boolean isUser = false;
    
    private int productActiveIndex = 2;
    
    private int empActiveIndex = 0;
    
    private int profileActiveIndex = 0;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

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

    public boolean isIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
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
}
