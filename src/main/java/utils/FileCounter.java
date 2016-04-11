package utils;

import java.io.File;
import java.util.List;

/**
 * Created by Serhiy_Kovalenko on 4/7/2016.
 */
public interface FileCounter {
    public void addFile(File file);
    public void addDirectory(File dir);
    public int getUniqueFiles();
    public int getUniqueDirectories();
    public int getTotalFiles();
    public List<String> getPopularFileNames();
}
