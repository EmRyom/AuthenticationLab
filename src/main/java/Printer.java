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

    public boolean moveToTop (int job) {
        if (job>=Queue.size()) {
            return false;
        } else {
            String file = this.Queue.get(job - 1);
            this.Queue.remove(job - 1);
            this.Queue.add(0, file);
            return true;
        }
    }

    public String status () {
        return this.name + " has " + Queue.size() + " files in queue";
    }
}
