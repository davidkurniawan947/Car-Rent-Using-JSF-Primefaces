/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Users;

/**
 *
 * @author louiv
 */
@Stateless
public class UserSessionBean {
    
    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public Users validateLogin(String username, String password) {
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUser AND u.password = :tempPass");
            q.setParameter("tempUser", username);
            q.setParameter("tempPass", password);
            
            List<Users> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                return data.get(0);
            }
            
        } catch (Exception e){
            System.out.println("Exception");
            return null;
        }
    }
    
    public boolean registerUser(Users registrationUser){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUser");
            q.setParameter("tempUser", registrationUser.getUsername());
            
            List<Users> data = q.getResultList();
            
            if (data.size() == 0){
                em.persist(registrationUser);
                return true;
            } else {
                System.out.println("Username sudah ada.");
                return false;
            }
        } catch (Exception e){
            System.out.println("Username sudah dipakai.");
            return false;
        }
    }
    
    public boolean updateUser(Users updateUser){
        try {
            em.merge(updateUser);
            return true;
        } catch (Exception e){
            return false;
        }
    }
    
    public Users checkUser(int id){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.id = :tempId");
            q.setParameter("tempId", id);
            
            List<Users> data = q.getResultList();
            
            if (data.isEmpty()){
                return null;
            } else {
                return data.get(0);
            }
        } catch (Exception e){
            return null;
        }
    }
    
    public boolean removeUser(Users removeUser){
        try {
            Users dataUser = em.find(Users.class, removeUser.getId());
            em.remove(dataUser);
            return true;
        } catch (Exception e){
            System.out.println(e);
            return false;
        }
    }
    
    public boolean verifyOldPassword(Users user, String hashedOldPassword){
        try {
            Query q = em.createQuery("SELECT u FROM Users u WHERE u.username = :tempUserName AND u.password = :tempHashedPassword");
            q.setParameter("tempUserName", user.getUsername());
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
