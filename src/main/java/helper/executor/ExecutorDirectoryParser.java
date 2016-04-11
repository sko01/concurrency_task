package helper.executor;


import utils.FileCounter;
import utils.impl.FileCounterMultiThreadImpl;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorDirectoryParser{
    private final Queue exploreList = new ConcurrentLinkedQueue();
    private FileCounter fileCounter;
    private int threadsNum;

    public ExecutorDirectoryParser(int threadsNum, FileCounterMultiThreadImpl fileCounter) {
        this.threadsNum = threadsNum;
        this.fileCounter = fileCounter;
    }

    public Queue getExploreList() {
        return exploreList;
    }

    public void scan(File dir){
        exploreList.add(dir);
        ExecutorService executorService = Executors.newFixedThreadPool(threadsNum);
        ExecutorFileExplorer executor = new ExecutorFileExplorer(this, fileCounter);
        executorService.execute(executor);
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getResults(){
        System.out.println("Total dir/files - " + fileCounter.getTotalFiles());
        System.out.println("Unique files - " + fileCounter.getUniqueFiles());
        System.out.println("Unique dir - " + fileCounter.getUniqueDirectories());
        System.out.println("List - " + fileCounter.getPopularFileNames());
    }
}
