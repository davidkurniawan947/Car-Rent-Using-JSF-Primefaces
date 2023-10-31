/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.StateManagerSB;
import controller.UserSB;
import javax.inject.Named;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.Users;
import sessionbeans.UserSessionBean;
import util.PasswordHasher;

/**
 *
 * @author louiv
 */
@Named(value = "userManagedBean")
@ManagedBean(name = "UserManagedBean")
//ApplicationScoped
@SessionScoped
public class UserManagedBean implements Serializable {
    
    @EJB
    private UserSB userSB;
    
    @EJB
    private StateManagerSB stateSB;
    
    private String tempUser;
    private String tempPass;
    
    private boolean notLogin = true;
    private boolean validLogin = false;
    
    private String regisUser;
    private String regisPass;
    private String regisNik;
    private String regisEmail;
    private String regisNoHp;
    private String regisNamaLengkap;
    private String regisAlamat;
    
    
    // Profil
    private String userUserName;
    private String userPass;
    private String userEmail;
    private String userHp;
    private String userNik;
    private String userNamaLengkap;
    private String userAlamat;
    
    
    // CHANGING PASSWORD
    private String oldPass;
    private String newPass;
    
    
    //Customer Master Data
    private List<Users> listUsers;
    
    
    //Editing Boolean
    private boolean editing = false;
    
    
    //Search User;
    private int userId;
    private int userUserId;
    
    // MIGRATION TO SESSION
    @EJB
    private UserSessionBean userSession;
    
    @ManagedProperty(value="#{StateManagedBean}")
    private StateManagedBean stateBean;
    
    private Users loginUser;
    private Users registrationUser;
    private Users searchUser;
    
    // PROFILE TO USE WHEN AN EMPLOYEE SEARCH FOR USER
    private String searchUserUsername;
    private String searchUserNamaLengkap;
    private String searchUserAlamat;
    private String searchUserEmail;
    private String searchUserNoHp;
    private String searchUserNik;
    
