package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.FileGroup;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.StringManipulator;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class FileGroupList extends AbstractGroupList{

    public FileGroupList(){}

    public FileGroupList(String basePath, ExtensionGroupList extList) throws FileNotFoundException, IOException {
        //groupList = new ArrayList<>();

        BufferedReader br;
        File base = new File(basePath);
        String line;

        FileGroup other = new FileGroup("Other files");

        System.out.println(base.list().length);
        for(String name: Objects.requireNonNull(base.list())){
            File file = new File(basePath+"/" +name);
            FileGroup fileGroup = new FileGroup(name);

            String[] words;

            for(Group e: extList.getGroupList()){
                br = new BufferedReader(new FileReader(file));
                line = br.readLine();
                //System.out.println(e.getName());
                //boolean found = false;
                while(line!=null){
                    words = line.split("\t");
                    //System.out.println(words[0]+" "+e.getName().toUpperCase());
                    if(words[0].equals(e.getName().toUpperCase())){
                        fileGroup.addGroup(e);
                        //found = true;
                        //System.out.println("found");
                        break;
                    }
                    line = br.readLine();
                }
                br.close();
            }
            groupList.add(fileGroup);
        }
        //System.out.println(groupList.size());
    }


}
