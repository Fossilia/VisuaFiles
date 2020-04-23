package com.fossilia.visuafiles.util;

import java.io.File;

public final class StringManipulator {

    public static String getProgressBar(double percentage, int divider){
        String pb = "[";
        for(int i=0; i<(int)percentage/divider; i++){
            pb+="#";
        }
        pb+=String.format("%"+((100/divider+2)-percentage/divider)+"s", "]");
        return pb;
    }

    public static String convertSize(long s){
        double size = (double)s;
        if(size<1024){ //Byte range
            return String.format("%5.0f %-5s", size, "Bytes");
        }
        if(size>=1024 && size<1024*1024){ //kb range
            return String.format("%5.2f %-5s", size/(1024), "KB");
        }
        if(size>=1024*1024 && size<1024*1024*1024){ //mb range
            return String.format("%.2f %-5s", size/(1024*1024), "MB");
        }
        if(size>=1024*1024*1024){ //kb range
            return String.format("%5.2f %-5s", size/(1024*1024*1024), "GB");
        }
        else{
            return "";
        }
    }

    public static String getExtension(File f){
        String path = f.getName();
        if(path.lastIndexOf(".")!=-1){
            return path.substring(path.lastIndexOf(".")+1);
        }
        else{
            return null;
        }
    }

    public static String convertTime(long time){
        if(time<1000){ //ms
            return String.format("%d ms", time);
        }
        else if(time>1000 && time<60000){ //seconds
            return String.format("%f seconds", time/1000.0);
        }
        else{
            return String.format("%f minutes", time/60000.0);
        }

    }
}
