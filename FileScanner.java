import java.nio.file.*;
import java.io.IOException; 
import java.io.File;
import java.util.*;

public class FileScanner{
	private long size;
	private int files;
	private int folders;
	private int percent;
	private File filePath;
	private ArrayList<File> filesList = new ArrayList<File>();
	private ArrayList<ExtensionGroup> extensionList = new ArrayList<ExtensionGroup>();

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;

	}

	public void scanFolder(String path){
		filePath = new File(path);
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
			System.out.printf(VisuaFiles.getProgressBar(percent, 5)+" %3d%%\r",percent);
			//System.out.print(percent+"%\r");
			//System.out.print(percent+"% Currently scanning: "+path+" \r");
		}
		//System.out.printf("%.2f%%\n", percentageDone);

		String[] subDirs;
		if(path.isFile()){
			files++;
			//Extension fileEx = getExtension(path);
			if(getExtension(path)!=null){
				boolean found = false;
					for(ExtensionGroup e: extensionList){
						//System.out.println(fileEx.getName()+" "+e.getName()+" "+e.getCount());
						if(getExtension(path).equals(e.getName())){
							e.addFile(path);
							found = true;
						}
					}
					if(found == false){
						ExtensionGroup eg = new ExtensionGroup(getExtension(path));
						eg.addFile(path);
						extensionList.add(eg);
						
					}
				found = false;
			}
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
		Collections.sort(filesList, new FileSortbySize());
	}

	public void sortExtensionGroups(){
		Collections.sort(extensionList, new EGSortbySize());
	}

	public void printFiles(int num){
		for(int i=0; i<num; i++){
			System.out.println(filesList.get(i)+" size:"+filesList.get(i).length()/(1024.0*1024.0*1024.0));
		}
	}

	public void printExtensions(int num){
		Collections.sort(extensionList, new EGSortbySize());
		int limit = 0;
		if(num<=extensionList.size()){
			limit = num;
		}
		else{
			limit = extensionList.size();
		}
		for(int i=0; i<limit; i++){
			double percent = ((double)extensionList.get(i).getSize()/(double)size)*100;
			extensionList.get(i).sortFiles();
			System.out.printf("%10s%10s count: %5d size: %s\n", extensionList.get(i).getName(), VisuaFiles.getProgressBar(percent, 5), extensionList.get(i).getCount(), VisuaFiles.convertSize(extensionList.get(i).getSize()));
			//extensionList.get(i).printFiles(10);
			/*ArrayList<File> groupFiles = extensionList.get(i).getFiles();
			for(int k=0; i<10; k++){
				System.out.println(groupFiles.get(i));
			}*/
		}
	}

	public void sortExtensionGroupsFiles(){
		for(ExtensionGroup e: extensionList){
			e.sortFiles();
		}
	}

	public String getExtension(File f){
		String path = f.getPath();
		if(path.lastIndexOf(".")!=-1){
			return path.substring(path.lastIndexOf(".")+1);
		}
		else{
			return null;
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