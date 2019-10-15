import java.nio.file.*;
import java.io.IOException; 
import java.io.File;

public class FileScanner{
	private long size;
	private int files;
	private int folders;

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
	}

	public void scanFolder(File folder){
		String[] subDirs;
		if(folder.isFile()){
			files++;
			size+=folder.length();
			if(folder.length()>(1024*1024*1024)){ //shows files above 1 GB
				System.out.println(folder+" : "+folder.length()/(1024*1024)+" mb");
			}
			return;
		}
		//System.out.println(folder);
		folders++;
		subDirs = folder.list();
		if(subDirs==null){ //checks if folder is empty
			return;
		}
		File item;
		for(String string : subDirs){
			//System.out.println(string);
			item = new File(folder+"/"+string+"/");
			scanFolder(item);
		}
	}

	public int getNumberOfFolders(){
		return folders-1;
	}

	public int getNumberOfFiles(){
		return files;
	}

	public long getSize(){
		return size;
	}

	public void printSizeKB(){
		System.out.printf("%.2f KB\n", size/(1024.0));
	}

	public void printSizeMB(){
		System.out.printf("%.2f MB\n", size/(1024.0*1024.0));
	}

	public void printSizeGB(){
		System.out.printf("%.2f GB\n", size/(1024.0*1024.0*1024.0));
	}

	//catch(Exception e){return;}

}