package ai.brace.utils;

import ai.brace.Main;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GsonReader {

  public static <T> T readJsonFile(String fileName, Class<T> classtype ) throws FileNotFoundException {
    ClassLoader classLoader = new GsonReader().getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());

    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    Gson gson = new Gson();
    T json = gson.fromJson(bufferedReader, classtype);

    return json;
  }
}

