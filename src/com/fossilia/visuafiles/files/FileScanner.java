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
	private long size; //total size of all files
	private int files; //num of files
	private int folders; //num of folders
	private int percent;

	private File filePath; //path to scan

	private FileGroupList fileGroups; //file group list
	private ExtensionGroupList extensionGroups; //extenion group list

	private ArrayList<File> filesList = new ArrayList<File>();
	private ArrayList<Group> extensionList = new ArrayList<Group>();
	private HashMap<String, Group> extensionListHash = new HashMap<String, Group>();

	/**
	 * Constructor with no parameters
	 */
	public FileScanner(){
		size = 0;
		files = 0;
		folders = 0;
		percent = -1;
		fileGroups = new FileGroupList();

	}

	/**
	 * Takes a path to start scan from different constructor
	 * @param path path to scan in String form
	 */
	public void scanFolder(String path){
		filePath = new File(path);
		double usedSpace = filePath.getTotalSpace()-filePath.getUsableSpace();
		scanFolder(filePath, filePath, usedSpace);
	}

	/**
	 * Takes a path to start scan from different constructor
	 * @param path path to scan in File form
	 */
	public void scanFolder(File path){
		double usedSpace = path.getTotalSpace()-path.getUsableSpace();
		filePath = path;
		scanFolder(path, path, usedSpace);
	}

	/**
	 * Scans a path and create a hash table of extensions (adds a extensions when it is first found) then adds files
	 * of the same extension to that created group
	 * @param basePath the original path
	 * @param path the current path
	 * @param usedSpace the amount of space used up on this drive/directory used to get file scanning progress
	 */
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

	/**
	 * Creates extension group arraylist from extension hash list (used fro scanning because look up is faster)
	 */
	public void createExtensionGroups(){
		//if(HashImp) extensionList = new ArrayList<Group>(extensionListHash.values());
		if(HashImp) extensionGroups = new ExtensionGroupList(new ArrayList<>(extensionListHash.values()));
	}

	/**
	 * prints num files of all the files scanned
	 * @param num
	 */
	public void printFiles(int num){
		for(int i=0; i<num; i++){
			System.out.println(filesList.get(i)+" size:"+filesList.get(i).length()/(1024.0*1024.0*1024.0));
		}
	}

	/**
	 * Creates file groups from path to text files and generated extension group list
	 * @throws IOException
	 */
	public void createGroups() throws IOException {
		fileGroups = new FileGroupList("DATA", extensionGroups);
	}

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

	public File getFilePath() {
		return filePath;
	}

	public void setFilePath(File filePath) {
		this.filePath = filePath;
	}

}