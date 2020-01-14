package com.fossilia.visuafiles.files;

import com.fossilia.visuafiles.group.ExtensionGroup;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.group.list.ExtensionGroupList;
import com.fossilia.visuafiles.group.list.FileGroupList;
import com.fossilia.visuafiles.util.Global;
import com.fossilia.visuafiles.util.StringManipulator;

import java.io.IOException;
import java.io.File;
import java.util.*;
import java.io.*;

public class FileScanner implements Global {
	private long size;
	private int files;
	private int folders;
	private int percent;
	private File filePath;

	private FileGroupList fileGroups;
	private ExtensionGroupList extensionGroups;

	private ArrayList<File> filesList = new ArrayList<File>();
	//private ArrayList<Group> groupList = new ArrayList<Group>();
	private ArrayList<Group> extensionList = new ArrayList<Group>();
	private HashMap<String, Group> extensionListHash = new HashMap<String, Group>();

	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;
		fileGroups = new FileGroupList();

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

		double percentageDone = ((double)size/usedSpace)*100;
		int newPercent = (int)percentageDone;
		if(percent<newPercent && newPercent<=100){
			percent = newPercent;
			System.out.printf("%s %3d%%\r",StringManipulator.getProgressBar(percent, 5), percent);
			//System.out.print(percent+"%\r");
			//System.out.print(percent+"% Currently scanning: "+path+" \r");
		}
		//System.out.printf("%.2f%%\n", percentageDone);

		String[] subDirs;
		if(path.isFile()){ //if what is being scanned is a file
			//Extension fileEx = StringManipulator.getExtension(path);
			if(StringManipulator.getExtension(path)!=null){ //if file has an extension
				files++;
				if(HashImp){
					if(extensionListHash.containsKey(StringManipulator.getExtension(path))){
						extensionListHash.get(StringManipulator.getExtension(path)).addFile(path);
					}
					else{
						Group eg = new ExtensionGroup(StringManipulator.getExtension(path)); //create a new one
						eg.addFile(path); //add file to file group
						extensionListHash.put(StringManipulator.getExtension(path), eg);
					}
				}
				else{
					boolean found = false;
					for(Group e: extensionList){ //go through groupd
						//System.out.println(fileEx.getName()+" "+e.getName()+" "+e.getCount());		
						if(StringManipulator.getExtension(path).equals(e.getName())){ //if a match is found, add file to that group
							e.addFile(path);
							found = true;
						}
					}
					if(found == false){ //if matching file group was not found
						Group eg = new ExtensionGroup(StringManipulator.getExtension(path)); //create a new one
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

	/*public void sortExtensionGroups(){
		if(HashImp) extensionList = new ArrayList<Group>(extensionListHash.values());
		if(HashImp) extensionGroups = new ExtensionGroupList(new ArrayList<Group>(extensionListHash.values()));
		extensionGroups.sort();
		Sorter.sortGroups(extensionList);
		//Collections.sort(extensionList, new com.fossilia.visuafiles.group.GroupSortbySize());
	}*/

	public void createExtensionGroups(){
		//if(HashImp) extensionList = new ArrayList<Group>(extensionListHash.values());
		if(HashImp) extensionGroups = new ExtensionGroupList(new ArrayList<Group>(extensionListHash.values()));
		System.out.println(extensionListHash.size()+" xx "+extensionGroups.getGroupList().size());
	}

	/*public void sortFileGroups(){
		fileGroups.sort();
		Sorter.sortGroups(groupList);
		//Collections.sort(extensionList, new com.fossilia.visuafiles.group.GroupSortbySize());
	}*/

	public void printFiles(int num){
		for(int i=0; i<num; i++){
			System.out.println(filesList.get(i)+" size:"+filesList.get(i).length()/(1024.0*1024.0*1024.0));
		}
	}

	/*public void printExtensions(int num){
		System.out.println(extensionList.size());
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
			System.out.printf("%10s%10s count: %5d percent: %5.2f%% size: %s\n", extensionList.get(i).getName(), StringManipulator.getProgressBar(percent, 5), extensionList.get(i).getCount(), percent, StringManipulator.convertSize(extensionList.get(i).getSize()));
			//extensionList.get(i).printFiles(10);
			/*ArrayList<File> groupFiles = extensionList.get(i).getFiles();
			for(int k=0; i<10; k++){
				System.out.println(groupFiles.get(i));
			}
		}
	}*/

	public void createGroups() throws FileNotFoundException, IOException{
		fileGroups = new FileGroupList("DATA", extensionGroups);
		/*BufferedReader br;
		File base = new File("DATA");
		String line;

		FileGroup other = new FileGroup("Other files");
		
		for(String name: base.list()){
			File file = new File("DATA/" +name);
			FileGroup fileGroup = new FileGroup(name);

			String[] words;

			for(Group e: extensionList){
				br = new BufferedReader(new FileReader(file));
				line = br.readLine();
				//System.out.println(e.getName());
				//boolean found = false;
				while(line!=null){
					words = line.split("\t");
					//System.out.println(words[0]+" "+e.getName().toUpperCase());
					if(words[0].equals(e.getName().toUpperCase())){
						fileGroup.addGroup(e);
						//found = true;
						//System.out.println("found");
						break;
					}
					line = br.readLine();
				}
				br.close();
			} 
			groupList.add(fileGroup);*/

		/*com.fossilia.visuafiles.group.FileGroup videoGroup = new com.fossilia.visuafiles.group.FileGroup("Video files");
		for(com.fossilia.visuafiles.group.Group e: extensionList){
			if(e.getName().equals("mkv") || e.getName().equals("mp4") || e.getName().equals("webm")){
				videoGroup.addGroup(e);
			}
		}
		groupList.add(videoGroup);
		*/
	}

	/*public void sortGroup(int num){
		groupList.get(num).sortFiles();
	}*/

	/*public void displayGroupFiles(int num){
		groupList.get(num).printFiles(100);
	}*/

	/*public void printFileGroups(int num){
		int limit = 0;
		if(num<=groupList.size()){
			limit = num;
		}
		else{
			limit = groupList.size();
		}
		for(int i=0; i<limit; i++){
			if(groupList.get(i).getSize()>0){
				double percent = ((double)groupList.get(i).getSize()/(double)size)*100;
			System.out.printf("%-20s%10s count: %5d percent: %5.2f%% size: %-10s\n", groupList.get(i).getName(), StringManipulator.getProgressBar(percent, 5), groupList.get(i).getCount(), percent, StringManipulator.convertSize(groupList.get(i).getSize()));
			}	
		}
	}*/


	/*public void sortExtensionGroupsFiles(){
		for(Group e: extensionList){
			e.sortFiles();
		}
	}*/

	public ExtensionGroupList getExtensionGroups() {
		return extensionGroups;
	}

	public void setExtensionGroups(ExtensionGroupList extensionGroups) {
		this.extensionGroups = extensionGroups;
	}

	public FileGroupList getFileGroups() {
		return fileGroups;
	}

	public void setFileGroups(FileGroupList fileGroups) {
		this.fileGroups = fileGroups;
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