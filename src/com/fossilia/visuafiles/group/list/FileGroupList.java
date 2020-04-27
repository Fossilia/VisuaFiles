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

            boolean test = false;
            if(fileGroup.getName().equals("Executable")){
                test = true;
            }
            for (int i = 0; i < extList.getGroupList().size(); i++) { //go through every extension
                if(!extList.getGroupList().get(i).isCounted()){
                    if (Sorter.binarySearch(groupDataList, extList.getGroupList().get(i).getName().toUpperCase(), test) != -1) { //check if extension exists in current filegroup
                        fileGroup.addGroup(extList.getGroupList().get(i)); //add extension to filegroup if found
                        //extList.getGroupList().remove(i); //remove from temporary array list so that other filegroups dont add it
                        extList.getGroupList().get(i).setCounted();
                        if(extList.getGroupList().get(i).getName().equals("bat")){
                            //System.out.println("pass 3");
                        }
                    }
                }
            }
            groupList.add(fileGroup);
        }

        FileGroup other = new FileGroup("Other files");
        int counter = 0;
        for(Group g: extList.getGroupList()){
            if(!g.isCounted()){
                counter++;
                other.addGroup(g); //add all the leftover extensions to the "other" group
                g.setCounted();
            }
        }
        System.out.println("Recognized extensions: "+(extList.getGroupList().size()-counter)+" / "+extList.getGroupList().size()+", unrecognized extensions are in Other Files group.");
        groupList.add(other);
    }
}
