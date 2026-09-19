package csd230.lecture_2_1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.javafaker.Faker;
import com.github.javafaker.Commerce;

import java.util.List;
import java.util.Optional;



@SpringBootApplication
public class Application implements CommandLineRunner {

    // Spring injects the repository automatically (Dependency Injection)
    private final ProductRepository productRepository;

    // Constructor injection: recommended by Spring for required dependencies
    public Application(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // CommandLineRunner: this method runs automatically when the application starts.
        // We use it to demonstrate CRUD operations without needing a Controller or API.

        // Create Faker instance to generate fake product data
        Faker faker = new Faker();
        Commerce cm = faker.commerce();
        com.github.javafaker.Number number = faker.number();

        // Create fake products
        String name = cm.productName();
        String description = cm.material();
        Product newProduct = new Product(name, description, number.randomDouble(2, 10, 100));

        name = cm.productName();
        description = cm.material();
        Product newProduct2 = new Product(name, description, number.randomDouble(2, 10, 100));

        name = cm.productName();
        description = cm.material();
        Product newProduct3 = new Product(name, description, number.randomDouble(2, 10, 100));

        name = cm.productName();
        description = cm.material();
        Product newProduct4 = new Product(name, description, number.randomDouble(2, 10, 100));

        // Saving products to the database using Spring Data JPA
        // Spring automatically generates the SQL behind save()
        productRepository.save(newProduct);
        productRepository.save(newProduct2);
        productRepository.save(newProduct3);
        productRepository.save(newProduct4);

        // Retrieve all products from the Database
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(System.out::println);

        // Find product by ID
        Optional<Product> productOptional = productRepository.findById(1L);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            System.out.println("Product found by ID: " + product);
        }


        // Custom query method: Spring generates the SQL based on the method name
        // findFirstByName → SELECT * FROM product WHERE name = ? LIMIT 1
        productOptional = Optional.ofNullable(productRepository.findFirstByName(newProduct.getName()));
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            System.out.println("First product with this name: " + product);
        }

        // Find all products by name
        // Custom query method: findAllByName → SELECT * FROM product WHERE name = ?
        List<Product> productsByName = productRepository.findAllByName(newProduct2.getName());
        productsByName.forEach(System.out::println);

        // Create and save another product
        Product newProduct5 = new Product("ssd", "100GB drive", 200.00);
        productRepository.save(newProduct5);

        // Retrieve all products again to confirm insertion
        allProducts = productRepository.findAll();
        allProducts.forEach(System.out::println);

        // Delete product with ID = 1
        productRepository.deleteById(1L);

        // Retrieve all products after deletion
        allProducts = productRepository.findAll();
        allProducts.forEach(System.out::println);
    }

}
