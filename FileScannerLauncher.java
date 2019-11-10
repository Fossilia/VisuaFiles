import java.util.*;
import java.nio.file.*;
import java.io.IOException; 
import java.io.File;

public class FileScannerLauncher{
	public static void main(String[]args)throws IOException{
		FileScanner fs = new FileScanner();
		Scanner sc = new Scanner(System.in);
		int input;

		System.out.println("Welcome to [VisuaFiles]\nPick what to scan:\n\n0. Scan a custom path.");
		File roots[] = File.listRoots();
		for(int i=0; i<roots.length; i++){
			double freeSpace = roots[i].getUsableSpace()/(1024.0*1024*1024);
			double totalSpace =  roots[i].getTotalSpace()/(1024.0*1024*1024);
			double usedSpace = totalSpace - freeSpace;
			double percentage = (usedSpace/totalSpace)*100;
			System.out.printf("%d. "+roots[i]+" [ %.2f GB free / %.2f GB total ] | %s %.2f%% used\n", i+1, freeSpace, totalSpace, getProgressBar(percentage, 5), percentage);
		}
		input = sc.nextInt();
		if(input == 0){
			System.out.println("Input what path you want to scan:");
			sc.nextLine();
			String path = sc.nextLine();
			fs.scanFolder(path);
		}
		else{
			fs.scanFolder(roots[input-1]);
		}
		//fs.scanFolder("C:/Users/Faisal/Videos");
		//fs.scanFolder(roots[input]);
		System.out.println("folders: "+fs.getNumberOfFolders());
		System.out.println("files:   "+fs.getNumberOfFiles());
		//fs.sort();
		fs.printExtensions(10);
	}

	public static String getProgressBar(double percentage, int divider){
		String pb = "[";
		for(int i=0; i<(int)percentage/divider; i++){
				pb+="#";
			}
		pb+=String.format("%"+((100/divider+2)-percentage/5)+"s", "]");
		return pb;
	}

	public static String convertSize(long s){
		double size = (double)s;
		if(size<1024){ //Byte range
			return String.format("%.2f Bytes", size);
		}
		if(size>=1024 && size<1024*1024){ //kb range
			return String.format("%.2f KB", size/(1024));
		}
		if(size>=1024*1024 && size<1024*1024*1024){ //mb range
			return String.format("%.2f MB", size/(1024*1024));
		}
		if(size>=1024*1024*1024){ //kb range
			return String.format("%.2f GB", size/(1024*1024*1024));
		}
		else{
			return "";
		}
	}

}