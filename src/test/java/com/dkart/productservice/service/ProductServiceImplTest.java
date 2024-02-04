package com.dkart.productservice.service;

import static org.junit.Assert.*;

import com.dkart.productservice.entity.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ProductServiceImplTest {

	@Autowired
    private static ProductService productService;



	private static List<Product> products=new ArrayList<>();
	@BeforeClass
	public static void setUp(){
	//	Price price=new Price("INR", BigDecimal.valueOf(100000),1L);
		Price price=new Price();
		price.setPriceId(1L);
		price.setCurrency("INR");
		price.setAmount(BigDecimal.valueOf(10000));
		Inventory inventory=new Inventory();
		inventory.setInventoryId(1L);
		inventory.setTotal(10L);
		inventory.setAvailable(4L);
		inventory.setReserved(2L);
		//Inventory inventory=new Inventory(10,4,2,1L);
		Set<Attribute> attributeSet=Set.of(new Attribute("Color","Black",1L),new Attribute(),new Attribute("Size","M",2L));
		Category category=new Category(1L,"electronics",null);
		Product product=new Product(1L,"Samsung S21 Ultra","Samsung","Description",price,inventory,attributeSet,category);
		Product productSaved = productService.addProduct(product, category.getCategoryName());
		products.add(productSaved);
	}

    @Test
	public void testProductServiceImpl() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddProduct() {
		//Price price=new Price("INR", BigDecimal.valueOf(100000),1L);
		Price price=new Price();
		price.setPriceId(1L);
		price.setCurrency("INR");
		price.setAmount(BigDecimal.valueOf(10000));
		Inventory inventory=new Inventory(10,4,2, 1L);
		Set<Attribute> attributeSet=Set.of(new Attribute("Color","Black",1L),new Attribute(),new Attribute("Size","M",2L));
		Category category=new Category(1L,"electronics",null);
		Product product=new Product(1L,"Samsung S20 Ultra","Samsung","Description",price,inventory,attributeSet,category);
		Product productSaved = productService.addProduct(product, category.getCategoryName());
		assertNotNull(productSaved);
		assertEquals(product.getName(),productSaved.getName());
		products.add(productSaved);
		assertEquals(2,products.size());

		//fail("Not yet implemented");
	}

	@Test
	public void testAddProducts() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllProducts() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllProductsByCategoryAndPrice() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAllProductsByCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProducts() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProduct() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProductsByPrice() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProductByInventory() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateProductByPrice() {
		fail("Not yet implemented");
	}


}
