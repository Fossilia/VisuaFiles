package com.fossilia.visuafiles.group;

import java.util.*;

public class FileGroup extends Group {
	private ArrayList<ExtensionGroup> groups = new ArrayList<ExtensionGroup>();

	public FileGroup(){
		super();
	}

	public FileGroup(String name){
		super(name);
	}


	public void addGroup(Group e){
		files.addAll(e.getFiles());
		size+=e.getSize();
		groups.add((ExtensionGroup)e);
	}
}