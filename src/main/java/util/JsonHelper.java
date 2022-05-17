package util;

import boardgame.Main;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import util.result.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    /**
     * Method used to write all the current Game Data into the data.json file.
     * @param result object of class GameData containing information to be written into data.json file
     */
    public static void write(Result result) {
        File file = new File(Main.class.getClassLoader().getResource("games.json").getFile());
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        try {
            List<Result> gameDataList = new ArrayList<>();
            if (file.length() != 0) {
                gameDataList = mapper.readValue(file, new TypeReference<>() {
                });
            }
            gameDataList.add(result);
            writer.writeValue(file, gameDataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to read the data.json file.
     * @return the data.json File
     */
    public static File read() {
        return new File(Main.class.getClassLoader().getResource("games.json").getFile());
    }
}


