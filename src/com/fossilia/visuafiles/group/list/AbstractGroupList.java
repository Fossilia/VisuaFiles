package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.Sorter;
import com.fossilia.visuafiles.util.StringManipulator;

import java.util.ArrayList;

public class AbstractGroupList {
    protected ArrayList<Group> groupList = new ArrayList<>();

    public AbstractGroupList(ArrayList<Group> groupList){
        this.groupList = groupList;
    }

    public AbstractGroupList(){}

    public void sort(){
        Sorter.sortGroups(groupList);
    }

    public void addToList(Group g){
        groupList.add(g);
    }

    public Group getGroup(int n){
        return groupList.get(n);
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

    public void printGroups(int num, long size){
        int limit = Math.min(num, groupList.size());
        int maxStringLength = 0;

        for(int i=0; i<limit; i++){
            if(groupList.get(i).getSize()>0) {
                maxStringLength = Math.max(maxStringLength, groupList.get(i).getName().length());
            }
        }
        maxStringLength+=2;
        //System.out.println(maxStringLength);

        for(int i=0; i<limit; i++){
            if(groupList.get(i).getSize()>0){
                double percent = ((double)groupList.get(i).getSize()/(double)size)*100;
                String percentBar = StringManipulator.getProgressBar(percent, 5);
                String memorySize = StringManipulator.convertSize(groupList.get(i).getSize());
                //System.out.printf("%s %5.2f%% "+groupList.get(i)+"\n", percentBar, percent);
                System.out.printf("%3d. %-"+maxStringLength+"s%s count: %5d percent: %5.2f%% size: %-10s\n", (i+1), groupList.get(i).getName(), percentBar, groupList.get(i).getCount(), percent, memorySize);
            }
        }
    }
}
