package Resources;

public enum APIResources {
	AddPlaceAPI("place/add/json"),
	GetPlaceAPI("place/get/json"),
	DeletePlaceAPI("place/delete/json");
	private String resource;

	APIResources(String resource) {
		// TODO Auto-generated constructor stub
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
}
