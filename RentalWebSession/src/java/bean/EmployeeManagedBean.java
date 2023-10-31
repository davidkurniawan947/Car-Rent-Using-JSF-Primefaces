/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.EmployeeSB;
import controller.StateManagerSB;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Employees;
import sessionbeans.EmployeeSessionBean;
import util.PasswordHasher;

/**
 *
 * @author louiv
 */
//@Named(value = "employeeManagedBean")
@ManagedBean(name = "EmployeeManagedBean")
//@ApplicationScoped
@SessionScoped
public class EmployeeManagedBean {
    
    @EJB
    private EmployeeSB empSB;
    
    @EJB
    private StateManagerSB stateSB;
    
    @EJB
    private EmployeeSessionBean employeeSession;
    
    private boolean notLogin = true;
    private boolean validLogin = false;
    private boolean manager = false;
    
    private String tempUser;
    private String tempPass;
    
    private String empUser;
    private String empJob;
    
    //List all employees
    private List<Employees> listEmp;
    
    //Search Employee
    private int empIdSearch;
    
    private int empId;
    private String empUsername;
    private String empPassword;
    private String empJobDesc;
    
    //Add Employee
    private String addUsername;
    private String addPassword;
    private String addJob;
    
    
    // FOR CHANGING EMPLOYEE PASSWORD
    private int searchEmpPassId;
    private int empPassId;
    private String empPassUsername;
    private String empNewPassword;
    
    private boolean validEmp = false;
    
    
    
    // SESSION MIGRATION PHASE
    private Employees loginEmployee;
    private Employees searchEmployee;
    private Employees addEmployee;
    private Employees searchEmpPass;
    
    @ManagedProperty(value="#{StateManagedBean}")
    private StateManagedBean stateBean;
    
    
    /**
     * Creates a new instance of EmployeeManagedBean
     */
    public EmployeeManagedBean() {
    }
    
    // Setter Getter
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

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    public String getTempUser() {
        return tempUser;
    }

    public void setTempUser(String tempUser) {
        this.tempUser = tempUser;
    }

    public String getTempPass() {
        return tempPass;
    }

    public void setTempPass(String tempPass) {
        this.tempPass = tempPass;
    }
    

    //Profil
    public String getEmpUser(){
        //return empSB.getEmployee().getUsername();
        return loginEmployee.getUsername();
    }
    
    public String getEmpJob() {
        //return empSB.getEmployee().getJob();
        return loginEmployee.getJob();
    }

    //Methods
    public String login() {
        try {
            boolean validLogin = empSB.validateLogin(tempUser, tempPass);
            
            loginEmployee = employeeSession.validateLogin(tempUser, tempPass);
            
            if (validLogin){
                notLogin = false;
                this.validLogin = true;
                
                stateSB.setNotLogin(false);
                stateSB.setValidLogin(true);
                stateSB.setIsEmployee(true);
                
                stateBean.setNotLogin(false);
                stateBean.setValidLogin(true);
                stateBean.setIsEmployee(true);
                
                if (empSB.getEmployee().getJob().equalsIgnoreCase("manager")){
                    manager = true;
                }
                return "index";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect username or password!", "Enter a correct username or password!"));
                return "emplogin";
            }
        } catch (Exception e){
            return "index";
        }
    }
    
    public String logout(){
        try {
            //empSB.nullUser();
            
            //SESSIONS
            loginEmployee = null;
            
            validLogin = false;
            notLogin = true;
            manager = false;
            
            stateSB.setNotLogin(true);
            stateSB.setValidLogin(false);
            stateSB.setIsEmployee(false);
            
            
            //SESSIONS
            stateBean.setNotLogin(true);
            stateBean.setValidLogin(false);
            stateBean.setIsEmployee(false);
            
            return "index";
        } catch (Exception e){
            return "index";
        }
    }
    
    public String printEmp(){
        try {
            System.out.println(empSB.getEmployee().toString());
        } catch (Exception e){
            System.out.println("null pointer");
        }
        return "index";
    }
    
    // List all employee

    public List<Employees> getListEmp() {
        return empSB.listAllEmp();
    }
    
    
    // Searching employee

    public int getEmpIdSearch() {
        return empIdSearch;
    }

    public void setEmpIdSearch(int empIdSearch) {
        this.empIdSearch = empIdSearch;
    }

