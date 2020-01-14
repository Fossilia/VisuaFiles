package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.ExtensionGroup;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.Sorter;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.ArrayList;

public class ExtensionGroupList extends AbstractGroupList{

    public ExtensionGroupList(ArrayList<Group> groupList){
        this.groupList = groupList;
    }

    public ExtensionGroupList(){
         groupList = new ArrayList<Group>();
    }

}
