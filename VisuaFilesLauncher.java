import java.io.*;

public class VisuaFilesLauncher{
	public static void main(String[]args){
		VisuaFiles visuaFiles = new VisuaFiles();
		try{
			visuaFiles.start();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
}