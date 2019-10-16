import java.util.*;
import java.nio.file.*;
import java.io.IOException; 
import java.io.File;

public class FileScannerLauncher{
	public static void main(String[]args)throws IOException{
		FileScanner fs = new FileScanner();
		Scanner sc = new Scanner(System.in);
		String input;

		System.out.println("Free Space: "+(new File("C:/").getUsableSpace()/(1024*1024*1024))+" GB");
		System.out.println("Total Space: "+(new File("C:/").getTotalSpace()/(1024*1024*1024))+" GB");
		System.out.println("Used Space: "+((new File("C:/").getTotalSpace())-(new File("C:/").getUsableSpace()))/(1024*1024*1024)+" GB");
		//System.out.println(new File("C:/").getUsedSpace()/(1024*1024*1024)+" GB");
		/*for(String string : new File("C:/$Recycle.Bin/S-1-5-18").list()){
			System.out.println(string);
		}*/
		//fs.scanFolder(new File("C:/"));

		System.out.println("Input what path you want to scan:");
		input = sc.nextLine();
		//fs.scanFolder("C:/Users/Faisal/Videos");
		fs.scanFolder(input);
		fs.printSizeKB();
		fs.printSizeMB();
		fs.printSizeGB();
		System.out.println("folders: "+fs.getNumberOfFolders());
		System.out.println("files:   "+fs.getNumberOfFiles());

		//fs.scanFolder(new File("C:/UW.pdf"));
		//System.out.println(fs.getSize()/(1024.0*1024.0));
		
		/*
		String[] subDirs;
		File path = new File("C:/UW.pdf");
		double result = path.length();
		System.out.println(result/1024.0);

		File root = new File("C:/");
		subDirs = root.list();

		for(String item : subDirs){
			System.out.println(item);
		}*/
	}

}