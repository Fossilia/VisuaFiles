package com.fossilia.visuafiles.util;

import java.io.File;

/**
 * Class used for useful string manipulations
 */
public final class StringManipulator {

    /**
     * returns a string progress bar witha certain percentage
     * @param percentage how much the bar should be filled
     * @param divider what the increment amount should be for the progress bar (also affects size)
     * @return the string progress bar
     */
    public static String getProgressBar(double percentage, int divider){
        StringBuilder pb = new StringBuilder("[");
        for(int i=0; i<(int)percentage/divider; i++){
            pb.append("#");
        }
        pb.append(String.format("%" + ((100 / divider + 2) - percentage / divider) + "s", "]"));
        return pb.toString();
    }

    /**
     * Converts bytes to bytes, KB, MB, or GB depending on which would be best for reading
     * @param s the bytes to convert
     * @return formatted string of converted bytes
     */
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

    /**
     * Gets the extension of a file
     * @param f the file to get extension from
     * @return extension of file if it has one, otherwise null
     */
    public static String getExtension(File f){
        String path = f.getName();
        if(path.lastIndexOf(".")!=-1){
            return path.substring(path.lastIndexOf(".")+1);
        }
        else{
            return null;
        }
    }

    /**
     * Converts milliseconds to milliseconds, seconds, or minutes depending on which which would be best for reading
     * @param time the milliseconds to be converted
     * @return string version of converted time
     */
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
