package helper.multithreading;

import utils.impl.FileCounterMultiThreadImpl;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MultithreadingDirectoryParser extends Thread{
    private final Queue exploreList = new ConcurrentLinkedQueue();
    private final FileCounterMultiThreadImpl fileCounter;
    int[] threads;

    public MultithreadingDirectoryParser(int numberOfThreads, FileCounterMultiThreadImpl fileCounter){
        this.threads = new int[numberOfThreads];
        this.fileCounter = fileCounter;

        for(int i = 0; i < numberOfThreads; i++){
            threads[i] = -1;
        }
    }

    public void scan(final File file){
        exploreList.add(file);
        for(int i = 0; i < threads.length; i++){
            FileExplorer explorer = new FileExplorer(i, this, fileCounter);
            Thread t = new Thread(explorer);
            t.start();
        }

        Thread waitToFinish = new Thread(new Runnable() {
            public void run() {
                boolean working = true;

                while (working){
                    working = false;
                    for(int i = 0; i < threads.length; i ++){
                        if(threads[i] == -1){
                            working = true;
                            break;
                        }
                    }
                    try{
                        Thread.sleep(2);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        waitToFinish.start();
        try {
            waitToFinish.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Queue getExploreList() {
        return exploreList;
    }

    public void done(int id){
        threads[id] = 1;
    }

    public void getResults(){
        System.out.println("Total dir/files - " + fileCounter.getTotalFiles());
        System.out.println("Unique files - " + fileCounter.getUniqueFiles());
        System.out.println("Unique dir - " + fileCounter.getUniqueDirectories());
        System.out.println("List - " + fileCounter.getPopularFileNames());
    }
}
