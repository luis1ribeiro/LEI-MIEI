import java.util.List;

interface Ficheiro {
    void using(String path);
    void notUsing(String path, boolean modified);
    List<String> startBackup();
    void endBackup();
}

