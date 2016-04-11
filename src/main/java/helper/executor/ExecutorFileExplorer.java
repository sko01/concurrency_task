package helper.executor;

import utils.FileCounter;

import java.io.File;

public class ExecutorFileExplorer implements Runnable{
    public ExecutorDirectoryParser pool;
    private FileCounter fileCounter;

    public ExecutorFileExplorer(ExecutorDirectoryParser pool, FileCounter fileCounter) {
        this.pool = pool;
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
    }
}
