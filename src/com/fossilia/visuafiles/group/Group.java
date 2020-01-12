package com.fossilia.visuafiles.group;

import com.fossilia.visuafiles.util.Sorter;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.*;
import java.lang.*; 
import java.io.*;

public abstract class Group{
	protected String name;
	protected ArrayList<File> files = new ArrayList<>();
	protected long size;

	public Group(){
		this.name = null;
	}

	public Group(String name){
		this.name = name;
	}

	public void sortFiles(){
		Sorter.sortFiles(files);
		//Collections.sort(files, new com.fossilia.visuafiles.files.FileSortbySize());
	}

	/*public void printFiles(int num){

		int limiter;
		int count = files.size();
		limiter = Math.min(num, count);
		for(int i=0; i<limiter; i++){
			System.out.println(files.get(i).getName()+" size:"+StringManipulator.convertSize(files.get(i).length()));
		}
		for(File f: files){
			if(f.length()>(1024*1024*1024)){
				System.out.println(f.getName()+" size:"+ StringManipulator.convertSize(f.length()));
			}
		}
	}*/

	public void printFiles(int num){
		int limit = Math.min(num, files.size());
		for(int i=0; i<limit; i++){
			if(files.get(i).length()>0){
				double percent = ((double)files.get(i).length()/(double)size)*100;
				String percentBar = StringManipulator.getProgressBar(percent, 5);
				String memorySize = StringManipulator.convertSize(files.get(i).length());
				System.out.printf("%s %5.2f%% %-80s size: %15s path: %s\n",percentBar, percent, files.get(i).getName(), memorySize, files.get(i).getAbsolutePath());
				//System.out.printf("%20s%10s count: %5d percent: %5.2f%% size: %s\n", groupList.get(i).getName(), percentBar, groupList.get(i).getCount(), percent, memorySize);
			}
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

	@Override
	public String toString() {
		String memorySize = StringManipulator.convertSize(getSize());
		return String.format("%10s count: %5d size: %s", getName(), getCount(), memorySize);
	}
}