    private String searchByName = "";
    private String searchByNameQuery = "WHERE u.username LIKE CONCAT('%', :tempName, '%') OR u.id LIKE CONCAT('%', :tempId, '%') OR u.namaLengkap LIKE CONCAT('%', :tempNamaLengkap, '%') OR u.email LIKE CONCAT('%', :tempEmail, '%') OR u.noHp LIKE CONCAT('%', :tempNoHp, '%') OR u.nik LIKE CONCAT('%', :tempNik, '%')";
    
    
    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
    }
    
    public void setUserSB(UserSB userSB) {
        this.userSB = userSB;
    }

    public String getUserName(){
        //return userSB.getUsers().getUsername();
        return loginUser.getUsername();
        //return userSB.getUsers().getUsername();
    }
    
    public String login() throws NoSuchAlgorithmException{
        try {
            //userSB.setUsers(new Users(tempUser, tempPass));
            //userSB.setUser(new User(tempUser, tempPass));

            // CONVERTING PLAIN STRING TO HASHED PASSWORD
            PasswordHasher hasher = new PasswordHasher();
            String hashedTempPass = hasher.toHexString(hasher.getSHA(tempPass));

            //boolean validLogin = userSB.validateLogin(tempUser, hashedTempPass);
            loginUser = userSession.validateLogin(tempUser, hashedTempPass);
            
            /*
            if (validLogin){
                notLogin = false;
                this.validLogin = true;

                stateSB.setNotLogin(false);
                stateSB.setValidLogin(true);
                stateSB.setIsUser(true);

                System.out.println(userSB.getUsers().toString());
                return "products";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect username or password!", "Enter a correct username or password"));
                System.out.println(validLogin);
                System.out.println("Gagal login bos");
                return "login";
            }
            */
            if (loginUser != null){
                notLogin = false;
                this.validLogin = true;

                //stateSB.setNotLogin(false);
               // stateSB.setValidLogin(true);
                //stateSB.setIsUser(true);
                
                stateBean.setNotLogin(false);
                stateBean.setValidLogin(true);
                stateBean.setIsUser(true);

                //System.out.println(userSB.getUsers().toString());
                return "products";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect username or password!", "Enter a correct username or password"));
                System.out.println(validLogin);
                System.out.println("Gagal login bos");
                return "login";
            }
        } catch (Exception e){
            System.out.println(e);
            return "login";
        }
    }
    
    public String logout(){
        try {
            userSB.nullUser();
            loginUser = null;
            validLogin = false;
            notLogin = true;
            
            
            //stateSB.setNotLogin(true);
            //stateSB.setValidLogin(false);
            //stateSB.setIsUser(false);
            
            stateBean.setNotLogin(true);
            stateBean.setValidLogin(false);
            stateBean.setIsUser(false);
            
            //System.out.println(userSB.getUsers().toString());
            return "index";
        } catch(Exception e){
            return "index";
        }
    }
    
    public String printUser(){
        try {
            System.out.println(loginUser.toString());
        } catch (Exception e){
            System.out.println("Null pointer");
        }
        return "index";
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

    public String getRegisUser() {
        return regisUser;
    }

    public void setRegisUser(String regisUser) {
        this.regisUser = regisUser;
    }

    public String getRegisPass() {
        return regisPass;
    }

    public void setRegisPass(String regisPass) {
        this.regisPass = regisPass;
    }

    public String getRegisNik() {
        return regisNik;
    }

    public void setRegisNik(String regisNik) {
        this.regisNik = regisNik;
    }

    public String getRegisEmail() {
        return regisEmail;
    }

    public void setRegisEmail(String regisEmail) {
        this.regisEmail = regisEmail;
    }

    public String getRegisNoHp() {
        return regisNoHp;
    }

    public void setRegisNoHp(String regisNoHp) {
        this.regisNoHp = regisNoHp;
    }

    public String getRegisNamaLengkap() {
        return regisNamaLengkap;
    }

    public void setRegisNamaLengkap(String regisNamaLengkap) {
        this.regisNamaLengkap = regisNamaLengkap;
    }

    public String getRegisAlamat() {
        return regisAlamat;
    }

    public void setRegisAlamat(String regisAlamat) {
        this.regisAlamat = regisAlamat;
    }
    
    
    
    // Registrasi
    
    public String register() throws NoSuchAlgorithmException{
        try {
            /*
        if (regisEmail.isEmpty()){
            userSB.setUsers(new Users(regisUser, regisPass, regisNoHp, regisNik));
            //userSB.setUsers(new Users(regisUser, regisPass, regisNoHp, regisNik));
        } else {
            userSB.setUsers(new Users(regisUser, regisPass, regisNamaLengkap, regisEmail, regisNoHp, regisNik));
            //userSB.setUsers(new Users(regisUser, regisPass, regisEmail, regisNoHp, regisNik));
        }
             */

            // BEGIN HASHING PASSWORD - SHA-256:
            PasswordHasher hasher = new PasswordHasher();
            String hashedPassword = hasher.toHexString(hasher.getSHA(regisPass));

            // Updated version
            //userSB.setUsers(new Users(regisUser, hashedPassword, regisNamaLengkap, regisEmail, regisNoHp, regisNik));
            registrationUser = new Users(regisUser, hashedPassword, regisNamaLengkap, regisAlamat , regisEmail, regisNoHp, regisNik);

            //boolean validRegis = userSB.registerUser();
            boolean validRegisSession = userSession.registerUser(registrationUser);
            
            /*
            if (validRegis) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrasi berhasil!", "Silahkan login dengan username dan password anda."));

                return "login";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register tidak berhasil!", "Username sudah ada, tolong pilih username lain!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "register";
            }
            */
            
            if (validRegisSession) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrasi berhasil!", "Silahkan login dengan username dan password anda."));

                return "login";
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register tidak berhasil!", "Username sudah ada, tolong pilih username lain!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "register";
            }
        } catch (Exception e) {
            return "register";
        }
    }
    
    
    // Profil user
    public String getUserUserName() {
        try {
            //return userSB.getUsers().getUsername();
            return loginUser.getUsername();
        } catch (Exception e){
            return "";
        }
        //return userSB.getUsers().getUsername();
    }

    public String getUserPass() {
        try {
            //return userSB.getUsers().getPassword();
            return loginUser.getPassword();
        } catch (Exception e){
            return "";
        }
    }

    public String getUserEmail() {
        try {
            //return userSB.getUsers().getEmail();
            return loginUser.getEmail();
        } catch (Exception e){
            return "";
        }
    }

    public String getUserHp() {
        try {
            //return userSB.getUsers().getNoHp();
            return loginUser.getNoHp();
        } catch (Exception e){
            return "";
        }
    }

    public String getUserNik() {
        try {
            //return userSB.getUsers().getNik();
            return loginUser.getNik();
        } catch (Exception e){
            return "";
        }
    }

    public String getUserNamaLengkap() {
        try {
            //return userSB.getUsers().getNamaLengkap();
            return loginUser.getNamaLengkap();
        } catch (Exception e){
            return "";
        }
    }

    public String getUserAlamat() {
        try {
            return loginUser.getAlamat();
        } catch (Exception e){
            return "";
        }
    }
    
    

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserHp(String userHp) {
        this.userHp = userHp;
    }

    public void setUserNik(String userNik) {
        this.userNik = userNik;
    }

    public void setUserNamaLengkap(String userNamaLengkap) {
        this.userNamaLengkap = userNamaLengkap;
    }

    public void setUserAlamat(String userAlamat) {
        this.userAlamat = userAlamat;
    }
    
    
    
    public String updateUser(){
        
        //userSB.getUsers().getUsersPK().setUsername(userUserName);
        //userSB.getUsers().setUsername(userUserName);
        //userSB.getUsers().setPassword(userPass);
        //userSB.getUsers().setNamaLengkap(userNamaLengkap);
        //userSB.getUsers().setEmail(userEmail);
        //userSB.getUsers().setNoHp(userHp);
        //userSB.getUsers().setNik(userNik);
        
        loginUser.setNamaLengkap(userNamaLengkap);
        loginUser.setAlamat(userAlamat);
        loginUser.setEmail(userEmail);
        loginUser.setNoHp(userHp);
        loginUser.setNik(userNik);
    
        //boolean updateUserData = userSB.updateUser();
        boolean updateUserDataSession = userSession.updateUser(loginUser);
        /*
        if (updateUserData){
            stateSB.setProfileActiveIndex(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil diupdate!"));
            
            return "profil";
        } else {
            stateSB.setProfileActiveIndex(0);
            System.out.println("Gagal update");
            return "profil";
        }
        */
        
        if (updateUserDataSession){
            //stateSB.setProfileActiveIndex(0);
            stateBean.setProfileActiveIndex(0);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil diupdate!"));
            
            return "profil";
        } else {
            //stateSB.setProfileActiveIndex(0);
            stateBean.setProfileActiveIndex(0);
            System.out.println("Gagal update");
            return "profil";
        }
    }
    
    
    // Customer Master Data

    public List<Users> getListUsers() {
        return userSB.listSemuaUser(searchByNameQuery, searchByName);
    }
    
    
    //Editing

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
    public String changeEditing(){
        if (editing == true){
            editing = false;
            return "usersmaster";
        } else {
            editing = true;
            return "usersmaster";
        }
    }
    
    public String checkEditingStatus(){
        if (editing == true){
            return "Yes";
        } else {
            return "No";
        }
    }
    
    public String updateUserByEmployee(){
        
        /*
        userSB.getUsers().setUsername(object.getUsername());
        //userSB.getUsers().setPassword(object.getPassword());
        */
        //userSB.getUsers().setNamaLengkap(userNamaLengkap);
        //userSB.getUsers().setEmail(userEmail);
        //userSB.getUsers().setNoHp(userHp);
        //userSB.getUsers().setNik(userNik);
        
        searchUser.setNamaLengkap(searchUserNamaLengkap);
        searchUser.setAlamat(searchUserAlamat);
        searchUser.setEmail(searchUserEmail);
        searchUser.setNoHp(searchUserNoHp);
        searchUser.setNik(searchUserNik);
        
        //boolean updateUserData = userSB.updateUser();
        boolean updateUserDataSession = userSession.updateUser(searchUser);
        
        /*
        if (updateUserData){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data user berhasil di update!"));
            return "usersmaster";
        } else {
            System.out.println("Gagal update");
            return "usersmaster";
        }
        */
        
        if (updateUserDataSession){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data user berhasil di update!"));
            return "usersmaster";
        } else {
            System.out.println("Gagal update");
            return "usersmaster";
        }
    }
    
    //Search User

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserUserId() {
        try {
            return searchUser.getId();
        } catch (Exception e){
            return 0;
        }
    }

    public void setUserUserId(int userUserId) {
        this.userUserId = userUserId;
    }
    
    public String checkUser(){
        try {
            //boolean validUser = userSB.checkUser(userId);
            searchUser = userSession.checkUser(userId);
            
            /*
            if (validUser){
                
                return "usersmaster";
            } else {
                userSB.nullUser();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "User tidak ditemukan!"));
                return "usersmaster";
            }
            */
            
            if (searchUser != null){
                return "usersmaster";
            } else {
                //userSB.nullUser();
                //searchUser = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "User tidak ditemukan!"));
                return "usersmaster";
            }
        } catch (Exception e){
            return "usersmaster";
        }
    }
    
    /*
    public String updateUserByEmployee(){
        //userSB.getUsers().getUsersPK().setUsername(userUserName);
        //userSB.getUsers().setUsername(userUserName);
        userSB.getUsers().setPassword(userPass);
        userSB.getUsers().setEmail(userEmail);
        userSB.getUsers().setNoHp(userHp);
        userSB.getUsers().setNik(userNik);
    
        boolean updateUserData = userSB.updateUser();
        
        if (updateUserData){
            return "usersmaster";
        } else {
            System.out.println("Gagal update");
            return "usersmaster";
        }
    }
        */
    
    public String removeUser(){
        try {
            //boolean validUser = userSB.removeUser();
            boolean validUserRemoveSession = userSession.removeUser(searchUser);
            
            /*
            if (validUser){
                userSB.nullUser();
                searchUser = null;
                return "usersmaster";
            } else {
                System.out.println("Gagal");
                return "usersmaster";
            }
            */
            
            if (validUserRemoveSession){
                searchUser = null;
                return "usersmaster";
            } else {
                System.out.println("Gagal");
                return "usersmaster";
            }
        } catch (Exception e){
            System.out.println("Gagal remove");
            return "usersmaster";
        }
    }
    
    // CHANGING PASSWORD GETTER SETTER

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    
    public String changePassword(){
        try {
            PasswordHasher hasher = new PasswordHasher();
            
            // Verify the old password first.
            String hashedOldPassword = hasher.toHexString(hasher.getSHA(oldPass));
            
            //boolean validOldPassword = userSB.verifyOldPassword(hashedOldPassword);
            boolean validOldPasswordSession = userSession.verifyOldPassword(loginUser, hashedOldPassword);
            
            /*
            if (validOldPassword){
                
                System.out.println(hashedOldPassword);
                
                // Hash the new password first.
                PasswordHasher hasher2 = new PasswordHasher();
                String hashedNewPassword = hasher2.toHexString(hasher2.getSHA(newPass));
                
                userSB.getUsers().setPassword(hashedNewPassword);
                
                boolean validUpdate = userSB.updateUser();
                
                if (validUpdate){
                    stateSB.setProfileActiveIndex(0);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Password telah diganti!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Password tidak berhasil diganti!"));
                }
            } else {
                stateSB.setProfileActiveIndex(1);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Password lama anda salah!"));
            }
            */
            
            if (validOldPasswordSession){
                
                System.out.println(hashedOldPassword);
                
                // Hash the new password first.
                PasswordHasher hasher2 = new PasswordHasher();
                String hashedNewPassword = hasher2.toHexString(hasher2.getSHA(newPass));
                
                //userSB.getUsers().setPassword(hashedNewPassword);
                loginUser.setPassword(hashedNewPassword);
                
                //boolean validUpdate = userSB.updateUser();
                boolean validUpdateSession = userSession.updateUser(loginUser);
                
                /*
                if (validUpdate){
                    stateSB.setProfileActiveIndex(0);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Password telah diganti!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Password tidak berhasil diganti!"));
                }
                */
                
                if (validUpdateSession){
                    //stateSB.setProfileActiveIndex(0);
                    stateBean.setProfileActiveIndex(0);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Password telah diganti!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Password tidak berhasil diganti!"));
                }
            } else {
                //stateSB.setProfileActiveIndex(1);
                stateBean.setProfileActiveIndex(1);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Password lama anda salah!"));
            }
            
            return "profil";
        } catch (Exception e){
            return "profil";
        }
    }
    
    
    
    
    
    
    // MIGRATION TO SESSION

    public Users getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(Users loginUser) {
        this.loginUser = loginUser;
    }

    public Users getRegistrationUser() {
        return registrationUser;
    }

    public void setRegistrationUser(Users registrationUser) {
        this.registrationUser = registrationUser;
    }

    public Users getSearchUser() {
        return searchUser;
    }

    public void setSearchUser(Users searchUser) {
        this.searchUser = searchUser;
    }

    public StateManagedBean getStateBean() {
        return stateBean;
    }

    public void setStateBean(StateManagedBean stateBean) {
        this.stateBean = stateBean;
    }
    
    
    
    
    // PROFILE FOR EMPLOYEE-SEARCHED USER, GETTER SETTER

    public String getSearchUserUsername() {
        try {
            return searchUser.getUsername();
        } catch (Exception e){
            return "";
        }
        
    }

    public String getSearchUserNamaLengkap() {
        try {
            return searchUser.getNamaLengkap();
        } catch (Exception e){
            return "";
        }
        
    }

    public String getSearchUserAlamat() {
        try {
            return searchUser.getAlamat();
        } catch (Exception e){
            return "";
        }
        
    }
    

    public String getSearchUserEmail() {
        try {
            return searchUser.getEmail();
        } catch (Exception e){
            return "";
        }
        
    }

    public String getSearchUserNoHp() {
        try {
            return searchUser.getNoHp();
        } catch (Exception e){
            return "";
        }
        
    }

    public String getSearchUserNik() {
        try {
            return searchUser.getNik();
        } catch (Exception e){
            return "";
        }
        
    }

    public void setSearchUserUsername(String searchUserUsername) {
        this.searchUserUsername = searchUserUsername;
    }

    public void setSearchUserNamaLengkap(String searchUserNamaLengkap) {
        this.searchUserNamaLengkap = searchUserNamaLengkap;
    }

    public void setSearchUserAlamat(String searchUserAlamat) {
        this.searchUserAlamat = searchUserAlamat;
    }
    

    public void setSearchUserEmail(String searchUserEmail) {
        this.searchUserEmail = searchUserEmail;
    }

    public void setSearchUserNoHp(String searchUserNoHp) {
        this.searchUserNoHp = searchUserNoHp;
    }

    public void setSearchUserNik(String searchUserNik) {
        this.searchUserNik = searchUserNik;
    }

    public String getSearchByName() {
        return searchByName;
    }

    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }
    
    public void printValue(){
        System.out.println(searchByName);
        System.out.println(searchByNameQuery);
    }
}
