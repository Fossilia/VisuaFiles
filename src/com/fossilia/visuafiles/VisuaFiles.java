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
	int fileViewerStart = 0;
	int getFileViewerEnd = 20;

	public VisuaFiles(){
		sc = new Scanner(System.in);
		fs = new FileScanner();
	}

	public void start()throws IOException{
		int input;
		roots = File.listRoots();
		printMenu();
		input = Input.getIntInput(1, roots.length+1);
		long startTime;
		long endTime;

		if(input == 1){ //custom path
			System.out.println("Input what path you want to scan (or input 'exit'to exit):");
			String path = Input.getValidPath();
			startTime = System.currentTimeMillis();
			fs.scanFolder(path);
			endTime = System.currentTimeMillis();
			time = String.format("%-10d", endTime - startTime);
		}
		else{ //scan root
			startTime = System.currentTimeMillis();
			fs.scanFolder(roots[input-2]);
			endTime = System.currentTimeMillis();
			time = String.format("%-10d", endTime - startTime);
		}

		System.out.printf("Sorting file groups...\r");
		fs.createExtensionGroups();
		fs.getExtensionGroups().sort();
		//fs.sortExtensionGroups();
		try{
			fs.createGroups();
			fs.getFileGroups().sort();
		}
		catch(FileNotFoundException e){
			System.out.println(e);
		}
		catch(IOException e){
			System.out.println(e);
		}

		//fs.sortFileGroups();
		printScannerOutput(fs);

		pickGroup(fs);

		/*input = Input.getStringInput();
		while(!input.equals("exit")){
			pickGroup(fs);
		}*/
		//fs.sort();
	}

	public void printMenu(){
		System.out.println("Welcome to [VisuaFiles]\nPick what to scan (or input 0 to exit):\n\n1. Scan a custom path.");
		File roots[] = File.listRoots();
		for(int i=0; i<roots.length; i++){
			double freeSpace = roots[i].getUsableSpace()/(1024.0*1024*1024);
			double totalSpace =  roots[i].getTotalSpace()/(1024.0*1024*1024);
			double usedSpace = totalSpace - freeSpace;
			double percentage = (usedSpace/totalSpace)*100;
			System.out.printf("%d. "+roots[i]+" [ %.2f GB free / %.2f GB total ] | %s %.2f%% used\n", i+2, freeSpace, totalSpace, StringManipulator.getProgressBar(percentage, 5), percentage);
		}
	}

	public void printScannerOutput(FileScanner fs){
		System.out.println("-----SCANNING OVERVIEW---------");
		System.out.println("Results of scanning path ["+fs.getFilePath()+"]:");
		System.out.printf("Folders scanned: %-8d\n", fs.getNumberOfFolders());
		System.out.printf("Files scanned:   %-8d\n", fs.getNumberOfFiles());
		System.out.printf("Total size:      %-8s\n", StringManipulator.convertSize(fs.getSize()));
		System.out.printf("Scan time:       "+time+"\n\n");
		//fs.printFileGroups(28);
		System.out.println("---------FILE GROUPS----------");
		//fs.getFileGroups().printGroups(28, fs.getSize());
		System.out.println();
		//fs.printExtensions(20);
	}

	public void pickGroup(FileScanner fs){
		int numInput = -1;
		String stringInput = "";

		//while(!stringInput.toLowerCase().equals("back")){
		while(true){
			fs.getFileGroups().printGroups(28, fs.getSize());
			System.out.println("Type in the number corresponding to a group to view its files (or 0 to exit):");
			int input = Input.getIntInput(1, 100);
			System.out.printf("Sorting files...\r");
			fs.getFileGroups().getGroup(input-1).sortFiles();
			//fs.displayGroupFiles(input-1);
			fs.getFileGroups().printGroupFiles(input-1);
			pickFile(fs, input);
		}
	}

	public void pickFile(FileScanner fs, int groupNum) {
		//System.out.println("Type in a 'open ' followed by the files number to open it in file explorer\nType in 'back' to go back group list");
		String sInput = "";

		while (!sInput.equals("back")) {
			//System.out.println("Type in a 'open ' followed by the files number to open it in file explorer\nType in 'back' to go back group list");
			System.out.println("[COMMANDS] DELETE: del (file num) | OPEN IN FILE EXPLORER: open (file num) | VIEW AGAIN: view | LOAD MORE: load (num to load) | | LOAD PREVIOUS: prev (num to load) | BACK: back | EXIT: exit");
			sInput = Input.getStringInput();
			if (sInput.equals("back")) break;
			if (sInput.equals("view")) fs.getFileGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
			int fileNum = -1;
			String command = "";
			try {
				fileNum = Integer.parseInt(sInput.split(" ")[1]);
				command = sInput.split(" ")[0];
			} catch (Exception e) {
				System.out.println("Type in a valid command!");
				continue;
			}

			if (fileNum > 0 && fileNum <= fs.getFileGroups().getGroup(groupNum - 1).getFiles().size()) {
				switch (command.toLowerCase()) {
					case "open":
						fs.getFileGroups().getGroup(groupNum - 1).openFileInExplorer(fileNum - 1);
						System.out.println("Opened "+fs.getFileGroups().getGroup(groupNum - 1).getFiles().get(fileNum).getName()+" in file explorer.");
						break;
					case "del":
						if(fs.getFileGroups().getGroup(groupNum - 1).deleteFile(fileNum - 1)){
							fs.getFileGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
						}
						break;
					case "load":
						fileViewerStart = getFileViewerEnd;
						getFileViewerEnd+=fileNum;
						fs.getFileGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
						break;
					case "prev":
						getFileViewerEnd = fileViewerStart;
						fileViewerStart-=fileNum;
						fs.getFileGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
						break;
					default:
						System.out.println("Type in a valid command!");
				}
			}
			else {
				System.out.println("file number inputted is not valid!");
			}
		}
	}

}