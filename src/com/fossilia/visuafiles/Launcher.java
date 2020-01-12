package com.fossilia.visuafiles;

import java.io.*;

public class Launcher{
	public static void main(String[]args){
		VisuaFiles visuaFiles = new VisuaFiles();
		try{
			//Runtime.getRuntime().exec("explorer.exe /select, C:/");
			visuaFiles.start();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
}

//3251 og
//1845 wo group insert
//3252 wo bar

//288923
//86884

//new: 166312
//old: 547924