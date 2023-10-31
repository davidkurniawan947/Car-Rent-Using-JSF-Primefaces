/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

//import javax.inject.Named;
import controller.ProductSB;
import controller.StateManagerSB;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import model.Orders;
import model.Products;
import org.primefaces.model.file.UploadedFile;
import sessionbeans.OrderSessionBean;
import sessionbeans.ProductSessionBean;

/**
 *
 * @author louiv
 */
//@Named(value = "productManagedBean")
@ManagedBean(name = "ProductMB")
//@ApplicationScoped
@SessionScoped
public class ProductManagedBean implements Serializable {
    
    @EJB
    private ProductSB prodSB;
    
    @EJB
    private StateManagerSB stateSB;
    
    private List<Products> products;
    
    private List<Products> listProductMurah;
    
    private List<Products> listAllProd;
    
    private int carid;
    
    private boolean carNotNull = false;
    
    //Product Profile
    private int prodid;
    private String prodName;
    private int prodSeat;
    private String prodTransmisi;
    private String prodPlat;
    private double prodPrice;
    private String prodImage;
    
    
    //Add Product
    private String addName;
    private int addSeat;
    private String addTransmisi;
    private String addPlat;
    private double addPrice;
    private String addImage;
    private UploadedFile imageFile;
    
    
    // PRODUCT PROFILE - REFERRED FROM CATALOG
    private String referName;
    private int referSeat;
    private String referTransmisi;
    private double referPrice;
    private String referImage;
    
    
    // FOP SEARCHING CAR IN CATALOG PAGE
    private String searchCarName;
    private String orderProductsByPrice = "ORDER BY p.price ASC";
    
    // DISABLED DATES SYSTEM
    private List<Date> invalidDates = new ArrayList<Date>();
    
    // HARDCODED PATH
    // private String PARENT_PATH = "C:\\Kuliah\\Semester 5\\Pemrograman Aplikasi Bisnis\\Rental\\RentalWeb\\web\\images\\";
    
    
    // RELATIVE PATH
    private String PROJECT_DIRECTORY = FacesContext.getCurrentInstance().getExternalContext().getRealPath("images/");
    private String RELATIVE_PATH = PROJECT_DIRECTORY.replace("build", "") + "/";
    
    //Min Date
    private Date minDate = new Date(Calendar.getInstance().getTime().getTime() + TimeUnit.DAYS.toMillis(1));
    
    
    // MIGRATION TO SESSION BASED
    private Products generalProduct;
    private Products addProduct;
    private Products searchProduct;
    
    @ManagedProperty(value="#{StateManagedBean}")
    private StateManagedBean stateBean;
    
    @EJB
    private ProductSessionBean prodSession;
    
    @EJB
    private OrderSessionBean orderSession;
    
    
    /**
     * Creates a new instance of ProductManagedBean
     */
    
    public ProductManagedBean() {
    }

    public int getCarid() {
        return carid;
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }
    
    //Product Profile Gettter
    public int getProdid() {
        try {
            //return prodSB.getSearchProduct().getId();
            return searchProduct.getId();
        } catch (Exception e){
            return 0;
        }
    }

    public String getProdName() {
        try {
            //return prodSB.getSearchProduct().getName();
            return searchProduct.getName();
        } catch (Exception e){
            return "";
        }
        
    }

    public int getProdSeat() {
        try {
            //return prodSB.getSearchProduct().getSeat();
            return searchProduct.getSeat();
        } catch(Exception e){
            return 0;
        }
    }

    public String getProdTransmisi() {
        try {
            //return prodSB.getSearchProduct().getTransmisi();
            return searchProduct.getTransmisi();
        } catch(Exception e){
            return "";
        }
    }

    public String getProdPlat() {
        try {
            return searchProduct.getPlat();
        } catch (Exception e){
            return "";
        }
    }

