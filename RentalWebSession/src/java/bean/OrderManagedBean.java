/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import controller.OrderSB;
import controller.ProductSB;
import controller.UserSB;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import model.Orders;
import model.Products;
import model.Users;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import sessionbeans.OrderSessionBean;

/**
 *
 * @author louiv
 */
//@Named(value = "orderManagedBean")
@ManagedBean(name = "OrderManagedBean")
//@ApplicationScoped
@SessionScoped
//@ViewScoped
public class OrderManagedBean {

    /**
     * Creates a new instance of OrderManagedBean
     */
    
    @EJB
    private OrderSB orderSB;
    
    @EJB
    private UserSB userSB;
    
    @EJB
    private ProductSB prodSB;
    
    //DateList
    private List<java.util.Date> date_range;
    
    // Adding Orders
    private java.util.Date tanggal_mulai;
    private java.util.Date tanggal_selesai;
    private String status;
    private Products productId;
    private Users userId;
    
    //List all Orders
    private List<Orders> listOrders;
    
    
    //FILTERED ORDERS
    private List<Orders> listPendingOrders;
    private List<Orders> listConfirmedOrders;
    private List<Orders> listCompletedOrders;
    
    //Searching Orders
    private int searchOrderId;
    
    private int orderId;
    private Date orderStartDate;
    private Date orderEndDate;
    private Products orderProductId;
    private Users orderUserId;
    private String orderStatus;
    private double orderTotalHarga;
    private int orderDurasi;
    
    
    // KALKULASI TOTAL HARGA
    private double total_harga;
    
    //List Orders by User
    private List<Orders> dataOrdersByUser;
    
    //Built in state manager -> Used to control which button to appear in order confirmation page.
    private boolean notConfirmed = false;
    private boolean Confirmed = false;
    private boolean Cancellable = false;
    private boolean deletable = false;
    
    
    // MIGRATION TO SESSIO BASED
    private Orders order;
    private Orders searchOrder;
    
    @ManagedProperty(value="#{StateManagedBean}")
    private StateManagedBean stateBean;
    
    @ManagedProperty(value="#{ProductMB}")
    private ProductManagedBean prodBean;
    
    @ManagedProperty(value="#{UserManagedBean}")
    private UserManagedBean userBean;
    
    @ManagedProperty(value="#{EmployeeManagedBean}")
    private EmployeeManagedBean employeeBean;
    
    @EJB
    private OrderSessionBean orderSession;
    
    private Products referencedProduct;
    private Users referencedUser;
    
    private String confirmEmployee;
    private String completeEmployee;
    
    private BarChartModel barModel;
    
    public OrderManagedBean() {
    }
    
    
    //Adding Order: Setter Getters

