
public class User {
	private String name;
	private String id;
	
	public User(){
		name = "";
		id = "";
	}
	
	public User(String name, String id){
		this.name = name;
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	
	//Testing Purposes
	public void displayDetails(){
		System.out.println(name + "\t" + id);
	}
}
