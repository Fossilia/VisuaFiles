import java.nio.file.*;
import java.io.IOException; 
import java.io.File;
import java.util.*;

public class FileScanner implements Global{
	private VisuaFiles manager = null;
	private long size;
	private int files;
	private int folders;
	private int percent;
	private File filePath;
	private ArrayList<File> filesList = new ArrayList<File>();
	private ArrayList<Group> extensionList = new ArrayList<Group>();
	private HashMap<String, Group> extensionListHash = new HashMap<String, Group>();

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;

	}

	public FileScanner(VisuaFiles manager){
		this.manager = manager;
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;

		//extensionList = (ArrayList<ExtensionGroup>)extensionListHash.values();
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
			System.out.printf("%s %3d%%\r",manager.getProgressBar(percent, 5), percent);
			//System.out.print(percent+"%\r");
			//System.out.print(percent+"% Currently scanning: "+path+" \r");
		}
		//System.out.printf("%.2f%%\n", percentageDone);

		String[] subDirs;
		if(path.isFile()){ //if what is being scanned is a file
			files++;
			//Extension fileEx = getExtension(path);
			if(getExtension(path)!=null){ //if file has an extension
				if(HashImp){
					if(extensionListHash.containsKey(getExtension(path))){
						extensionListHash.get(getExtension(path)).addFile(path);
					}
					else{
						Group eg = new ExtensionGroup(getExtension(path)); //create a new one
						eg.addFile(path); //add file to file group
						extensionListHash.put(getExtension(path), eg);
					}
				}
				else{
					boolean found = false;
					for(Group e: extensionList){ //go through groupd
						//System.out.println(fileEx.getName()+" "+e.getName()+" "+e.getCount());		
						if(getExtension(path).equals(e.getName())){ //if a match is found, add file to that group
							e.addFile(path);
							found = true;
						}
					}
					if(found == false){ //if matching file group was not found
						Group eg = new ExtensionGroup(getExtension(path)); //create a new one
						eg.addFile(path); //add file to file group
						extensionList.add(eg); //add file group to file group group
						
					}
					found = false;
				}	
			}
			size+=path.length();
			return;
		}
		//System.out.println(folder);
		//at this point, file is a folder
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
		if(HashImp) extensionList = new ArrayList<Group>(extensionListHash.values());
		manager.mergeSort(extensionList);
		//Collections.sort(extensionList, new GroupSortbySize());
	}

	public void printFiles(int num){
		for(int i=0; i<num; i++){
			System.out.println(filesList.get(i)+" size:"+filesList.get(i).length()/(1024.0*1024.0*1024.0));
		}
	}

	public void printExtensions(int num){
		int limit = 0;
		if(num<=extensionList.size()){
			limit = num;
		}
		else{
			limit = extensionList.size();
		}
		for(int i=0; i<limit; i++){
			double percent = ((double)extensionList.get(i).getSize()/(double)size)*100;
			//extensionList.get(i).sortFiles();
			System.out.printf("%10s%10s count: %5d percent: %5.2f%% size: %s\n", extensionList.get(i).getName(), manager.getProgressBar(percent, 5), extensionList.get(i).getCount(), percent, manager.convertSize(extensionList.get(i).getSize()));
			//extensionList.get(i).printFiles(10);
			/*ArrayList<File> groupFiles = extensionList.get(i).getFiles();
			for(int k=0; i<10; k++){
				System.out.println(groupFiles.get(i));
			}*/
		}
	}

	public void sortExtensionGroupsFiles(){
		for(Group e: extensionList){
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

}