    public double getProdPrice() {
        try {
            //return prodSB.getSearchProduct().getPrice();
            return searchProduct.getPrice();
        } catch (Exception e){
            return 0;
        }
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setProdSeat(int prodSeat) {
        this.prodSeat = prodSeat;
    }

    public void setProdTransmisi(String prodTransmisi) {
        this.prodTransmisi = prodTransmisi;
    }

    public void setProdPlat(String prodPlat) {
        this.prodPlat = prodPlat;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    public int getAddSeat() {
        return addSeat;
    }

    public void setAddSeat(int addSeat) {
        this.addSeat = addSeat;
    }

    public String getAddTransmisi() {
        return addTransmisi;
    }

    public void setAddTransmisi(String addTransmisi) {
        this.addTransmisi = addTransmisi;
    }

    public String getAddPlat() {
        return addPlat;
    }

    public void setAddPlat(String addPlat) {
        this.addPlat = addPlat;
    }

    public double getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(double addPrice) {
        this.addPrice = addPrice;
    }

    public String getAddImage() {
        return addImage;
    }

    public void setAddImage(String addImage) {
        this.addImage = addImage;
    }

    public UploadedFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(UploadedFile imageFile) {
        this.imageFile = imageFile;
    }

    public boolean isCarNotNull() {
        return carNotNull;
    }

    public void setCarNotNull(boolean carNull) {
        this.carNotNull = carNull;
    }

    public String getProdImage() {
        try {
            //return prodSB.getSearchProduct().getImage();
            return searchProduct.getImage();
        } catch (Exception e){
            return "";
        }
    }

    public void setProdImage(String prodImage) {
        this.prodImage = prodImage;
    }
    
    
    // PRODUCT PROFIL - REFERRED FROM CATALOG

    public String getReferName() {
        //return prodSB.getProduct().getName();
        return generalProduct.getName();
    }

    public int getReferSeat() {
        //return prodSB.getProduct().getSeat();
        return generalProduct.getSeat();
    }

    public String getReferTransmisi() {
        //return prodSB.getProduct().getTransmisi();
        return generalProduct.getTransmisi();
    }

    public Double getReferPrice() {
        //return prodSB.getProduct().getPrice();
        return generalProduct.getPrice();
    }

    public String getReferImage() {
        //return prodSB.getProduct().getImage();
        return generalProduct.getImage();
    }
    
    
    
    // FOR SEARCHING CAR IN CATALOG PAGE, GETTER SETTER

    public String getSearchCarName() {
        return searchCarName;
    }

    public void setSearchCarName(String searchCarName) {
        this.searchCarName = searchCarName;
    }

    public String getOrderProductsByPrice() {
        return orderProductsByPrice;
    }

    public void setOrderProductsByPrice(String orderProductsByPrice) {
        this.orderProductsByPrice = orderProductsByPrice;
    }
    
    public void storeCarName(){
        searchCarName = searchCarName;
        System.out.println(searchCarName);
        System.out.println(orderProductsByPrice);
    }
    
    
    //Methods
    public List<Products> getProducts() {
        try {
            if (searchCarName == null || searchCarName.isEmpty()){
                return prodSB.allDataProducts(orderProductsByPrice);
            } else {
                return prodSB.listProductsByName(searchCarName, orderProductsByPrice);
            }
        } catch (Exception e){
            return null;
        }
    }

    public List<Products> getListProductMurah() {
        return prodSB.cekHargaMurah();
    }
    
    public String searchCar(){
        
        //stateSB.setProductActiveIndex(1);
        stateBean.setProductActiveIndex(1);
        //System.out.println(stateSB.getProductActiveIndex());
        
        //boolean validCar = prodSB.checkProduct(carid);
        searchProduct = prodSession.checkProduct(carid);
        
        /*
        if (validCar){
            carNotNull = true;
            return "productsmaster";
        } else {
            carNotNull = false;
            prodSB.nullCar();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "Produk tidak ditemukan.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "productsmaster";
        }
        */
        
        if (searchProduct != null){
            carNotNull = true;
            return "productsmaster";
        } else {
            carNotNull = false;
            //prodSB.nullCar();
            searchProduct = null;
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR!", "Produk tidak ditemukan.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return "productsmaster";
        }
    }
    
    public String searchCarByList(Products product){
        stateBean.setProductActiveIndex(1);
        
        searchProduct = prodSession.checkProduct(product.getId());
        
        if (searchProduct != null){
            carid = product.getId();
            carNotNull = true;
            return "productsmaster";
        } else {
            carNotNull = false;
            searchProduct = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Gagal!", "Produk tidak ditemukan!"));
            return "productsmaster";
        }
    }
    
    public String updateCar(){
        try {
            
            //stateSB.setProductActiveIndex(1);
            stateBean.setProductActiveIndex(1);
            //System.out.println(stateSB.getProductActiveIndex());
            
            /*
            prodSB.getSearchProduct().setName(prodName);
            prodSB.getSearchProduct().setSeat(prodSeat);
            prodSB.getSearchProduct().setTransmisi(prodTransmisi);
            prodSB.getSearchProduct().setPrice(prodPrice);
            */
            
            searchProduct.setName(prodName);
            searchProduct.setSeat(prodSeat);
            searchProduct.setTransmisi(prodTransmisi);
            searchProduct.setPrice(prodPrice);
            searchProduct.setPlat(prodPlat);
            
            if (imageFile != null && !imageFile.getFileName().isEmpty()){
                InputStream is = imageFile.getInputStream();
            
                //File newFile = new File(PARENT_PATH+imageFile.getFileName());
                File newFile = new File(RELATIVE_PATH+imageFile.getFileName());
                
                OutputStream os = new FileOutputStream(newFile);

                int read = 0;
                byte[] bytes = new byte[1024];

                while((read = is.read(bytes)) != -1){
                    os.write(bytes, 0, read);
                }
                
                
                //prodSB.getSearchProduct().setImage(imageFile.getFileName());
                searchProduct.setImage(imageFile.getFileName());
            }
            
            //boolean validCar = prodSB.updateCar();
            boolean validCarSession = prodSession.updateCar(searchProduct);
            
            /*
            if (validCar){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil di-update!"));
                return "productsmaster";
            } else {
                return "productsmaster";
            }
            */
            
            if (validCarSession){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil di-update!"));
                return "productsmaster";
            } else {
                return "productsmaster";
            }
        } catch (Exception e){
            return "productsmaster";
        }
    }
    
    public String addCar() throws IOException{
        try {
            
            //stateSB.setProductActiveIndex(2);
            stateBean.setProductActiveIndex(2);
            
            //System.out.println(stateSB.getProductActiveIndex());
            
            System.out.println(imageFile.getFileName());
            
            InputStream is = imageFile.getInputStream();
            
            // Absolute Path
            //File newFile = new File(PARENT_PATH+imageFile.getFileName());
            
            
            // Relative Path
            File newFile = new File(RELATIVE_PATH+imageFile.getFileName());
            
            OutputStream os = new FileOutputStream(newFile);
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while ((read = is.read(bytes)) != -1){
                System.out.println("Printing image");
                os.write(bytes, 0, read);
            }
            
            addImage = imageFile.getFileName();
            
            if (addName.isEmpty() || addPrice == 0 || addSeat == 0 || addTransmisi.isEmpty() || addImage.isEmpty() || addPlat.isEmpty() || imageFile.getFileName() == null){
                if (addName.isEmpty()){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Nama belum diisi!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                if (addSeat == 0){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Jumlah seat belum diisi!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                if (addTransmisi.isEmpty()){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Jenis transmisi harus diisi!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                if (addPrice == 0){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Harga belum diisi!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                if (addPlat.isEmpty()){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Plat belum diisi!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                if (imageFile.getFileName() == null){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Data belum lengkap!", "Gambar harus ada!");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                
                return "productsmaster";
            } else {
                //prodSB.setProduct(new Products(addName, addPrice, addSeat, addTransmisi, addImage));
                addProduct = new Products(addName, addPrice, addSeat, addTransmisi, addPlat, addImage);
                
                //boolean validAddCar = prodSB.addCar();
                boolean validAddCarSession = prodSession.addCar(addProduct);
                
                /*
                if (validAddCar){
                    addName = null;
                    addPrice = 0;
                    addSeat = 0;
                    addTransmisi = null;
                    addImage = null;
                    imageFile = null;
                    
                    prodSB.nullCar();
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil ditambahkan!"));
                    
                    return "productsmaster";
                } else {
                    System.out.println("Gagal");
                    return "productsmaster";
                }
                */
                
                if (validAddCarSession){
                    addName = null;
                    addPrice = 0;
                    addSeat = 0;
                    addTransmisi = null;
                    addImage = null;
                    imageFile = null;
                    
                    //prodSB.nullCar();
                    addProduct = null;
                    
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Berhasil!", "Data berhasil ditambahkan!"));
                    
                    return "productsmaster";
                } else {
                    System.out.println("Gagal");
                    return "productsmaster";
                }
            }
        } catch (Exception e){
            System.out.println(e);
            return "productsmaster";
        }
    }
    
    public String removeCar(){
        try {
            //stateSB.setProductActiveIndex(0);
            stateBean.setProductActiveIndex(0);
            
            /*
            if (prodSB.getSearchProduct() != null){
                
                boolean validObject = prodSB.removeCar();
                
                if (validObject){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Data berhasil dihapus.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    prodSB.nullCar();
                    return "productsmaster";
                } else {
                    System.out.println("Gagal maning");
                    return "productsmaster";
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Data produk masih kosong!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "productsmaster";
            }
            */
            
            if (searchProduct != null){
                
                //boolean validObject = prodSB.removeCar();
                boolean validRemoveSession = prodSession.removeCar(searchProduct);
                
                /*
                if (validObject){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Data berhasil dihapus.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    prodSB.nullCar();
                    return "productsmaster";
                } else {
                    System.out.println("Gagal maning");
                    return "productsmaster";
                }
                */
                
                if (validRemoveSession){
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Data berhasil dihapus.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    //prodSB.nullCar();
                    searchProduct = null;
                    return "productsmaster";
                } else {
                    System.out.println("Gagal maning");
                    return "productsmaster";
                }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error!", "Data produk masih kosong!");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return "productsmaster";
            }
        } catch (Exception e){
            System.out.println(e);
            return "productsmaster";
        }
    }
    
    public String printCar(Products product){
        System.out.println(product.toString());
        return "products";
    }
    
    public String referCar(Products product){
        try {
            //prodSB.setProduct(product);
            //System.out.println(product.toString());
            setGeneralProduct(product);
            //System.out.println("GenProd: " + generalProduct.toString());
            
            // Get product data with confirmed status.
            
            invalidDates.clear();
            List<Orders> confirmedProduct = orderSession.checkConfirmedStatus(product.getId());
            
            // Add list of disabled dates from the selected product.

            if (confirmedProduct != null){
                for (int a = 0; a < confirmedProduct.size(); a++){
                    long differenceInMilli = confirmedProduct.get(a).getTanggalSelesai().getTime() - confirmedProduct.get(a).getTanggalMulai().getTime();
                    long days = TimeUnit.DAYS.convert(differenceInMilli, TimeUnit.MILLISECONDS);

                    invalidDates.add(new Date(confirmedProduct.get(a).getTanggalMulai().getTime()));

                    for (int i = 0; i < days; i++){
                        Date inbetweenDates = new Date(confirmedProduct.get(a).getTanggalMulai().getTime() + TimeUnit.DAYS.toMillis(i+1));
                        invalidDates.add(inbetweenDates);
                    }
                }
                return "productview";
            } else {
                return "productview";
            }
            
        } catch (Exception e){
            System.out.println(e);
            return "products";
        }
    }
    
    //Date

    public Date getMinDate() {
        return minDate;
    }
    
    
    
    
    
    // MIGRATION TO SESSION BASED

    public Products getGeneralProduct() {
        return generalProduct;
    }

    public void setGeneralProduct(Products generalProduct) {
        this.generalProduct = generalProduct;
    }

    public Products getAddProduct() {
        return addProduct;
    }

    public void setAddProduct(Products addProducts) {
        this.addProduct = addProducts;
    }

    public Products getSearchProduct() {
        return searchProduct;
    }

    public void setSearchProducts(Products searchProducts) {
        this.searchProduct = searchProducts;
    }

    public StateManagedBean getStateBean() {
        return stateBean;
    }

    public void setStateBean(StateManagedBean stateBean) {
        this.stateBean = stateBean;
    }
    
    
    public void verifyProductIsNotNull() throws IOException{
        if (generalProduct == null){
            FacesContext.getCurrentInstance().getExternalContext().redirect("products.xhtml");
        }
    }

    public List<Date> getInvalidDates() {
        return invalidDates;
    }

    public void setInvalidDates(List<Date> invalidDates) {
        this.invalidDates = invalidDates;
    }

    public List<Products> getListAllProd() {
        return prodSession.listAllProd();
    }

  
}
