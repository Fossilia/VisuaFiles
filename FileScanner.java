import java.nio.file.*;
import java.io.IOException; 
import java.io.File;
import java.util.*;

public class FileScanner{
	private long size;
	private int files;
	private int folders;
	private int percent;
	private ArrayList<File> filesList = new ArrayList<File>();

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;

	}

	public void scanFolder(String path){
		File filePath = new File(path);
		//System.out.println("0%");
		double usedSpace = filePath.getTotalSpace()-filePath.getUsableSpace();
		scanFolder(filePath, filePath, usedSpace);
	}

	public void scanFolder(File path){
		double usedSpace = path.getTotalSpace()-path.getUsableSpace();
		scanFolder(path, path, usedSpace);
	}

	public void scanFolder(File basePath, File path, double usedSpace){

		double percentageDone = ((double)size/(double)usedSpace)*100;
		int newPercent = (int)percentageDone;
		if(percent<newPercent && newPercent<=100){
			percent = newPercent;
			System.out.printf(FileScannerLauncher.getProgressBar(percent, 5)+" %3d%%\r",percent);
			//System.out.print(percent+"%\r");
			//System.out.print(percent+"% Currently scanning: "+path+" \r");
		}
		//System.out.printf("%.2f%%\n", percentageDone);

		String[] subDirs;
		if(path.isFile()){
			files++;
			filesList.add(path);
			size+=path.length();
			if(path.length()>(1024*1024*1024)){ //shows files above 1 GB
				//System.out.println(path+" : "+path.length()/(1024*1024)+" mb");
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
			scanFolder(path, item, usedSpace);
		}
	}

	public void sort(){
		Collections.sort(filesList, new SortbySize());
	}

	public void printFiles(int num){
		for(int i=0; i<num; i++){
			System.out.println(filesList.get(i)+" size:"+filesList.get(i).length()/(1024.0*1024.0*1024.0));
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