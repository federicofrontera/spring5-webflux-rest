package ff.springframework.spring5webfluxrest.bootstrap;

import ff.springframework.spring5webfluxrest.domain.Category;
import ff.springframework.spring5webfluxrest.domain.Vendor;
import ff.springframework.spring5webfluxrest.repositories.CategoryRepository;
import ff.springframework.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadCategories();
    }

    private void loadCategories() {
        if (categoryRepository.count().block() == 0) {
            System.out.println("Loading category data...");
            categoryRepository.save(Category.builder().
                    description("Fruits")
                    .build());
            categoryRepository.save(Category.builder().
                    description("Nuts")
                    .build());
            categoryRepository.save(Category.builder().
                    description("Meats")
                    .build());
            categoryRepository.save(Category.builder().
                    description("Veggies")
                    .build());
            categoryRepository.save(Category.builder().
                    description("Breads")
                    .build());
            System.out.println("Finished loading " + categoryRepository.count().block() + " categories");
        }
    }

    private void loadVendors() {
        if (vendorRepository.count().block() == 0) {
            System.out.println("Loading vendor data...");
            vendorRepository.save(Vendor.builder()
                    .firstName("John")
                    .lastName("Smith")
                    .build());
            vendorRepository.save(Vendor.builder()
                    .firstName("Smoth")
                    .lastName("Jihn")
                    .build());
            vendorRepository.save(Vendor.builder()
                    .firstName("Rachel")
                    .lastName("Ferguson")
                    .build());
            vendorRepository.save(Vendor.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .build());
            vendorRepository.save(Vendor.builder()
                    .firstName("Jim")
                    .lastName("Doe")
                    .build());
            System.out.println("Finished loading " + vendorRepository.count().block() + " vendors");
        }
    }
}