    public int getEmpId() {
        try {
            //return empSB.getSearchEmployee().getId();
            return searchEmployee.getId();
        } catch (Exception e){
            return 0;
        }
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpUsername() {
        try {
            //return empSB.getSearchEmployee().getUsername();
            return searchEmployee.getUsername();
        } catch (Exception e){
            return "";
        }
    }

    public void setEmpUsername(String empUsername) {
        this.empUsername = empUsername;
    }

    public String getEmpPassword() {
        try {
            //return empSB.getSearchEmployee().getPassword();
            return searchEmployee.getPassword();
        } catch (Exception e){
            return "";
        }
    }

    public void setEmpPassword(String empPassword) {
        this.empPassword = empPassword;
    }

    public String getEmpJobDesc() {
        try {
            //return empSB.getSearchEmployee().getJob();
            return searchEmployee.getJob();
        } catch (Exception e){
            return "";
        }
    }

    public void setEmpJobDesc(String empJobDesc) {
        this.empJobDesc = empJobDesc;
    }
    
    public String searchEmp(){
        try {
            
            //stateSB.setEmpActiveIndex(1);
            
            stateBean.setEmpActiveIndex(1);
            
            //boolean validEmp = empSB.searchEmp(empIdSearch);
            
            searchEmployee = employeeSession.searchEmp(empIdSearch);
            
            /*
            if (validEmp && searchEmployee != null){
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Pegawai tidak ditemukan!"));
                return "emplist";
            }
            */
            
            if (searchEmployee != null){
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Pegawai tidak ditemukan!"));
                return "emplist";
            }
        } catch (Exception e){
            return "emplist";
        }
    }
    
    public String updateEmp(){
        try {       
            //stateSB.setEmpActiveIndex(1);
            
            stateBean.setEmpActiveIndex(1);
            
            //empSB.getSearchEmployee().setPassword(empPassword);
            //empSB.getSearchEmployee().setJob(empJobDesc);
            searchEmployee.setJob(empJobDesc);
            
           // boolean validUpdate = empSB.updateEmp();
            boolean validUpdateSession = employeeSession.updateEmp(searchEmployee);
            
            /*
            if (validUpdate && validUpdateSession){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil di-update!"));
                
                System.out.println("Berhasil update");
                return "emplist";
            } else {
                return "emplist";
            }
            */
            
            if (validUpdateSession){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil di-update!"));
                
                System.out.println("Berhasil update");
                return "emplist";
            } else {
                return "emplist";
            }
        } catch (Exception e){
            return "emplist";
        }
    }
    
    public String removeEmp(){
        try {
            //stateSB.setEmpActiveIndex(0);
            
            stateBean.setEmpActiveIndex(0);
            
            //boolean validRemove = empSB.removeEmp();
            boolean validRemoveSession = employeeSession.removeEmp(searchEmployee);
            
            /*
            if (validRemove && validRemoveSession){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil dihapus."));
                
                empSB.nullSearchUser();
                searchEmployee = null;
                return "emplist";
            } else {
                return "emplist";
            }
            */
            
            if (validRemoveSession){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil dihapus."));
                
                //empSB.nullSearchUser();
                searchEmployee = null;
                return "emplist";
            } else {
                return "emplist";
            }
        } catch (Exception e){
            return "emplist";
        }
    }
    
    // Add Employee

    public String getAddUsername() {
        return addUsername;
    }

    public void setAddUsername(String addUsername) {
        this.addUsername = addUsername;
    }

    public String getAddPassword() {
        return addPassword;
    }

    public void setAddPassword(String addPassword) {
        this.addPassword = addPassword;
    }

    public String getAddJob() {
        return addJob;
    }

    public void setAddJob(String addJob) {
        this.addJob = addJob;
    }
    
    public String addEmployee(){
        try {
            
            //stateSB.setEmpActiveIndex(2);
            stateBean.setEmpActiveIndex(2);
            
            if (addUsername.isEmpty() || addPassword.isEmpty() || addJob.isEmpty()){
                if (addUsername.isEmpty()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Username harus diisi!"));
                }
                
                if (addPassword.isEmpty()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Password harus diisi!"));
                }
                
                if (addJob.isEmpty()){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Posisi harus diisI!"));
                }
                
                return "emplist";
            }
            
            // HASHING THE PASSWORD
            PasswordHasher passHasher = new PasswordHasher();
            String hashedEmpPassword = passHasher.toHexString(passHasher.getSHA(addPassword));
            
            //ASSIGN THE HASHED PASSWORD
            //empSB.setAddEmployee(new Employees(addUsername, hashedEmpPassword, addJob));
            addEmployee = new Employees(addUsername, hashedEmpPassword, addJob);
                    
            //boolean validAdd = empSB.addEmp();
            boolean validAddSession = employeeSession.addEmp(addEmployee);
            
            /*
            if (validAdd && validAddSession){
                System.out.println("Berhasil menambahkan employee");
                
                empSB.nullAddEmp();
                addUsername = "";
                addPassword = "";
                addJob = "";
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Berhasil menambahkan pegawai!"));
                
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Username sudah terpakai!"));
                
                return "emplist";
            }
            */
            
            if (validAddSession){
                System.out.println("Berhasil menambahkan employee");
                
                empSB.nullAddEmp();
                addEmployee = null;
                addUsername = "";
                addPassword = "";
                addJob = "";
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Berhasil menambahkan pegawai!"));
                
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Username sudah terpakai!"));
                
                return "emplist";
            }
        } catch (Exception e){
            return "emplist";
        }
    }

    

    // FOR CHANGING EMPLOYEE PASSWORD, GETTER SETTER - line 63
    
    public int getSearchEmpPassId() {
        return searchEmpPassId;
    }
    
    public void setSearchEmpPassId(int searchEmpPassId) {
        this.searchEmpPassId = searchEmpPassId;
    }

    public int getEmpPassId() {
        try {
            //return empSB.getChangePassEmployee().getId();
            return searchEmpPass.getId();
        } catch (Exception e){
            return 0;
        }
    }

    public void setEmpPassId(int empPassId) {
        this.empPassId = empPassId;
    }

    public String getEmpPassUsername() {
        try {
            //return empSB.getChangePassEmployee().getUsername();
            return searchEmpPass.getUsername();
        } catch (Exception e){
            return "";
        }
    }

    public void setEmpPassUsername(String empPassUsername) {
        this.empPassUsername = empPassUsername;
    }

    public String getEmpNewPassword() {
        return empNewPassword;
    }

    public void setEmpNewPassword(String empNewPassword) {
        this.empNewPassword = empNewPassword;
    }

    public boolean isValidEmp() {
        return validEmp;
    }

    public void setValidEmp(boolean validEmp) {
        this.validEmp = validEmp;
    }
    
    public String searchChangePassEmployee(){
        try {
            
            //stateSB.setEmpActiveIndex(3);
            stateBean.setEmpActiveIndex(3);
            
            //boolean valid = empSB.searchChangePassEmp(searchEmpPassId);
            searchEmpPass = employeeSession.searchChangePassEmp(searchEmpPassId);
            
            /*
            if (valid && searchEmpPass != null){
                validEmp = true;
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Pegawa tidak ditemukan!"));
                return "emplist";
            }
            */
            
            if (searchEmpPass != null){
                validEmp = true;
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Pegawai tidak ditemukan!"));
                return "emplist";
            }
            
        } catch (Exception e){
            return "emplist";
        }
    }
    
    public String changePassword(){
        try {
            
            //stateSB.setEmpActiveIndex(3);
            stateBean.setEmpActiveIndex(3);
            
            // HASH THE NEW PASSWORD
            PasswordHasher newPassHash = new PasswordHasher();
            String HashedNewPassword = newPassHash.toHexString(newPassHash.getSHA(empNewPassword));
            
            //empSB.getChangePassEmployee().setPassword(HashedNewPassword);
            searchEmpPass.setPassword(HashedNewPassword);
            
            //boolean validChange = empSB.updateEmpPass();
            boolean validChangeSession = employeeSession.updateEmpPass(searchEmpPass);
            
            /*
            if (validChange){
                empSB.nullChangePassEmp();
                empNewPassword = "";
                validEmp = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Password berhasil diubah!"));
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Password gagal diubah!"));
                return "emplist";
            }
            */
            
            if (validChangeSession){
                //empSB.nullChangePassEmp();
                searchEmpPass = null;
                empNewPassword = "";
                validEmp = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Password berhasil diubah!"));
                return "emplist";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Password gagal diubah!"));
                return "emplist";
            }
            
        } catch (Exception e){
            return "emplist";
        }
    }
    
    
    
    
    
    
    
    
    // SESSION MIGRATION PHASE

    public Employees getLoginEmployee() {
        return loginEmployee;
    }

    public void setLoginEmployee(Employees loginEmployee) {
        this.loginEmployee = loginEmployee;
    }
    
    public String printEmployee(){
        try {
            System.out.println(loginEmployee.toString());
            return "index";
        } catch (Exception e){
            System.out.println("Ini tuh null");
            return "index";
        }
    }

    public Employees getSearchEmployee() {
        return searchEmployee;
    }

    public void setSearchEmployee(Employees searchEmployee) {
        this.searchEmployee = searchEmployee;
    }

    public Employees getAddEmployee() {
        return addEmployee;
    }

    public void setAddEmployee(Employees addEmployee) {
        this.addEmployee = addEmployee;
    }

    public Employees getSearchEmpPass() {
        return searchEmpPass;
    }

    public void setSearchEmpPass(Employees searchEmpPass) {
        this.searchEmpPass = searchEmpPass;
    }
    
    
    
    // STATE BEAN SESSION

    public StateManagedBean getStateBean() {
        return stateBean;
    }

    public void setStateBean(StateManagedBean stateBean) {
        this.stateBean = stateBean;
    }
    
}
