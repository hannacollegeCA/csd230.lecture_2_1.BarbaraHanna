package csd230.lecture_2_1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testSaveAndFind() {
        Product product = new Product("Test Product", "Test Description", 99.99);
        productRepository.save(product);

        Optional<Product> found = productRepository.findById(product.getId());
        assertTrue(found.isPresent());
        assertEquals("Test Product", found.get().getName());
    }

    @Test
    void testFindAll() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    void testDelete() {
        Product product = new Product("Delete Product", "Delete Description", 10.00);
        productRepository.save(product);

        Long id = product.getId();
        productRepository.deleteById(id);

        Optional<Product> deleted = productRepository.findById(id);
        assertFalse(deleted.isPresent());
    }
}
