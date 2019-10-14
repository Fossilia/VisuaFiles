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
			System.out.println(folder+" : "+folder.length()/1024+" kb");
			return;
		}
		subDirs = folder.list();
		File item;
		folders++;
		for(String string : subDirs){
			item = new File(folder+"/"+string+"/");
			//System.out.println(item);
			//if(item.isFolder()){
				scanFolder(item);
			//}
		}
	}

	public int getNumberOfFolders(){
		return folders;
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

}