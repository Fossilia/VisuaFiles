import java.util.*; 
import java.lang.*; 
import java.io.*;

public abstract class Group{
	protected String name;
	protected ArrayList<File> files = new ArrayList<File>();
	protected long size;

	public Group(){
		this.name = null;
	}

	public Group(String name){
		this.name = name;
	}

	public void sortFiles(){
		Collections.sort(files, new FileSortbySize());
	}

	public void printFiles(int num){
		int limiter;
		int count = files.size();
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

	public void addFile(File f){
		files.add(f);
		size+=f.length();
	}

	public String getName(){
		return name;
	}

	public long getSize(){
		return size;
	}

	public int getCount(){
		return files.size();
	}

	public ArrayList<File> getFiles(){
		return files;
	}
}
