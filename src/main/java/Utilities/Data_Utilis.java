package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Data_Utilis {
    public final static String TEST_DATA_PATH =  "src/test/resources/TestData/";

    //TODO: Read Data From Json File

    public static String getJsonData(String jsonFileName , String field ) throws FileNotFoundException {
        try {
                  /// define object of file Reader
        FileReader reader = new FileReader(TEST_DATA_PATH + jsonFileName + ".json");

                /// parse The Json Directory
        JsonElement jsonElement = JsonParser.parseReader(reader);
        return jsonElement.getAsJsonObject().get(field).getAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //TODO: get Properties from any .property file

    public static String getPropertyValue(String filename , String key ) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TEST_DATA_PATH + filename + ".properties"));
        return properties.getProperty(key);
    }
}
