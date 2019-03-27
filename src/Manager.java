
public class Manager {
	private String name;
	private String id;
	
	public Manager(){
		name = "";
		id = "";
	}
	
	public Manager(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	public void displayDetails(){
		System.out.println(name + "\t" + id);
	}
	
}
