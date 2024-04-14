package org.maya.RestAssuredTest;

import java.util.List;

public class PlaceOrderReq {
	private List<CreateOrderReq> orders;

	public List<CreateOrderReq> getOrders() {
		return orders;
	}

	public void setOrders(List<CreateOrderReq> orders) {
		this.orders = orders;
	}
	
	
}
