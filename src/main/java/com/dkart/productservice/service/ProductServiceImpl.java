package com.dkart.productservice.service;

import com.dkart.productservice.entity.Category;
import com.dkart.productservice.entity.Inventory;
import com.dkart.productservice.entity.Price;
import com.dkart.productservice.entity.Product;
import com.dkart.productservice.exception.ProductAlreadyExistsException;
import com.dkart.productservice.exception.ProductNotFoundException;
import com.dkart.productservice.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

   // private static final Map<String,List<Product>> cache=new HashMap<>();
    private ConcurrentMapCacheManager cacheManager;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
   @CachePut(value="productsCache",key = "#product.productId") //updating cache
    public Product addProduct(Product product,String categoryName) {
        Category category=categoryService.checkForCategory(categoryName);
        product.setCategory(category);
        checkForExistingProduct(product.getName());
        return productRepository.save(product);
    }

    private void checkForExistingProduct(String productName) {
        if(productRepository.findProductByName(productName).isPresent()){
            throw new ProductAlreadyExistsException("Product "+ productName+" already exists");
        }
    }


    @Override
    @CachePut(value="productsCache",key = "#products.productId") //updating cache
    public List<Product> addProducts(List<Product> products, String categoryName) {
        Category category=categoryService.checkForCategory(categoryName);
        List<Product> productSaved=new ArrayList<>();
         products.forEach(product->{
                    product.setCategory(category);
                    checkForExistingProduct(product.getName());
                    productSaved.add(productRepository.save(product));

                });
         return productSaved;
    }



    @Override
    @Cacheable(value = "productsCache",key = "'allProducts'")
    public List<Product> getAllProducts() {
        List<Product> products=new ArrayList<>();
               products = productRepository.findAll();
            if(products.isEmpty()){
                throw new ProductNotFoundException("No Products Found in DB");
            }
        return products;
    }

//Business Requirement
    @Override
    public List<Product> getAllProductsByCategoryAndPrice(String categoryName, BigDecimal minAmount,BigDecimal maxAmount,String currency) {
        categoryService.checkForCategory(categoryName);
        List<Product> products=getAllProducts();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found in any category ");
        }

        products = products.stream()
                .filter(product -> product.getPrice().getCurrency().equals(currency))
                .filter(product -> product.getPrice().getAmount().compareTo(minAmount) > 0 && product.getPrice().getAmount().compareTo(maxAmount) < 0)
                .filter(product -> product.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .filter(product -> product.getInventory().getAvailable() > 2)
                .collect(Collectors.toList());
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found for category "+categoryName+"and amount btwn"+minAmount+" and "+maxAmount);
        }
        return products;
    }

    @Override
    public List<Product> getAllProductsByCategory(String categoryName) {
        categoryService.checkForCategory(categoryName);
        List<Product> products=getAllProducts();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found in any category ");
        }

        products = products.stream()
                .filter(product -> product.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
                .filter(product -> product.getInventory().getAvailable() > 2)
                .toList();
        if(products.isEmpty()){
            throw new ProductNotFoundException("No products found for category "+categoryName);
        }
        return products;
    }


    @Override
    @CacheEvict(value="productsCache",key = "#products") //updating cache
    public void deleteProducts(List<String> products) throws BadRequestException {
        if (products.isEmpty()){
            throw new BadRequestException("Provide Product names to remove");
        }
         products.forEach(this::checkForExistingProduct);
         productRepository.deleteAllByNameIn(products);
    }

    @Override
    @CacheEvict(value="productsCache",key = "#productName") //updating cache
    public void deleteProduct( String productName) {
        checkForExistingProduct(productName);
        Product product = getProduct(productName).get();
        productRepository.delete(product);
    }
    
//    @Override
//    @CacheEvict(value="productsCache",key = "#products") //updating cache
//    public void deleteProducts( products) throws BadRequestException {
//        if (products.isEmpty()){
//            throw new BadRequestException("Provide Product names to remove");
//        }
//
//        productRepository.deleteAllByNameIn(products);
//    }

    @Override
    @CachePut(value="productsCache",key = "#products") //updating cache
    public List<Product> updateProductsByPrice(Map<String, Price> products) {
        List<Product> updatedProducts=new ArrayList<>();
        products.forEach((key, value) -> {
              Optional<Product> product=productRepository.findProductByName(key);
              if (product.isEmpty()){
                  throw new ProductNotFoundException("Product"+key+" Not found to update");
              }
              product.get().setPrice(value);
              updatedProducts.add(productRepository.save(product.get()));
        });
        return updatedProducts;
    }

    @Override
    @CachePut(value="productsCache",key = "#productName") //updating cache
    public Product updateProductByInventory(String productName, Inventory inventory) {
        Optional<Product> product = getProduct(productName);
        product.get().setInventory(inventory);
        return productRepository.save(product.get());
    }

    private Optional<Product> getProduct(String productName) {
        Optional<Product> product= productRepository.findProductByName(productName);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product doesnt exist");
        }
        return product;
    }

    @Override
    @CachePut(value="productsCache",key = "#productName") //updating cache
    public Product updateProductByPrice(String productName,Price price) {
        Optional<Product> product= getProduct(productName);
        product.get().setPrice(price);
        return productRepository.save(product.get());
    }
//    public List<Product> getAllProductsFromCache() {
//        Cache productsCache = cacheManager.getCache("productsCache");
//        List<Product> cachedProducts = new ArrayList<>();
//        if (productsCache != null) {
//            for (Cache.ValueWrapper valueWrapper : productsCache.getNativeCache()) {
//                if (valueWrapper != null && valueWrapper.get() instanceof Product) {
//                    cachedProducts.add((Product) valueWrapper.get());
//                }
//            }
//        }
//        return cachedProducts;
//    }
}
