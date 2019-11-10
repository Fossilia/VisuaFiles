import java.util.*; 
import java.lang.*; 
import java.io.*;

public class EGSortbySize implements Comparator<ExtensionGroup>{
	//used for sorting in decsending order of area
	public int compare(ExtensionGroup a, ExtensionGroup b){
		return (int)(b.getSize() - a.getSize()); //cast to int since compare only accepts that
	}
}