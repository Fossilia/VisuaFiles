
public class Extension{
	private String name;
	private int count;

	public Extension(String name, int count){
		this.name = name;
		this.count = count;
	}

	public String getName(){
		return name;
	}

	public int getCount(){
		return count;
	}

	public void incCount(){
		count++;
	}
}