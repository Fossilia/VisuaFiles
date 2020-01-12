package com.fossilia.visuafiles.group.list;

import com.fossilia.visuafiles.group.FileGroup;
import com.fossilia.visuafiles.group.Group;
import com.fossilia.visuafiles.util.StringManipulator;

import java.io.*;
import java.util.ArrayList;

public class FileGroupList extends AbstractGroupList{
    private ArrayList<Group> groupList;

    public FileGroupList(String basePath, ExtensionGroupList extList) throws FileNotFoundException, IOException {
        BufferedReader br;
        File base = new File("DATA");
        String line;

        FileGroup other = new FileGroup("Other files");

        for(String name: base.list()){
            File file = new File("DATA/" +name);
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
    }

}
