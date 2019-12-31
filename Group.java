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
		mergeSort(files);
		//Collections.sort(files, new FileSortbySize());
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
		/*for(File f: files){
			if(f.length()>(1024*1024*1024)){
				System.out.println(f.getName()+" size:"+FileScannerLauncher.convertSize(f.length()));
			}
		}*/
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

	public static void merge(ArrayList<File> S1, ArrayList<File> S2, ArrayList<File> S) {
		int i = 0, j = 0;
		while (i + j < S.size()) {
			if (j == S2.size() || (i < S1.size() && (S1.get(i).length()-S2.get(j).length())>0))
				S.set(i + j, S1.get(i++));
			else
				S.set(i + j, S2.get(j++));
		}
	}

	public static void mergeSort(ArrayList<File> S) {
		int n = S.size();
		if (n < 2)
			return;
		int mid = n / 2;
		// partition the string into two strings
		ArrayList<File> S1 = new ArrayList<File>(S.subList(0, mid));
		ArrayList<File> S2 = new ArrayList<File>(S.subList(mid, n));
		mergeSort(S1);
		mergeSort(S2);
		merge(S1, S2, S);
	}
}
