package com.demo.productservice;

import com.demo.productservice.entity.Category;
import com.demo.productservice.entity.Product;
import com.demo.productservice.jpa.CategoryRepository;
import com.demo.productservice.jpa.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@SpringBootApplication
public class DataInitializer implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataInitializer.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String dataFile = "TestExampleFile.csv";

        try {
            this.processCSVFile(dataFile);
        } catch (FileNotFoundException e) {
            logger.error("file " + dataFile + "Not Found!");
            throw new RuntimeException(e);
        }
    }

    private void processCSVFile(String dataFile) throws FileNotFoundException {
        logger.debug("start to process file " + dataFile);

        List<List<String>> records = new ArrayList<>();

        Scanner scanner = new Scanner(new File(dataFile));
        while (scanner.hasNextLine()) {
            List<String> data = parseLine(scanner.nextLine());
//            System.out.println("-> " + data.size() + " " + data);

            String productCode = data.get(0);
            if (productCode.equals("PRODUCT_CODE")) {
                // skip headline
                continue;
            }
            records.add(data);
        }

        // Looping records and insert category
        HashMap<Integer, Category> cachedCategoryMap = new HashMap<>();
        for (List<String> data: records) {
            int categoryCode = Integer.parseInt(data.get(3));

            if (cachedCategoryMap.containsKey(categoryCode)) {
                continue;
            }

            String categoryName = data.get(4);

            // checking if category exists, if not exists insert new category first
            Category category = categoryRepository.findByCategoryCode(categoryCode);
            if (null == category) {
                category = new Category(categoryName, categoryCode);
                categoryRepository.save(category);
                logger.info("Inserted new category: " + category);
                cachedCategoryMap.put(categoryCode, category);
            }
        }

        // Looping records and insert product {
        for (List<String> data: records) {
            String productCode = data.get(0);
            String productName = data.get(1);
            int productCategoryCode = Integer.parseInt(data.get(2));

            Category category = null;
            if (cachedCategoryMap.containsKey(productCategoryCode)) {
                category = cachedCategoryMap.get(productCategoryCode);
            }

            // this should not happen, unless there is data in DB already, add for safe
            if (null == category) {
                category = categoryRepository.findByCategoryCode(productCategoryCode);
            }

            if (null == category) {
                logger.error("Can not find a valid category[category_code: " + productCategoryCode + "] for product [" + productCode + "," + productName+ "]");
                continue;
            }

            // check if product exists, if not exists insert new product
            Product product = productRepository.findByProductCode(productCode);
            if (null == product) {
                product = new Product(productName, productCode, category);
                productRepository.save(product);
                logger.info("Inserted new product: " + product);
            }

        }
        scanner.close();

    }

    private List<String> parseLine(String line) {
        List<String> data = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                data.add(rowScanner.next());
            }
        }
        return data;
    }

}
