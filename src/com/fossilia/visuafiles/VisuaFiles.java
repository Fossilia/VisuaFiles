package com.fossilia.visuafiles;

import com.fossilia.visuafiles.files.FileScanner;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.Input;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

//import static com.fossilia.visuafiles.util.Input;

public class VisuaFiles{
	Scanner sc = null;
	FileScanner fs = null;
	File[] roots = null;
	String time;

	public VisuaFiles(){
		sc = new Scanner(System.in);
		fs = new FileScanner();
	}

	public void start()throws IOException{
		int input;
		roots = File.listRoots();
		printMenu();
		input = Input.getIntInput(0, roots.length);
		long startTime;
		long endTime;

		if(input == 0){ //custom path

			System.out.println("Input what path you want to scan:");
			String path = Input.getStringInput();
			startTime = System.currentTimeMillis();
			fs.scanFolder(path);
			endTime = System.currentTimeMillis();
			time = String.format("%-10d", endTime - startTime);
		}
		else{ //scan root
			startTime = System.currentTimeMillis();
			fs.scanFolder(roots[input-1]);
			endTime = System.currentTimeMillis();
			time = String.format("%-10d", endTime - startTime);
		}

		System.out.printf("Sorting file groups...\r");
		fs.sortExtensionGroups();
		try{
			fs.createGroups();
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}
		fs.sortFileGroups();
		
		printScannerOutput(fs);
	}

	public void printScannerOutput(FileScanner fs){
		System.out.printf("Folders scanned: %-8d\n", fs.getNumberOfFolders());
		System.out.printf("  Files scanned: %-8d\n", fs.getNumberOfFiles());
		System.out.printf("     Total size: %-8s\n", StringManipulator.convertSize(fs.getSize()));
		System.out.printf("      Scan time: "+time+"\n\n");
		fs.printFileGroups(28);
		System.out.println();
		fs.printExtensions(20);
		pickGroup(fs);

		String input = Input.getStringInput();
		while(!input.equals("exit")){
			pickGroup(fs);
		}
		//fs.sort();
	}

	public void printMenu(){
		System.out.println("Welcome to [VisuaFiles]\nPick what to scan:\n\n0. Scan a custom path.");
		File roots[] = File.listRoots();
		for(int i=0; i<roots.length; i++){
			double freeSpace = roots[i].getUsableSpace()/(1024.0*1024*1024);
			double totalSpace =  roots[i].getTotalSpace()/(1024.0*1024*1024);
			double usedSpace = totalSpace - freeSpace;
			double percentage = (usedSpace/totalSpace)*100;
			System.out.printf("%d. "+roots[i]+" [ %.2f GB free / %.2f GB total ] | %s %.2f%% used\n", i+1, freeSpace, totalSpace, StringManipulator.getProgressBar(percentage, 5), percentage);
		}
	}

	public void pickGroup(FileScanner fs){
		System.out.println("Type in the number corresponding to a group to view its files:");
		int input = Input.getIntInput(0, 100);
		fs.sortGroup(input);
		fs.displayGroupFiles(input);

	}

}