    public java.util.Date getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(java.util.Date tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public java.util.Date getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(java.util.Date tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Products getProductId() {
        return productId;
    }

    public void setProductId(Products productId) {
        this.productId = productId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public List<java.util.Date> getDate_range() {
        return date_range;
    }

    public void setDate_range(List<java.util.Date> date_range) {
        this.date_range = date_range;
    }
    
    // List all orders

    public List<Orders> getListOrders() {
        return orderSB.listOrder();
    }
    
    //Searching Orders

    public int getSearchOrderId() {
        return searchOrderId;
    }

    public void setSearchOrderId(int searchOrderId) {
        this.searchOrderId = searchOrderId;
    }

    public int getOrderId() {
        try {
            //return orderSB.getOrder().getId();
            return searchOrder.getId();
        } catch (Exception e){
            return 0;
        }
    }

    public Date getOrderStartDate() {
        try {
            //return orderSB.getOrder().getTanggalMulai();
            return searchOrder.getTanggalMulai();
        } catch (Exception e){
            return null;
        }
    }

    public Date getOrderEndDate() {
        try {
            //return orderSB.getOrder().getTanggalSelesai();
            return searchOrder.getTanggalSelesai();
        } catch (Exception e){
            return null;
        }
    }

    public Products getOrderProductId() {
        try {
            //return orderSB.getOrder().getProductId();
            return searchOrder.getProductId();
        } catch (Exception e){
            return null;
        }
    }

    public Users getOrderUserId() {
        try {
            //return orderSB.getOrder().getUserId();
            return searchOrder.getUserId();
        } catch (Exception e){
            return null;
        }
    }

    public String getOrderStatus() {
        try {
            //return orderSB.getOrder().getStatus();
            return searchOrder.getStatus();
        } catch (Exception e){
            return "";
        }
    }

    public double getOrderTotalHarga() {
        try {
            //return orderSB.getOrder().getTotalHarga();
            return searchOrder.getTotalHarga();
        } catch (Exception e){
            return 0;
        }
    }

    public int getOrderDurasi() {
        try {
            long diffInMillies = (searchOrder.getTanggalSelesai().getTime() + TimeUnit.DAYS.toMillis(1)) - searchOrder.getTanggalMulai().getTime();
            long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            
            orderDurasi = (int) days;
            return orderDurasi;
        } catch (Exception e){
            return 0;
        }
    }
    
    
    
    // State Managers

    public boolean isNotConfirmed() {
        //return orderSB.isNotConfirmed();
        return notConfirmed;
    }

    public void setNotConfirmed(boolean notConfirmed) {
        this.notConfirmed = notConfirmed;
    }

    public boolean isConfirmed() {
        //return orderSB.isConfirmed();
        return Confirmed;
    }

    public void setConfirmed(boolean Confirmed) {
        this.Confirmed = Confirmed;
    }

    public boolean isCancellable() {
        //return orderSB.isCancellable();
        return Cancellable;
    }

    public void setCancellable(boolean Cancellable) {
        this.Cancellable = Cancellable;
    }

    public boolean isDeletable() {
        //return orderSB.isDeletable();
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
    
    
    
    
    //List orders by users

    public List<Orders> getDataOrdersByUser() {
        //return orderSB.showOrderByUser(userSB.getUsers());
        return orderSB.showOrderByUser(userBean.getLoginUser());
    }
    
    // FILTER ORDER BY STATUS

    public List<Orders> getListPendingOrders() {
        return orderSB.listPendingOrders();
    }

    public List<Orders> getListConfirmedOrders() {
        return orderSB.listConfirmedOrders();
    }

    public List<Orders> getListCompletedOrders() {
        return orderSB.listCompletedOrders();
    }
    
    

    // KALKULTASI TOTAL HARGA, GETTER SETTER
    
    
    
    public double getTotal_harga(){
        try {
            /*
            long millisecondDifference = Math.abs((date_range.get(1).getTime() + TimeUnit.DAYS.toMillis(1)) - date_range.get(0).getTime());
            long diff = TimeUnit.DAYS.convert(millisecondDifference, TimeUnit.MILLISECONDS);
            
            total_harga = prodSB.getProduct().getPrice() * diff;
            */
            return total_harga;
        } catch (Exception e){
            return 0;
        }
        
    }
    
    public void setTotal_harga(double total_harga) {
        this.total_harga = total_harga;
    }
    
    public void calculateTotal(){
        try {
            long millisecondDifference = Math.abs((date_range.get(1).getTime() + TimeUnit.DAYS.toMillis(1)) - date_range.get(0).getTime());
            long diff = TimeUnit.DAYS.convert(millisecondDifference, TimeUnit.MILLISECONDS);
            
            //total_harga = prodSB.getProduct().getPrice() * diff;
            //setTotal_harga(prodBean.getGeneralProduct().getPrice() * diff);
            total_harga = prodBean.getGeneralProduct().getPrice() * diff;
            //orderSB.setTotal_harga(total_harga);
            //total_harga = 50000;
            
            System.out.println(total_harga);
        } catch (Exception e){
            System.out.println(e);
            System.out.println("Gagal maning;");
        }
    }

    // Methods
    public String addOrder() {
        try {
            System.out.println(date_range);
            
            tanggal_mulai = date_range.get(0);
            tanggal_selesai = date_range.get(1);
            
            System.out.println(tanggal_mulai);
            System.out.println(tanggal_selesai);
            
            // CONVERT THE TIME RANGE
            long diffInMillis = date_range.get(1).getTime() - date_range.get(0).getTime();
            long days = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            
            List<Date> specificProductInvalidDate = prodBean.getInvalidDates();
            
            for (int i = 0; i < days; i++){
                Date orderDateRange = new Date(tanggal_mulai.getTime() + TimeUnit.DAYS.toMillis(i));
                System.out.println("USER ORDER DATE: " + orderDateRange.toString());
                
                for (int a = 0; a < specificProductInvalidDate.size(); a++){
                    System.out.println("USER ORDER DATE: " + orderDateRange.toString() + "\n INVALID DATES: " + specificProductInvalidDate.get(0).toString());
                    if (orderDateRange.equals(specificProductInvalidDate.get(a))){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Sesuaikan tanggal sewa dengan tanggal yang tersedia!"));
                        return "productview";
                    }
                }
            }
            //System.out.println(referencedProduct.toString());
            //System.out.println(prodBean.getGeneralProduct().toString());
            //System.out.println(userBean.getLoginUser().toString());
            //orderSB.setOrder(new Orders(tanggal_mulai, tanggal_selesai, "PENDING", orderSB.getTotal_harga(), prodSB.getProduct(), userSB.getUsers()));
            order = new Orders(tanggal_mulai, tanggal_selesai, "PENDING", total_harga, prodBean.getGeneralProduct(), userBean.getLoginUser());

            //boolean validOrder = orderSB.addOrder();
            boolean validOrderSession = orderSession.addOrder(order);
            
            /*
            if (validOrder){
                orderSB.nullOrder();
                System.out.println("Berhasil menambahkan order");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Order berhasil dibuat, silahkan cek ID order anda."));
                
                return "historyorder";
            } else {
                System.out.println("Gagal");
                return "productview";
            }
            */
            
            if (validOrderSession){
                // orderSB.nullOrder();
                order = null;
                date_range.clear();
                System.out.println("Berhasil menambahkan order");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Order berhasil dibuat, silahkan cek ID order anda."));
                
                return "historyorder";
            } else {
                System.out.println("Gagal");
                return "productview";
            }
        } catch (Exception e){
            System.out.println(e);
            return "productview";
        }
    }
    
    public String searchOrder(){
        try {
            
            //boolean validOrders = orderSB.searchOrder(searchOrderId);
            searchOrder = orderSession.searchOrder(searchOrderId);
            
            /*
            if (validOrders){
                System.out.println("Order valid");
                return "ordersmaster";
            } else {
                orderSB.nullOrder();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Order tidak ditemukan!"));
                return "ordersmaster";
            }
            */
            
            if (searchOrder != null){
                System.out.println("Order valid");
                
                if (searchOrder.getStatus().equalsIgnoreCase("Pending")) {
                    notConfirmed = true;
                    Confirmed = false;
                    Cancellable = true;
                    deletable = true;
                } else if (searchOrder.getStatus().equalsIgnoreCase("Confirmed")) {
                    Confirmed = true;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                } else if (searchOrder.getStatus().equalsIgnoreCase("COMPLETED")) {
                    Confirmed = false;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                }
                
                return "ordersmaster";
            } else {
                //orderSB.nullOrder();
                resetState();
                searchOrder = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Order tidak ditemukan!"));
                return "ordersmaster";
            }
        } catch (Exception e){
            System.out.println(e);
            return "ordersmaster";
        }
    }
    
    public String searchOrderFromList(Orders order){
        
        searchOrder = orderSession.searchOrder(order.getId());
        
        if (searchOrder != null){
            
            searchOrderId = order.getId();
            
            if (searchOrder.getStatus().equalsIgnoreCase("Pending")) {
                    notConfirmed = true;
                    Confirmed = false;
                    Cancellable = true;
                    deletable = true;
                } else if (searchOrder.getStatus().equalsIgnoreCase("Confirmed")) {
                    Confirmed = true;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                } else if (searchOrder.getStatus().equalsIgnoreCase("COMPLETED")) {
                    Confirmed = false;
                    notConfirmed = false;
                    Cancellable = false;
                    deletable = true;
                }
                
                return "ordersmaster";
        } else {
            return "ordersmaster";
        }
    }
    
    public void resetState(){
        notConfirmed = false;
        Confirmed = false;
        Cancellable = false;
        deletable = false;
    }
    
    public String confirmOrder(){
        try {
            //orderSB.getOrder().setStatus("CONFIRMED");
            searchOrder.setStatus("CONFIRMED");
            searchOrder.setConfimedBy(employeeBean.getLoginEmployee());
            
            //boolean validUpdate = orderSB.confirmOrder();
            boolean validUpdateSession = orderSession.confirmOrder(searchOrder);
            
            /*
            if (validUpdate){
                orderSB.nullOrder();
                orderSB.resetState();
                System.out.println("Berhasil update");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil diupdate!"));
                
                return "ordersmaster";
            } else {
                return "ordersmaster";
            }
            */
            
            if (validUpdateSession){
                //orderSB.nullOrder();
                searchOrder = null;
                //orderSB.resetState();
                resetState();
                System.out.println("Berhasil update");
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil diupdate!"));
                
                return "ordersmaster";
            } else {
                return "ordersmaster";
            }
        } catch (Exception e){
            return "ordersmaster";
        }
    }
    
    public String deleteOrder(){
        try {
            //boolean validDeletion = orderSB.deleteOrder();
            boolean validDeletionSession = orderSession.deleteOrder(searchOrder);
            
            /*
            if (validDeletion){
                orderSB.nullOrder();
                orderSB.resetState();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil dihapus!"));
                
                System.out.println("Berhasil delete");
                return "ordersmaster";
            } else {
                System.out.println("Gagal delete");
                return "ordersmaster";
            }
            */
            
            if (validDeletionSession){
                searchOrder = null;
                resetState();
                //orderSB.nullOrder();
                //orderSB.resetState();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil dihapus!"));
                
                System.out.println("Berhasil delete");
                return "ordersmaster";
            } else {
                System.out.println("Gagal delete");
                return "ordersmaster";
            }
        } catch (Exception e){
            System.out.println(e);
            return "ordersmaster";
        }
    }
    
    public String completeOrder(){
        try {
            //orderSB.getOrder().setStatus("COMPLETED");
            searchOrder.setStatus("COMPLETED");
            searchOrder.setCompletedBy(employeeBean.getLoginEmployee());
            
            //boolean validComplete = orderSB.completeOrder();
            boolean validCompleteSession = orderSession.confirmOrder(searchOrder);
            
            /*
            if (validComplete){
                orderSB.nullOrder();
                orderSB.resetState();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Order berhasil diselesaikan!"));
                
                return "ordersmaster";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Gagal menyelesaikan order!"));
                
                return "ordersmaster";
            }
            */
            
            if (validCompleteSession){
                //orderSB.nullOrder();
                searchOrder = null;
                //orderSB.resetState();
                resetState();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Order berhasil diselesaikan!"));
                
                return "ordersmaster";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Gagal menyelesaikan order!"));
                
                return "ordersmaster";
            }
        } catch (Exception e){
            return "ordersmaster";
        }
    }
    
    public String cancelOrder(Orders order){
        try {
            //System.out.println(order);
            
            //orderSB.setCancelOrder(order);
            
            //orderSB.getCancelOrder().setStatus("CANCELLED");
            
            order.setStatus("CANCELLED");
            
            //boolean validCancel = orderSB.cancelOrder();
            boolean validCancelSession = orderSession.confirmOrder(order);
            
            /*
            if (validCancel){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Order telah dibatalkan."));
                
                return "historyorder";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gagal", "Order tidak berhasil dibatalkan."));
                
                return "historyorder";
            }
            */
            
            if (validCancelSession){
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Order telah dibatalkan."));
                
                return "historyorder";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gagal", "Order tidak berhasil dibatalkan."));
                
                return "historyorder";
            }
        } catch (Exception e){
            return "historyorder";
        }
    }
    
    public boolean checkIfOrderIsCancellable(Orders order){
        try {
            if (order.getStatus().equalsIgnoreCase("PENDING")){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            return false;
        }
    }
    
    // CANCEL ORDER BY EMPLOYEE
    public String cancelOrderByEmployee(){
        try {
            //orderSB.setCancelOrder(orderSB.getOrder());
            
            //orderSB.getCancelOrder().setStatus("CANCELLED");
            
            searchOrder.setStatus("CANCELLED");
            
            //boolean validCancelByEmployee = orderSB.cancelOrder();
            boolean validCancelEmpSession = orderSession.confirmOrder(searchOrder);
            
            /*
            if (validCancelByEmployee){
                orderSB.nullOrder();
                orderSB.nullCancelOrder();
                orderSB.resetState();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Order berhasil dibatalkan!"));
                
                return "ordersmaster";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Order tidak berhasil dibatalkan."));
                
                return "ordersmaster";
            }
            */
            
            if (validCancelEmpSession){
                //orderSB.nullOrder();
                //orderSB.nullCancelOrder();
                //orderSB.resetState();
                
                searchOrder = null;
                resetState();
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil", "Order berhasil dibatalkan!"));
                
                return "ordersmaster";
            } else {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Order tidak berhasil dibatalkan."));
                
                return "ordersmaster";
            }
            
        } catch (Exception e){
            return "ordersmaster";
        }
    }
    
    
    // MIGRATION TO SESSION

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Orders getSearchOrder() {
        return searchOrder;
    }

    public void setSearchOrder(Orders searchOrder) {
        this.searchOrder = searchOrder;
    }

    public StateManagedBean getStateBean() {
        return stateBean;
    }

    public void setStateBean(StateManagedBean stateBean) {
        this.stateBean = stateBean;
    }

    public ProductManagedBean getProdBean() {
        return prodBean;
    }

    public void setProdBean(ProductManagedBean prodBean) {
        this.prodBean = prodBean;
    }

    public UserManagedBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserManagedBean userBean) {
        this.userBean = userBean;
    }

    public OrderSessionBean getOrderSession() {
        return orderSession;
    }

    public void setOrderSession(OrderSessionBean orderSession) {
        this.orderSession = orderSession;
    }

    public Products getReferencedProduct() {
        return referencedProduct;
    }

    public void setReferencedProduct(Products referencedProduct) {
        this.referencedProduct = referencedProduct;
    }

    public Users getReferencedUser() {
        return referencedUser;
    }

    public void setReferencedUser(Users referencedUser) {
        this.referencedUser = referencedUser;
    }

    public EmployeeManagedBean getEmployeeBean() {
        return employeeBean;
    }

    public void setEmployeeBean(EmployeeManagedBean employeeBean) {
        this.employeeBean = employeeBean;
    }

    public String getConfirmEmployee() {
        try {
            return "ID: " + searchOrder.getConfimedBy().getId() + " - " + searchOrder.getConfimedBy().getUsername();
        } catch (Exception e){
            return "-";
        }
    }

    public String getCompleteEmployee() {
        try {
            return "ID: " + searchOrder.getCompletedBy().getId() + " - " + searchOrder.getCompletedBy().getUsername();
        } catch (Exception e){
            return "-";
        }
    }
    
    
    // CHART
    
    public BarChartModel createBarChart(){
        BarChartModel model = new BarChartModel();
        
        ChartSeries revenue = new ChartSeries();
        revenue.setLabel("Revenue");
        
        List<String> monthName = new ArrayList<>();
        monthName.add("January");
        monthName.add("February");
        monthName.add("March");
        monthName.add("April");
        monthName.add("May");
        monthName.add("June");
        monthName.add("July");
        monthName.add("August");
        monthName.add("September");
        monthName.add("October");
        monthName.add("November");
        monthName.add("December");
        
        
        for (int month = 1; month < monthName.size()+1; month++){
            
            List<Orders> availableDataByMonth = orderSession.completedOrderByMonth(month);
            
            double revenues = 0;
            
            if (availableDataByMonth != null){
                for (int i = 0; i < availableDataByMonth.size(); i++){
                    revenues += availableDataByMonth.get(i).getTotalHarga();
                }
                
                revenue.set(monthName.get(month-1), revenues);
            } else {
                System.out.println("Ga ketemu");
                revenue.set(monthName.get(month-1), revenues);
            }   
        }
        
        model.addSeries(revenue);
        
        return model;
    }
    
    public BarChartModel getBarModel() {
        
        barModel = createBarChart();
        /*
        ChartSeries boys = new ChartSeries();
        
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);
        
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 135);
        girls.set("2008", 120);
        */
        
        
        
        /*
        List<Orders> availableData = orderSession.listCompletedOrders();
        
        double totalRevenue = 0;
        
        if (availableData != null){
            for (int i = 0; i < availableData.size(); i++){
                totalRevenue += availableData.get(i).getTotalHarga();
            }
        }
        
        revenue.set("Revenue", totalRevenue);
        
        model.addSeries(revenue);
        /*
        model.addSeries(boys);
        model.addSeries(girls);
        */
        
        barModel.setTitle("Revenue - 2023");
        barModel.setLegendPosition("ne");
        barModel.setAnimate(true);
        barModel.setMouseoverHighlight(true);
        barModel.setShowPointLabels(true);
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Month");
        
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Revenue");
        yAxis.setTickAngle(-45);
        yAxis.setTickFormat("%'.0f");
        
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
    
}
