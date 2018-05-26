package schedulingApplication.Models;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManipulation {

    private static String logFileName = "/SchedulingApplicationLog.txt";
    private static String logFilePath = "";
    private static File file;

    public static void writeToFile(String newData) {

        getUsersHomeDesktop();

        try {
            File file = new File(logFilePath.concat(logFileName));
            if(file.exists()){
                newData = getFileContents(logFilePath+logFileName).concat(newData);
            }
            FileWriter fw = new FileWriter(logFilePath.concat(logFileName));
            BufferedWriter br = new BufferedWriter(fw);
            br.write(newData);
            br.close();
        } catch (IOException e) {
            System.out.println("ERROR Writing to logfile: "+e);
        }

    }

    public static void getUsersHomeDesktop(){
        String desktop = System.getProperty("user.home") + "/Desktop";
        desktop.replace("\\", "/");
        logFilePath = desktop;
    }

    public static String getFileContents(String filePath) throws IOException{
        String contents = "";
        contents = new String ( Files.readAllBytes(Paths.get(filePath)) );
        return contents;
    }

    public static String getFullFilePath(){
        return logFilePath.concat(logFileName);
    }
}
