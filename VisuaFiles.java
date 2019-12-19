import java.util.*;
import java.nio.file.*;
import java.io.IOException; 
import java.io.File;

public class VisuaFiles{
	Scanner sc = null;
	FileScanner fs = null;

	public VisuaFiles(){
		sc = new Scanner(System.in);
		fs = new FileScanner();
	}

	public void start()throws IOException{
		int input;

		printMenu();
		input = getIntInput(0, File.listRoots().length);

		if(input == 0){ //custom path
			System.out.println("Input what path you want to scan:");
			String path = getStringInput();
			fs.scanFolder(path);
		}
		else{ //scan root
			fs.scanFolder(roots[input-1]);
		}
		
		printScannerOutput(fs);
	}

	public void printScannerOutput(FileScanner fs){
		System.out.println("folders: "+fs.getNumberOfFolders());
		System.out.println("files:   "+fs.getNumberOfFiles());
		fs.sortExtensionGroups();
		System.out.println("Total size: "+convertSize(fs.getSize()));
		fs.printExtensions(10);
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
			System.out.printf("%d. "+roots[i]+" [ %.2f GB free / %.2f GB total ] | %s %.2f%% used\n", i+1, freeSpace, totalSpace, getProgressBar(percentage, 5), percentage);
		}
	}

	/**gets an integer from the user between min and max (inclusive), checks for exceptions*/
	public int getIntInput(int min, int max){
		int input = 0;
		boolean done = false;

		while(!done){
			try{
				input = sc.nextInt(); //gets input, may have an exception if user enters a string	
				if(input>=min && input<=max){ //checks if its in the range
					done = true;
					sc.nextLine();
					return input;
				}
				else{
					System.out.println("Type in a valid number between "+min+" and "+max);
				}
			}
			catch(InputMismatchException e){ //catches case user types in a string
				sc.nextLine();
				System.out.println("Please type in a valid number.");
			}
		}
		return 0;
	}

	/**gets a string from the user, checks for exceptions*/
	public String getStringInput(){
		String input = "";
		boolean done = false;

		while(!done){
			try{
				input = sc.nextLine(); //gets input, may have an exception if user does not type in a string
				if(input == ""){ //checks for empty string
					System.out.println("You have to type in something!");
					continue;
				}
				//sc.nextLine();
				return input;
			}
			catch(InputMismatchException e){ //in case user does not type in a string 
				sc.nextLine();
				System.out.println("Please type in a valid string.");
			}
		}
		return "";
	}

	public String getProgressBar(double percentage, int divider){
		String pb = "[";
		for(int i=0; i<(int)percentage/divider; i++){
				pb+="#";
			}
		pb+=String.format("%"+((100/divider+2)-percentage/5)+"s", "]");
		return pb;
	}

	public String convertSize(long s){
		double size = (double)s;
		if(size<1024){ //Byte range
			return String.format("%5.0f %-5s", size, "Bytes");
		}
		if(size>=1024 && size<1024*1024){ //kb range
			return String.format("%5.2f %-5s", size/(1024), "KB");
		}
		if(size>=1024*1024 && size<1024*1024*1024){ //mb range
			return String.format("%.2f %-5s", size/(1024*1024), "MB");
		}
		if(size>=1024*1024*1024){ //kb range
			return String.format("%5.2f %-5s", size/(1024*1024*1024), "GB");
		}
		else{
			return "";
		}
	}

}