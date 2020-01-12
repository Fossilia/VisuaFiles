package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.Sorter;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.ArrayList;

public class AbstractGroupList {
    protected ArrayList<Group> groupList;

    public void sort(){
        Sorter.sortGroups(groupList);
    }

    public void printGroupFiles(int num){
        groupList.get(num).printFiles(100);
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(ArrayList<Group> groupList) {
        this.groupList = groupList;
    }

    public void printGroups(int num, int size){
        int limit = Math.min(num, groupList.size());
        for(int i=0; i<limit; i++){
            if(groupList.get(i).getSize()>0){
                double percent = ((double)groupList.get(i).getSize()/(double)size)*100;
                String percentBar = StringManipulator.getProgressBar(percent, 5);
                System.out.printf("%s %5.2f%% "+groupList.get(i)+"\n", percentBar, percent);
                //System.out.printf("%20s%10s count: %5d percent: %5.2f%% size: %s\n", groupList.get(i).getName(), percentBar, groupList.get(i).getCount(), percent, memorySize);
            }
        }
    }
}
