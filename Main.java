import java.io.File;

/**
 * Created by Greg on 12.02.2017.
 */
public class Main {
    public static void main(String args[]){
        SearchController sc = new SearchController("test.zip",new File("C:/Users/Greg/"),4);
        new Thread(sc).start();
    }
}
