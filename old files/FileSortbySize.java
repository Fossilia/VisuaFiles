import java.util.*; 
import java.lang.*; 
import java.io.*;

public class FileSortbySize implements Comparator<File>{
	//used for sorting in decsending order of area
	public int compare(File a, File b){
		return (int)(b.length() - a.length()); //cast to int since compare only accepts that
	}
}