
public class Product {
    private String name;
    private int price;
    private String imagePath;
    private String brand; // THIS FIELD IS CRUCIAL
    private String shortDescription;
    private String fullDescription;

    // THIS IS THE CORRECT CONSTRUCTOR - IT HAS 5 PARAMETERS
    public Product(String name, int price, String brand, String imagePath, String description) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.imagePath = imagePath;
        this.shortDescription = description;
        this.fullDescription = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getBrand() { // THIS GETTER IS CRUCIAL
        return brand;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    // This method name matches what you used in ProductWebsiteApp
    public String getDetailedDescription() {
        return fullDescription;
    }
}