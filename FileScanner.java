import java.nio.file.*;
import java.io.IOException; 
import java.io.File;

public class FileScanner{
	private long size;
	private int files;
	private int folders;
	private int percent;

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = 0;
	}

	public void scanFolder(String path){
		System.out.println("0%");
		long usedSpace = new File(path).getTotalSpace()-new File(path).getUsableSpace();
		//System.out.println(usedSpace/(1024.0*1024.0*1024.0));
		scanFolder(new File(path), new File(path));
	}

	public void scanFolder(File basePath, File path){

		double usedSpace = basePath.getTotalSpace()-basePath.getUsableSpace();
		double percentageDone = ((double)size/(double)usedSpace)*100;
		//System.out.println(size+" "+usedSpace);
		//System.out.println(percentageDone);
		int newPercent = (int)percentageDone;
		if(percent<newPercent){
			percent = newPercent;
			System.out.println(percent+"%");
		}
		//System.out.printf("%.2f%%\n", percentageDone);

		String[] subDirs;
		if(path.isFile()){
			files++;
			size+=path.length();
			if(path.length()>(1024*1024*1024)){ //shows files above 1 GB
				System.out.println(path+" : "+path.length()/(1024*1024)+" mb");
			}
			return;
		}
		//System.out.println(folder);
		folders++;
		subDirs = path.list();
		if(subDirs==null){ //checks if folder is empty
			return;
		}
		File item;
		for(String string : subDirs){
			//System.out.println(string);
			item = new File(path+"/"+string+"/");
			scanFolder(path, item);
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