package utils.impl;

import utils.IntegerComparator;
import utils.FileCounter;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FileCounterMultiThreadImpl implements FileCounter {
    private AtomicInteger totalFiles = new AtomicInteger();
    private Map<String, Integer> fileNames = new HashMap<String, Integer>();
    private Map<String, Integer> dirNames = new HashMap<String, Integer>();

    synchronized public void addFile(File file) {
        totalFiles.incrementAndGet();
        if(fileNames.containsKey(file.getName())){
            fileNames.put(file.getName(), fileNames.remove(file.getName()) + 1);
        }
        else {
            fileNames.put(file.getName(), 1);
        }
    }

    synchronized public void addDirectory(File dir) {
        totalFiles.incrementAndGet();
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
        return totalFiles.get();
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
