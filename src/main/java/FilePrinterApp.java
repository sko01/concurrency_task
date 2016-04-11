import helper.recursion.DirectoryParser;
import helper.executor.ExecutorDirectoryParser;
import helper.multithreading.MultithreadingDirectoryParser;
import helper.nio.NioDirectoryParser;
import utils.impl.FileCounterMultiThreadImpl;
import utils.impl.FileCounterSingleThreadImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePrinterApp {
    public static void main(String[] args){
        final String DIR_PATH = ".";
        final File folder = new File(DIR_PATH);

        Long start = System.nanoTime();
        DirectoryParser directoryParser = new DirectoryParser(new FileCounterSingleThreadImpl());
        directoryParser.listFilesInFolder(folder);
        directoryParser.getResults();
        Long stop = System.nanoTime();
        System.out.println("Used time Traditional approach = " + (stop - start));

        Path folderPath = Paths.get(DIR_PATH);
        start = System.nanoTime();
        NioDirectoryParser nioDirectoryParser = new NioDirectoryParser(new FileCounterSingleThreadImpl());
        try {
            Files.walkFileTree(folderPath, nioDirectoryParser);
            nioDirectoryParser.getResults();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stop = System.nanoTime();
        System.out.println("Used time Nio approach = " + (stop - start));

        start = System.nanoTime();
        MultithreadingDirectoryParser mDirectoryParser = new MultithreadingDirectoryParser(10, new FileCounterMultiThreadImpl());
        mDirectoryParser.scan(folder);
        mDirectoryParser.getResults();
        stop = System.nanoTime();
        System.out.println("Used time multithreading approach = " + (stop - start));

        start = System.nanoTime();
        ExecutorDirectoryParser eDirectoryParser = new ExecutorDirectoryParser(10, new FileCounterMultiThreadImpl());
        eDirectoryParser.scan(folder);
        eDirectoryParser.getResults();
        stop = System.nanoTime();
        System.out.println("Used time executor service approach = " + (stop - start));


    }
}
