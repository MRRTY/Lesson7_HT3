import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Greg on 12.02.2017.
 */
public class SearchController implements Runnable {
    private String keyWord;
    private File inputDirectory;
    private ArrayBlockingQueue<File> directoryes = new ArrayBlockingQueue<File>(10000);
    private ArrayList<Search> searches = new ArrayList<>();
    private ArrayList<Thread> threads = new ArrayList<>();
    private int totalThreads;
    private ArrayList<String> results = new ArrayList<>();

    public SearchController() {
    }

    public SearchController(String keyWord, File inputDirectory, int totalThreads) {
        this.keyWord = keyWord;
        this.inputDirectory = inputDirectory;
        this.totalThreads = totalThreads;
    }

    public synchronized void addDirectory(File file){
        directoryes.add(file);
    }

    public synchronized void addResult(String res){
        results.add(res);
    }

    public synchronized void removeSearch(Search search){
        searches.remove(search);
    }
    public synchronized void removeThread(Thread thread){
        threads.remove(thread);
    }

    @Override
    public synchronized void run() {
        Search ss =new Search(keyWord,inputDirectory,this);
        searches.add(ss);
        Thread tt = new Thread(searches.get(0));
        threads.add(tt);
        tt.start();
        while (!threads.isEmpty()){
                while (searches.size() < totalThreads) {
                    Search s = new Search(keyWord, directoryes.poll(), this);
                    searches.add(s);
                    Thread t = new Thread(s);
                    threads.add(t);
                    t.start();
                }

            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Results:");
        for(String s:results){
            System.out.println(s);
        }

    }
    public synchronized void wakeUp(){
        notifyAll();
    }
}
