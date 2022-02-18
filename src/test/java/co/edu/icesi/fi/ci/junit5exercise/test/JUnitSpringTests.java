package co.edu.icesi.fi.ci.junit5exercise.test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.icesi.fi.ci.junit5exercise.main.AppConfig;
import co.edu.icesi.fi.ci.junit5exercise.model.Order;
import co.edu.icesi.fi.ci.junit5exercise.service.OrderService;

@ContextConfiguration(classes = { AppConfig.class })
@ExtendWith(value = { SpringExtension.class }) 
class JUnitSpringTests {
	
	private OrderService os;
	private Order o;
	
	@Autowired
	public JUnitSpringTests(OrderService os) {
		this.os = os;
	}

	@BeforeAll
	public static void setUp() {
		System.out.println("-----> SETUP <-----");
	}
	
	@BeforeEach
	public void setUp1() {
		os.deleteOrders();
		o = new Order(123456, "INITIATED", "ABCD", "yes, this is a description", new Date());
	}
	

	@Test
	@DisplayName("Descripcion empieza con 'Description:'")
	public void testSampleServiceGetAccountDescription() {
		// Check if the return description has a 'Description:' string.
		os.createOrder(o);
		assertTrue(os.getOrderDescription(o.orderId).substring(0, 12).contains("Description:"));
	}

	@Test
	@DisplayName("Metodo para obtener codigos empieza con 'Acoount Code:'")
	public void testSampleServiceGetAccountCode() {
		os.createOrder(o);
		assertTrue(os.getOrderStringCode(o.orderId).substring(0, 13).contains("Account Code:"));
	}
	
	@Test
	@DisplayName("Creacion de metodos correcta")
	public void testSampleServiceCreateOder() {
		Order to = os.createOrder(o);
		
		assertAll("crear",
				() -> assertNotNull(to.getDescription()),
				() -> assertEquals("ABCD", to.getSecurityCode()),
				() -> assertNotNull(to.getOrderDate()),
				() -> assertEquals("INITIATED", to.getOrderStatus()),
				() -> assertNotNull(to.getOrderId())
				);
		
		 assertTrue(to.getOrderStatus().equals("INITIATED") || to.getOrderStatus().equals("SHIPPED")
				 || to.getOrderStatus().equals("CANCELED") || to.getOrderStatus().equals("WAREHOUSE"));
		 assertEquals(4, to.getSecurityCode().length());
	}
	
	@Test
	@DisplayName("Obtención de metodos correcta")
	public void testSampleServiceGetOder() {
		os.createOrder(o);
		Order to = os.getOrder(123456);
		assertAll("obtener",
				() -> assertEquals("yes, this is a description", to.getDescription()),
				() -> assertEquals("ABCD", to.getSecurityCode()),
				() -> assertNotNull(to.getOrderDate()),
				() -> assertEquals("INITIATED", to.getOrderStatus()),
				() -> assertNotNull(to.getOrderId())
				);
	}
	
	@Test
	@DisplayName("Eliminacion de ordenes individuales")
	public void testSampleServiceDeleteOder() {
		os.createOrder(o);
		assertEquals(o, os.getOrder(o.getOrderId()));
		os.deleteOrder(o.getOrderId());
		assertThrows(NullPointerException.class, () -> os.getOrder(o.getOrderId()));
	}
	
	@Test
	@DisplayName("Eliminacion de todas las ordenes")
	public void testSampleServiceDeleteOders() {
		os.createOrder(o);
		assertEquals(o, os.getOrder(o.getOrderId()));
		os.deleteOrder(o.getOrderId());
		assertTrue(os.getOrders().isEmpty());
	}
	
	
	
	

	@AfterAll
	private static void afterTest() {
		System.out.println("-----> DESTROY <-----");
	}
}
