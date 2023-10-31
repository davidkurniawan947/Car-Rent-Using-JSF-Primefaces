/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import model.Orders;
import model.Users;

/**
 *
 * @author louiv
 */
@Singleton
//@Stateful
public class OrderSB {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "RentalPU")
    private EntityManager em;

    private Orders order;

    private Orders cancelOrder;

    private double total_harga;

    // State manager
    private boolean notConfirmed = false;
    private boolean Confirmed = false;
    private boolean Cancellable = false;
    private boolean deletable = false;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public double getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(double total_harga) {
        this.total_harga = total_harga;
    }

    public Orders getCancelOrder() {
        return cancelOrder;
    }

    public void setCancelOrder(Orders cancelOrder) {
        this.cancelOrder = cancelOrder;
    }

    // State Manager
    public boolean isNotConfirmed() {
        return notConfirmed;
    }

    public void setNotConfirmed(boolean notConfirmed) {
        this.notConfirmed = notConfirmed;
    }

    public boolean isConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(boolean Confirmed) {
        this.Confirmed = Confirmed;
    }

    public boolean isCancellable() {
        return Cancellable;
    }

    public void setCancellable(boolean Cancellable) {
        this.Cancellable = Cancellable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    // Methods
    public boolean addOrder() {
        try {
            em.persist(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Orders> listOrder() {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean nullOrder() {
        try {
            order = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean searchOrder(int id) {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.id = :tempId");
            q.setParameter("tempId", id);

            List<Orders> data = q.getResultList();

            if (data.isEmpty()) {
                notConfirmed = false;
                Confirmed = false;
                Cancellable = false;
                deletable = false;
                return false;
            } else {

                if (data.get(0).getStatus().equalsIgnoreCase("Pending")) {
                    notConfirmed = true;
                    Confirmed = false;
                    Cancellable = true;
                    deletable = true;
                } else if (data.get(0).getStatus().equalsIgnoreCase("Confirmed")) {
                    Confirmed = true;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                } else if (data.get(0).getStatus().equalsIgnoreCase("COMPLETED")) {
                    Confirmed = false;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                }

                order = data.get(0);
                return true;
            }
        } catch (Exception e) {
            System.out.println("Gagal search");
            return false;
        }
    }

    public boolean confirmOrder() {
        try {
            em.merge(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteOrder() {
        try {
            Orders orderDeletion = em.find(Orders.class, order.getId());
            em.remove(orderDeletion);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean completeOrder() {
        try {
            em.merge(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean cancelOrder() {
        try {
            em.merge(cancelOrder);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean nullCancelOrder() {
        try {
            cancelOrder = null;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void resetState() {
        notConfirmed = false;
        Confirmed = false;
        Cancellable = false;
        deletable = false;
    }

    public List<Orders> showOrderByUser(Users user) {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.userId.id = :tempId AND o.status NOT IN ('CANCELLED')");
            q.setParameter("tempId", user.getId());
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    //LIST ORDER WITH STATUS PENDING
    public List<Orders> listPendingOrders() {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.status IN ('PENDING')");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    //LIST ORDERS WITH STATUS CONFIRMED
    public List<Orders> listConfirmedOrders() {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.status IN ('CONFIRMED')");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    //LIST ORDERS WITH STATUS COMPLETED
    public List<Orders> listCompletedOrders() {
        try {
            Query q = em.createQuery("SELECT o FROM Orders o WHERE o.status IN ('COMPLETED')");
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
