/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Users;

/**
 *
 * @author louiv
 */
@Singleton
//@Stateful
public class UserSB {
    
    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    
    //private User user;
    
    private Users users;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /*
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    */
    
    
    //Database
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public boolean validateLogin(String username, String password) {
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUser AND u.password = :tempPass");
            q.setParameter("tempUser", username);
            q.setParameter("tempPass", password);
            
            List<Users> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                users = data.get(0);
                return true;
            }
            
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean nullUser(){
        try {
            users = null;
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean registerUser(){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUser");
            q.setParameter("tempUser", users.getUsername());
            
            List<Users> data = q.getResultList();
            
            if (data.size() == 0){
                em.persist(users);
                return true;
            } else {
                System.out.println("USername sudah ada.");
                return false;
            }
        } catch (Exception e){
            System.out.println("Username sudah dipakai.");
            return false;
        }
    }
    
    public boolean updateUser(){
        try {
            em.merge(users);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public List<Users> listSemuaUser(String query, String name){
        try {
            
            Query q;
            
            if (name.equals("")){
                q = em.createQuery("SELECT u FROM Users u ");
                
                return q.getResultList();
                
                
            } else {
                q = em.createQuery("SELECT u FROM Users u " + query);
                q.setParameter("tempName", name);
                q.setParameter("tempId", name);
                q.setParameter("tempNamaLengkap", name);
                q.setParameter("tempEmail", name);
                q.setParameter("tempNoHp", name);
                q.setParameter("tempNik", name);
                
                return q.getResultList();
            }
            
        } catch (Exception e){
            return null;
        }
        
    }
    
    
    public boolean checkUser(int id){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Users> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                users = data.get(0);
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    public boolean removeUser(){
        try {
            Users dataUser = em.find(Users.class, users.getId());
            em.remove(dataUser);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    // HANDLING PASSWORD CHANGE REQUEST
    public boolean verifyOldPassword(String hashedOldPassword){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUserName AND u.password = :tempHashedPassword");
            q.setParameter("tempUserName", users.getUsername());
            q.setParameter("tempHashedPassword", hashedOldPassword);
            
            List<Users> data = q.getResultList();
            
            if (data.isEmpty()){
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
}
