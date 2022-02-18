package co.edu.icesi.fi.ci.junit5exercise.service;

import java.util.ArrayList;

import co.edu.icesi.fi.ci.junit5exercise.model.Order;

public class OrderServiceImpl implements OrderService {
	
	private ArrayList<Order> orders;
	
	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public OrderServiceImpl() {
		orders = new ArrayList<Order>();
	}

	//dummy method
	public String getOrderDescription(int id) {
		Order existingOrder = getOrder(id);
		existingOrder.setDescription("Order for XYZ in units");
		return "Description: " + existingOrder.getDescription();
	}

	//dummy method
	public String getOrderStringCode(int id) {
		Order existingOrder = getOrder(id);
		existingOrder.setSecurityCode("XYZ");
		return "Account Code: " + existingOrder.getSecurityCode();
	}
	
	//dummy method
	public Order createOrder(Order order) {
		if (order != null) {
			if(order.getDescription() != null && order.getDescription() != "" &&
					order.getOrderDate() != null && order.getOrderStatus() != null && 
					order.getSecurityCode() != null && order.getSecurityCode().length() == 4 && 
					(order.getOrderStatus().equals("INITIATED") || order.getOrderStatus().equals("SHIPPED") ||
							order.getOrderStatus().equals("CANCELED") || order.getOrderStatus().equals("WAREHOUSE")))
			{
				orders.add(order);
			} else {
				throw new IllegalArgumentException();
			}
			
		} else {
			throw new NullPointerException();
		}
		return order;
	}
	
	//dummy method
	public Order getOrder(int id) {
		Order order = null;
		for (Order o : orders) {
			if (o.getOrderId() == id) order = o;
		}
		if (order == null) {
			throw new NullPointerException();
		} else {
			return order;
		}
		
	}
	
	public Order deleteOrder(int id) {
		Order order = getOrder(id);
		orders.remove(order);
		return order;
	}
	
	public void deleteOrders() {
		orders.clear();
	}
	
	
	
	
	
	

}
