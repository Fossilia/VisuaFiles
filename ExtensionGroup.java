import java.util.*;
import java.io.File;

public class ExtensionGroup implements Comparator<ExtensionGroup>{
	private String name;
	ArrayList<File> files = new ArrayList<File>();
	private int count;
	private long size;

	public ExtensionGroup(){
		this.name = null;
	}

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

	public void sortFiles(){
		Collections.sort(files, new FileSortbySize());
	}

	public int compare(ExtensionGroup a, ExtensionGroup b){
		return (int)(b.getSize() - a.getSize()); //cast to int since compare only accepts that
	}

	public void printFiles(int num){
		int limiter;
		if(num>count){
			limiter = count;
		}
		else{
			limiter = num;
		}
		for(int i=0; i<limiter; i++){
			System.out.println(files.get(i).getName()+" size:"+FileScannerLauncher.convertSize(files.get(i).length()));
		}
	}

	public void incCount(){
		count++;
	}
}