////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package persistence;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Iterator;
//import java.util.stream.Stream;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class JsonReader {
//    private String source;
//
//    public JsonReader(String source) {
//        this.source = source;
//    }
//
//    public Deck read() throws IOException {
//        String jsonData = this.readFile(this.source);
//        JSONObject jsonObject = new JSONObject(jsonData);
//        return this.parseDeck(jsonObject);
//    }
//
//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
//        Throwable var4 = null;
//
//        try {
//            stream.forEach((s) -> {
//                contentBuilder.append(s);
//            });
//        } catch (Throwable var13) {
//            var4 = var13;
//            throw var13;
//        } finally {
//            if (stream != null) {
//                if (var4 != null) {
//                    try {
//                        stream.close();
//                    } catch (Throwable var12) {
//                        var4.addSuppressed(var12);
//                    }
//                } else {
//                    stream.close();
//                }
//            }
//
//        }
//
//        return contentBuilder.toString();
//    }
//
//    private Deck parseDeck(JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        Deck wr = new Deck(name);
//        this.addThingies(wr, jsonObject);
//        return wr;
//    }
//
//    private void addThingies(Deck d, JSONObject jsonObject) {
//        JSONArray jsonArray = jsonObject.getJSONArray("thingies");
//        Iterator var4 = jsonArray.iterator();
//
//        while(var4.hasNext()) {
//            Object json = var4.next();
//            JSONObject nextThingy = (JSONObject)json;
//            this.addThingy(wr, nextThingy);
//        }
//
//    }
//
//    private void addThingy(Deck d, JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
//        Category category = Category.valueOf(jsonObject.getString("category"));
//        Thingy thingy = new Thingy(name, category);
//        wr.addThingy(thingy);
//    }
//}
