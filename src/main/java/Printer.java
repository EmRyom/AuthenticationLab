import java.util.ArrayList;
import java.util.Arrays;

public class Printer {
    String name;
    ArrayList<String> Queue = new ArrayList<>();

    public Printer (String name){
        this.name=name;
    }

    public void addToQueue (String filename) {
        Queue.add(filename);
    }

    public void moveToTop (int job) {
        String file = this.Queue.get(job);
        this.Queue.remove(job);
        this.Queue.add(0,file);
    }

    public String status () {
        return this.name + " has " + Queue.size() + " files in queue";
    }
}
