import java.util.*;
import java.io.File;

public class ExtensionGroup{
	private String name;
	ArrayList<File> files = new ArrayList<File>();
	private int count;
	private long size;

	public ExtensionGroup(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public int getCount(){
		return count;
	}

	public long getSize(){
		return size;
	}

	public ArrayList<File> getFiles(){
		return files;
	}

	public void addFile(File f){
		files.add(f);
		count++;
		size+=f.length();
	}

	public void incCount(){
		count++;
	}
}