import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*; // Import for BufferedImage
import javax.swing.border.LineBorder;

public class ProductWebsiteApp extends JFrame {

    // Components for the left panel (large display)
    private JLabel mainProductImage;
    private JLabel mainProductNameLabel;
    private JLabel mainProductPriceLabel;
    private JLabel mainProductBrandLabel;
    private JTextArea mainProductDescriptionArea;
    private JPanel detailPanel; // <--- DECLARE AS A CLASS FIELD HERE

    // List to hold product data
    private List<Product> products;

    public ProductWebsiteApp() {
        setTitle("Adidas Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Initialize product data
        products = getProductList();

        // Main panel using BorderLayout to separate left and right sections
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Left Panel (Main Product Display) ---
        detailPanel = new JPanel(); // <--- INITIALIZE HERE
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setPreferredSize(new Dimension(450, getHeight()));
        detailPanel.setBackground(new Color(245, 245, 245));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainProductImage = new JLabel();
        mainProductImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainProductImage.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        detailPanel.add(mainProductImage);
        detailPanel.add(Box.createVerticalStrut(20));

        mainProductNameLabel = new JLabel("Product Name");
        mainProductNameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        mainProductNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(mainProductNameLabel);
        detailPanel.add(Box.createVerticalStrut(5));

        mainProductBrandLabel = new JLabel("Brand");
        mainProductBrandLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        mainProductBrandLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(mainProductBrandLabel);
        detailPanel.add(Box.createVerticalStrut(10));

        mainProductPriceLabel = new JLabel("$0.00");
        mainProductPriceLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        mainProductPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(mainProductPriceLabel);
        detailPanel.add(Box.createVerticalStrut(20));

        mainProductDescriptionArea = new JTextArea(5, 30);
        mainProductDescriptionArea.setFont(new Font("Arial", Font.PLAIN, 16));
        mainProductDescriptionArea.setLineWrap(true);
        mainProductDescriptionArea.setWrapStyleWord(true);
        mainProductDescriptionArea.setEditable(false);
        mainProductDescriptionArea.setBackground(detailPanel.getBackground());
        mainProductDescriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        detailPanel.add(mainProductDescriptionArea);

        mainPanel.add(detailPanel, BorderLayout.WEST);

        // --- Right Panel (Product Grid) ---
        JPanel productGridPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        productGridPanel.setBackground(new Color(245, 245, 245));
        productGridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Populate the right panel with product cards
        for (Product product : products) {
            JPanel productCard = createProductCard(product);
            productGridPanel.add(productCard);
        }

        JScrollPane scrollPane = new JScrollPane(productGridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Initially display the first product
        if (!products.isEmpty()) {
            updateMainProductDisplay(products.get(0));
        }
    }

    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(220, 280));
        card.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        card.setBackground(Color.WHITE);

        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(getClass().getResource(product.getImagePath()));
            if (originalIcon.getImageLoadStatus() == MediaTracker.ERRORED || originalIcon.getImage() == null) {
                System.err.println("Error loading image for card: " + product.getImagePath());
                originalIcon = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
            }
        } catch (NullPointerException e) {
            System.err.println("Image path not found: " + product.getImagePath());
            originalIcon = new ImageIcon(new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB));
        }

        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        card.add(imageLabel, BorderLayout.NORTH);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(nameLabel);

        JLabel brandLabel = new JLabel(product.getBrand()); // Call getBrand() here
        brandLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        brandLabel.setForeground(Color.DARK_GRAY);
        brandLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(brandLabel);
        textPanel.add(Box.createVerticalStrut(3));

        JLabel priceLabel = new JLabel("$" + String.format("%.2f", (double)product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(Color.DARK_GRAY);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(priceLabel);
        textPanel.add(Box.createVerticalStrut(5));

        JLabel descriptionLabel = new JLabel(product.getShortDescription());
        descriptionLabel.setFont(new Font("Arial", Font.ITALIC, 9));
        descriptionLabel.setForeground(Color.GRAY);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(descriptionLabel);
        textPanel.add(Box.createVerticalGlue());

        card.add(textPanel, BorderLayout.CENTER);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateMainProductDisplay(product);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new LineBorder(new Color(0, 123, 255), 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
            }
        });

        return card;
    }

    private void updateMainProductDisplay(Product p) {
        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(getClass().getResource(p.getImagePath()));
            if (originalIcon.getImageLoadStatus() == MediaTracker.ERRORED || originalIcon.getImage() == null) {
                System.err.println("Error loading main display image: " + p.getImagePath());
                originalIcon = new ImageIcon(new BufferedImage(350, 350, BufferedImage.TYPE_INT_ARGB));
            }
        } catch (NullPointerException e) {
            System.err.println("Main image path not found: " + p.getImagePath());
            originalIcon = new ImageIcon(new BufferedImage(350, 350, BufferedImage.TYPE_INT_ARGB));
        }

        Image scaledImage = originalIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        mainProductImage.setIcon(new ImageIcon(scaledImage));

        mainProductNameLabel.setText(p.getName());
        mainProductBrandLabel.setText(p.getBrand()); // Call getBrand() here
        mainProductPriceLabel.setText("$" + String.format("%.2f", (double)p.getPrice()));
        mainProductDescriptionArea.setText(p.getDetailedDescription()); // Call getDetailedDescription() here

        // These now work because detailPanel is a class field
        detailPanel.revalidate();
        detailPanel.repaint();
    }

    private ArrayList<Product> getProductList() {
        ArrayList<Product> productList = new ArrayList<>();
        // Constructor: (name, price, brand, imagePath, description)
        productList.add(new Product("4DFWD PULSE SHOES", 160, "Adidas Originals", "img1.png", "This product is excluded from all promotional discounts and offers."));
        productList.add(new Product("FORUM MID SHOES", 100, "Adidas Basketball", "img2.png", "Experience comfort and iconic style with the Forum Mid. Perfect for everyday wear."));
        productList.add(new Product("SUPERNOVA SHOES", 150, "Adidas Running", "img3.png", "Lightweight and breathable running shoes designed for daily miles and superior cushioning."));
        productList.add(new Product("NMD CITY STOCK 2", 160, "Adidas Originals", "img4.png", "Urban style meets modern innovation in the NMD City Stock 2. A sleek design for the city."));
        productList.add(new Product("NMD CITY STOCK V2", 120, "Adidas Originals", "img5.png", "A refresh of the classic NMD design, offering enhanced comfort and a refined look."));
        productList.add(new Product("4DFWD PULSE ORANGE", 160, "Adidas Originals", "img6.png", "Stand out with the vibrant orange 4DFWD Pulse shoes. Designed for energy return."));
        productList.add(new Product("4DFWD PULSE GREEN", 160, "Adidas Originals", "img1.png", "A fresh take on the 4DFWD Pulse with striking green accents."));
        productList.add(new Product("FORUM LOW SHOES", 90, "Adidas Originals", "img2.png", "The low-cut version of the classic Forum, offering a versatile look for any occasion."));
        productList.add(new Product("ADIDAS ULTRABOOST", 180, "Adidas Running", "/images/img3.png", "The ultimate comfort and responsiveness for your daily runs or casual wear."));
        productList.add(new Product("GAZELLE SHOES", 80, "Adidas Originals", "/images/img4.png", "A timeless classic with a suede upper and iconic 3-Stripes."));
        productList.add(new Product("SAMBA OG SHOES", 75, "Adidas Originals", "/images/img5.png", "Originally designed for indoor soccer, the Samba is now a streetwear icon."));
        productList.add(new Product("ADIDAS OZWEEGO", 110, "Adidas Originals", "/images/img6.png", "Inspired by the 90s, the Ozweego blends retro vibes with modern comfort."));
        return productList;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProductWebsiteApp().setVisible(true);
        });
    }
}