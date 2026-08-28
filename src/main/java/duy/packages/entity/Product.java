package duy.packages.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(columnDefinition = "NVARCHAR(255) NULL")
    private String productName;

    private double price;
    
    @Column(columnDefinition = "NVARCHAR(MAX) NULL")
    private String description;

    @Column(columnDefinition = "NVARCHAR(255) NULL")
    private String images;

    // Quan hệ 1-N với Category
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    public Product() {}

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImages() { return images; }
    public void setImages(String images) { this.images = images; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}