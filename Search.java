import java.io.File;

public class Search  implements Runnable{
    private String keyWord;
    private File inputDirectory;
    private SearchController sc;

    public Search() {
    }

    public Search(String keyWord, File inputDirectory, SearchController sc) {
        this.keyWord = keyWord;
        this.inputDirectory = inputDirectory;
        this.sc = sc;
    }

    @Override
    public void run() {
        if(inputDirectory != null ) {
            try {
                for (File file : inputDirectory.listFiles()) {
                    if (file.isDirectory()) {
                        sc.addDirectory(file);
                    } else {
                        if (file.getName().equals(keyWord)) {
                            sc.addResult(file.getAbsolutePath());
                        }
                    }
                }

            } catch (NullPointerException e) {
                sc.removeThread(Thread.currentThread());
                sc.removeSearch(this);
                sc.wakeUp();
            }
        }
        sc.removeThread(Thread.currentThread());
        sc.removeSearch(this);
        sc.wakeUp();

    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public File getInputDirectory() {
        return inputDirectory;
    }

    public void setInputDirectory(File inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    public SearchController getSc() {
        return sc;
    }

    public void setSc(SearchController sc) {
        this.sc = sc;
    }
}
