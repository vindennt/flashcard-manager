//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import model.Deck;
import org.json.JSONObject;

public class JsonWriter {
    public static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws FileNotFoundException {
        this.writer = new PrintWriter(new File(this.destination));
    }

    public void write(Deck d) {
        // TODO implement into deck
        JSONObject json = d.toJson();
        this.saveToFile(json.toString(TAB));
    }

    public void close() {
        this.writer.close();
    }

    private void saveToFile(String json) {
        this.writer.print(json);
    }
}
