package utils.impl;

import utils.IntegerComparator;
import utils.FileCounter;

import java.io.File;
import java.util.*;

public class FileCounterSingleThreadImpl implements FileCounter {
    private int totalFileCounter = 0;
    private Map<String, Integer> fileNames = new HashMap<String, Integer>();
    private Map<String, Integer> dirNames = new HashMap<String, Integer>();

    public void addFile(File file) {
        totalFileCounter++;
        if(fileNames.containsKey(file.getName())){
            fileNames.put(file.getName(), fileNames.remove(file.getName()) + 1);
        }
        else {
            fileNames.put(file.getName(), 1);
        }
    }

    public void addDirectory(File dir) {
        totalFileCounter++;
        if(dirNames.containsKey(dir.getName())){
            dirNames.put(dir.getName(), dirNames.remove(dir.getName()) + 1);
        }
        else {
            dirNames.put(dir.getName(), 1);
        }
    }

    public int getUniqueFiles() {
        return fileNames.size();
    }

    public int getUniqueDirectories() {
        return dirNames.size();
    }

    public int getTotalFiles() {
        return totalFileCounter;
    }

    public List<String> getPopularFileNames() {
        int i = 0;
        int limit = 0;
        List<String> result = new ArrayList<String>();
        List<Map.Entry<String, Integer>> files = new ArrayList<Map.Entry<String, Integer>>();
        files.addAll(fileNames.entrySet());
        Collections.sort(files, new IntegerComparator());

        if(files.size() < 10) limit = files.size();
        else limit = 10;
        while(i < limit){
            result.add(files.get(i).getKey());
            i++;
        }
        return result;
    }
}
