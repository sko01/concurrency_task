package helper.recursion;

import utils.FileCounter;

import java.io.File;

public class DirectoryParser {
    private FileCounter fileCounter;

    public DirectoryParser(FileCounter fileCounter) {
        this.fileCounter = fileCounter;
    }

    public void listFilesInFolder(final File folder){
        fileCounter.addDirectory(folder);
        for(final File fileEntry : folder.listFiles()){
            if(fileEntry.isDirectory())
                listFilesInFolder(fileEntry);
            else
                fileCounter.addFile(fileEntry);
        }
    }

    public void getResults(){
        System.out.println("Total dir/files - " + fileCounter.getTotalFiles());
        System.out.println("Unique files - " + fileCounter.getUniqueFiles());
        System.out.println("Unique dir - " + fileCounter.getUniqueDirectories());
        System.out.println("List - " + fileCounter.getPopularFileNames());
    }
}
