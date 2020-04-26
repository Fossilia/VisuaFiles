package com.fossilia.visuafiles;

import com.fossilia.visuafiles.files.FileScanner;
import com.fossilia.visuafiles.util.Input;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

public class VisuaFiles{
	Scanner sc;
	FileScanner fs;
	File[] roots;
	String time;
	String sortingTime;
	int fileViewerStart = 0;
	int getFileViewerEnd = 20;
	final int FILEGROUPS = 1;
	final int EXTENSIONGROUPS = 2;
	final int FOLDERGROUPS = 3;

	public VisuaFiles(){
		sc = new Scanner(System.in);
		fs = new FileScanner();
		roots = File.listRoots();
	}

	/**
	 * Starts program, allows user to pick which drive or path they want to scan and scans them
	 */
	public void start() {
		int input;
		printMenu();
		input = Input.getIntInput(1, roots.length+1);
		long startTime;
		long endTime;
		long startSortingTime;
		long endSortingTime;

		if(input == 1){ //custom path
			System.out.println("Input what path you want to scan (or input 'exit'to exit):");
			String path = Input.getValidPath();
			startTime = System.currentTimeMillis();
			fs.scanFolder(path);
			endTime = System.currentTimeMillis();
			time = StringManipulator.convertTime(endTime - startTime);
		}
		else{ //scan root
			startTime = System.currentTimeMillis();
			fs.scanFolder(roots[input-2]);
			endTime = System.currentTimeMillis();
			time = StringManipulator.convertTime(endTime - startTime);
		}

		startSortingTime = System.currentTimeMillis();
		System.out.printf("Sorting file groups...\r");
		fs.createExtensionGroups();
		fs.getExtensionGroups().sort();
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
		endSortingTime = System.currentTimeMillis(); //end time for creating an sorting groups
		sortingTime = StringManipulator.convertTime(endSortingTime - startSortingTime);

		printScannerOutput(fs);
		pickGroup(fs);
	}

	/**
	 * Prints the start up menu where drives are displayed
	 */
	public void printMenu(){
		System.out.println("Welcome to [VisuaFiles]\nPick what to scan (or input 0 to exit):\n\n1. Scan a custom path.");
		for(int i=0; i<roots.length; i++){
			double freeSpace = roots[i].getUsableSpace()/(1024.0*1024*1024);
			double totalSpace =  roots[i].getTotalSpace()/(1024.0*1024*1024);
			double usedSpace = totalSpace - freeSpace;
			double percentage = (usedSpace/totalSpace)*100;
			System.out.printf("%d. "+roots[i]+" [ %.2f GB free / %.2f GB total ] | %s %.2f%% used\n", i+2, freeSpace, totalSpace, StringManipulator.getProgressBar(percentage, 5), percentage);
		}
	}

	/**
	 * Prints information about a filescanner object (which must have already run a scan)
	 * @param fs filescanner to get output from
	 */
	public void printScannerOutput(FileScanner fs){
		System.out.println("-----SCANNING OVERVIEW---------\n");
		System.out.println("Results of scanning path ["+fs.getFilePath()+"]:");
		System.out.printf("Folders scanned: %-8d\n", fs.getNumberOfFolders());
		System.out.printf("Files scanned:   %-8d\n", fs.getNumberOfFiles());
		System.out.printf("Total size:      %-8s\n", StringManipulator.convertSize(fs.getSize()));
		System.out.printf("Scan time:       "+time+"\n");
		System.out.printf("Sorting time:    "+sortingTime+"\n\n");
		//fs.printFileGroups(28);
		System.out.println("---------FILE GROUPS----------");
		//fs.getFileGroups().printGroups(28, fs.getSize());
		System.out.println();
		//fs.printExtensions(20);
	}

	/**
	 * Allows user to pick what group to view files from
	 * @param fs filescanner used to get groups from
	 */
	public void pickGroup(FileScanner fs){
		System.out.println("Type in [1] for the files to be sorted in file groups.");
		System.out.println("Type in [2] for the files to be sorted in extension groups.");
		int modeInput = Input.getIntInput(1, 2);
		while(true){
			if(modeInput == FILEGROUPS){
				fs.getFileGroups().printGroups(28, fs.getSize());
			}
			else if(modeInput == EXTENSIONGROUPS){
				fs.getExtensionGroups().printGroups(28, fs.getSize());
			}
			System.out.println("Type in the number corresponding to a group to view its files (or -1 to back to mode selection, 0 to exit):");
			int input = Input.getIntInput(-1, 100);
			if(input == -1){
				pickGroup(fs);
				return;
			}
			if(modeInput == FILEGROUPS){ //in case user picks file groups
				if(!fs.getFileGroups().getGroup(input-1).isSorted()){ //check if file group is already sorted
					System.out.printf("Sorting files...\r");
					fs.getFileGroups().getGroup(input-1).sortFiles();
				}
				fs.getFileGroups().printGroupFiles(input-1);
				pickFile(fs, input,FILEGROUPS);
			}
			if(modeInput == EXTENSIONGROUPS){ //in case user picks extension groups
				if(!fs.getExtensionGroups().getGroup(input-1).isSorted()){ //check if file group is already sorted
					System.out.printf("Sorting files...\r");
					fs.getExtensionGroups().getGroup(input-1).sortFiles();
				}
				fs.getExtensionGroups().printGroupFiles(input-1);
				pickFile(fs, input, EXTENSIONGROUPS);
			}
		}
	}

	/**
	 * Allows user to interact with the file menu with options to open files in file explorer, delete files, and scroll through files
	 * @param fs filescanner to get data from
	 * @param groupNum which group was picked to view files from
	 */
	public void pickFile(FileScanner fs, int groupNum, int mode) {
		String sInput = "";

		if(mode == FILEGROUPS){
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
		else if(mode == EXTENSIONGROUPS){
			while (!sInput.equals("back")) {
				//System.out.println("Type in a 'open ' followed by the files number to open it in file explorer\nType in 'back' to go back group list");
				System.out.println("[COMMANDS] DELETE: del (file num) | OPEN IN FILE EXPLORER: open (file num) | VIEW AGAIN: view | LOAD MORE: load (num to load) | | LOAD PREVIOUS: prev (num to load) | BACK: back | EXIT: exit");
				sInput = Input.getStringInput();
				if (sInput.equals("back")) break;
				if (sInput.equals("view")) fs.getExtensionGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
				int fileNum = -1;
				String command = "";
				try {
					fileNum = Integer.parseInt(sInput.split(" ")[1]);
					command = sInput.split(" ")[0];
				} catch (Exception e) {
					System.out.println("Type in a valid command!");
					continue;
				}

				if (fileNum > 0 && fileNum <= fs.getExtensionGroups().getGroup(groupNum - 1).getFiles().size()) {
					switch (command.toLowerCase()) {
						case "open":
							fs.getExtensionGroups().getGroup(groupNum - 1).openFileInExplorer(fileNum - 1);
							System.out.println("Opened "+fs.getExtensionGroups().getGroup(groupNum - 1).getFiles().get(fileNum).getName()+" in file explorer.");
							break;
						case "del":
							if(fs.getExtensionGroups().getGroup(groupNum - 1).deleteFile(fileNum - 1)){
								fs.getExtensionGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
							}
							break;
						case "load":
							fileViewerStart = getFileViewerEnd;
							getFileViewerEnd+=fileNum;
							fs.getExtensionGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
							break;
						case "prev":
							getFileViewerEnd = fileViewerStart;
							fileViewerStart-=fileNum;
							fs.getExtensionGroups().printGroupFiles(groupNum-1, fileViewerStart, getFileViewerEnd);
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

}