package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.FileGroup;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.Sorter;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileGroupList extends AbstractGroupList{

    public FileGroupList(){}

    /**
     * takes a folder path and a extenison list and splits the extensions into groups determined by txt files in the folder path
     * @param basePath path to look for text files
     * @param extList extension list to split into groups
     * @throws IOException
     */
    public FileGroupList(String basePath, ExtensionGroupList extList) throws IOException {

        BufferedReader br;
        File base = new File(basePath);
        String line;

        ArrayList<Group> tempGroupList = new ArrayList<>(); //create copy of the list to delete from to speed up file group creation
        for(Group g: extList.getGroupList()){
            tempGroupList.add(g);
        }

        for(String name : Objects.requireNonNull(base.list())) { //go through every file found in the base folder
            File file = new File(basePath + "/" + name); //create the file in memory
            FileGroup fileGroup = new FileGroup(name.substring(0, name.lastIndexOf("."))); //gets rid of extension and creates fileGroup

            String[] words; //used to store a line in a filegroup
            ArrayList<String> groupDataList = new ArrayList<String>(); //used to store all data in filegroup

            br = new BufferedReader(new FileReader(file));
            line = br.readLine();

            while (line != null) { //load data into arraylist
                words = line.split("\t");
                groupDataList.add(words[0]);
                line = br.readLine();
            }
            br.close();

            for (int i = 0; i < tempGroupList.size(); i++) { //go through every extension
                if (Sorter.binarySearch(groupDataList, tempGroupList.get(i).getName().toUpperCase()) != -1) { //check if extension exists in current filegroup
                    fileGroup.addGroup(tempGroupList.get(i)); //add extension to filegroup if found
                    tempGroupList.remove(i); //remove from temporary array list so that other filegroups dont add it
                }
            }
            groupList.add(fileGroup);
        }

        FileGroup other = new FileGroup("Other files");
        if(!tempGroupList.isEmpty()){ //if there are extensions not in the file group data
            for(Group g: tempGroupList){
                other.addGroup(g); //add all the leftover extensions to the "other" group
            }
            groupList.add(other);
        }
    }
}
