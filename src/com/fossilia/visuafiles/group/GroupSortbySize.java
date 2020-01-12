package com.fossilia.visuafiles.group;

import com.fossilia.visuafiles.group.Group;

import java.util.*;

public class GroupSortbySize implements Comparator<Group>{
	//used for sorting in decsending order of area
	public int compare(Group a, Group b){
		return (int)(b.getSize() - a.getSize()); //cast to int since compare only accepts that
	}
}