package helper.nio;

import utils.FileCounter;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

@SuppressWarnings("ALL")
public class NioDirectoryParser extends SimpleFileVisitor<Path>{
    private FileCounter fileCounter;

    public NioDirectoryParser(FileCounter fileCounter) {
        this.fileCounter = fileCounter;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr){
        fileCounter.addFile(file.toFile());
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc){
        fileCounter.addDirectory(dir.toFile());
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }

    public void getResults(){
        System.out.println("Total dir/files - " + fileCounter.getTotalFiles());
        System.out.println("Unique files - " + fileCounter.getUniqueFiles());
        System.out.println("Unique dir - " + fileCounter.getUniqueDirectories());
        System.out.println("List - " + fileCounter.getPopularFileNames());
    }
}
