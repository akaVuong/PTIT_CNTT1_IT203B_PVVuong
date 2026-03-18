package Entity;
import java.util.*;
abstract class Product implements IProduct {
    protected String id;
    protected String name;
    protected   double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}


class PhysicalProduct extends Product {
    private double weight;
    public PhysicalProduct(String id, String name, double price, double weight) {
        super(id, name, price);
        this.weight = weight;
    }

    @Override
    public void displayInfo(){
        System.out.println("physic Id: " + id + ", name: " + name + ", price: " + price + ", weight: "+ weight);
    }

    public void  setWeight(double weight){
        this.weight = weight;
    }
}

class DigitalProduct extends  Product{
    private double size;

    public DigitalProduct(String id, String name, double price, double size) {
        super(id, name, price);
        this.size = size;
    }

    @Override
    public void displayInfo(){
        System.out.println("physic Id: " + id + ", name: " + name + ", price: " + price + ", size: "+ size);
    }

    public void setSize(double size){
        this.size = size;
    }
}


class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;

    private ProductDatabase(){
        products = new ArrayList<>();
    }
    public static ProductDatabase getInstance(){
        if(instance == null){
            instance = new ProductDatabase();
        }
        return instance;
    }

    public  void addProduct(Product product){
        products.add(product);
    }

    public List<Product> getALL(){
        return products;
    }

    public Product findById(String id){
        for (Product p : products){
            if(p.getId().equals(id)){
                return p;
            }
            return null;
        }
    }

    public boolean delete (String id){
        Product p = findById(id);
        if(p != null){
            products.remove(p);
            return true;
        }
        return false;
    }
}

class ProductFactory{
    public  static  Product createProduct(int type, String id, String name, double price, double extra){
        if (type == 1) {
            return new PhysicalProduct(id, name, price,extra);
        }else if(type == 2){
            return  new DigitalProduct(id, name, price,extra);
        }else{
            return null;
        }
    }
}

