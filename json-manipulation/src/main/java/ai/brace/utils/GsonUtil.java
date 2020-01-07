package ai.brace.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GsonUtil {

  public static <T> T readJsonFile(String fileName, Class<T> classtype ) throws FileNotFoundException {
    ClassLoader classLoader = new GsonUtil().getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());

    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    Gson gson = new Gson();
    T json = gson.fromJson(bufferedReader, classtype);

    return json;
  }

  public static <T> void writeJsonFile(T object, String fileName )
      throws FileNotFoundException, URISyntaxException {
    URL url = new GsonUtil().getClass().getResource("/");
    File resourcesDirectory = new File(new URI(url.toString()));
    File file = new File(resourcesDirectory, fileName);

    try (Writer writer = new FileWriter(file)) {
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      gson.toJson(object, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

