package helper.multithreading;

import helper.multithreading.MultithreadingDirectoryParser;
import utils.impl.FileCounterMultiThreadImpl;

import java.io.File;

public class FileExplorer implements Runnable {
    public MultithreadingDirectoryParser pool;
    private int threadId;
    private FileCounterMultiThreadImpl fileCounter;

    public FileExplorer(int threadId, MultithreadingDirectoryParser pool, FileCounterMultiThreadImpl fileCounter) {
        this.pool = pool;
        this.threadId = threadId;
        this.fileCounter = fileCounter;
    }

    public void run() {
        while(!pool.getExploreList().isEmpty()) {
            File dir = (File) pool.getExploreList().poll();
            if(dir != null && dir.exists()){
                fileCounter.addDirectory(dir);
                for(File fileEntry : dir.listFiles()){
                    if(fileEntry.isDirectory()){
                        pool.getExploreList().add(fileEntry);
                    }
                    else{
                        fileCounter.addFile(fileEntry);
                    }
                }
            }
        }
        try {
            Thread.sleep(1);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        pool.done(threadId);
    }
}
