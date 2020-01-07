package ai.brace;

import ai.brace.entity.Book;
import ai.brace.entity.Comment;
import ai.brace.utils.GsonUtil;
import ai.brace.utils.TimeUtil;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

  public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
    System.out.println("Hello, world.");

    // task1
    // 1. load a1.json
    // 2. extract textArray
    // 3. sort ascendingly
    // 4. printout
    System.out.println("####Task1");
    Book a1 = GsonUtil.readJsonFile("a1.json", Book.class);
    List<Comment> a1Comments = a1.getTextArray();
    a1Comments = a1Comments.stream()
        .sorted(Comparator.comparing(Comment::getId)) // sort ascendingly
        .collect(Collectors.toList());
    a1Comments.forEach(comment -> System.out.println(comment.getTextdata()));
    System.out.println("\n\n");

    // task2
    // 1. load a2.json, reuse a1 object from task1
    // 2. extract textArray
    // 3. merge and sort ascendingly
    // 4. printout
    System.out.println("####Task2");
    Book a2 = GsonUtil.readJsonFile("a2.json", Book.class);
    List<Comment> a2Comments = a2.getTextArray();
    List<Comment> comments = Stream.of(a1Comments, a2Comments)
        .flatMap(x -> x.stream())// merge
        .sorted(Comparator.comparing(Comment::getId)) // sort
        .collect(Collectors.toList());
    comments.forEach(comment -> System.out.println(comment.getTextdata()));
    System.out.println("\n\n");

    // task3
    // 1. reuse merged comments object (textArray from a1 and a2)
    // 2. extract textdata, compile with pattern and normalize case
    // 3. split and filter empty string
    // 4. do sum
    // 5. sort by word
    // 6. printout
    System.out.println("####Task3");
    Map<String, Integer> wordCount = comments.parallelStream()
        .map(comment -> comment.getTextdata()) // get textdata
        .map(text -> text.replaceAll("[^a-zA-Z]", " ").toLowerCase().trim()) // regex
        .map(text -> text.split(" ")) // split paragraph to words
        .flatMap(wordArray -> Stream.of(wordArray)) // cast String array to String
        .filter(text -> text.length() > 0) // filter empty String
        .collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum)); // map-reduce

    List<String> keys = new ArrayList(wordCount.keySet());
    Collections.sort(keys, new Comparator<String>() {
      @Override
      public int compare(String s1, String s2) {
        return s1.compareTo(s2); // ascending order
      }
    });
    keys.forEach(key -> System.out.printf("(%s) : %d\n", key, wordCount.get(key)));
    System.out.println("\n\n");

    // task4
    // 1. reuse object a1, a2
    // 2. sort by lastModified, descendingly
    // 3. the first one is the latest, and iterate the list to increment the latest contents
    // 4. update uuid and change lastmodified format from epoch seconds to ISO
    // 5. write to the same folder as the input locates (resources folder)
    System.out.println("####Task4");
    List<Book> books = new ArrayList<>();
    books.add(a1);
    books.add(a2);
    Collections.sort(books, new Comparator<Book>() {
      @Override
      public int compare(Book o1, Book o2) {
        return o2.getLastModified().compareTo(o1.getLastModified()); // reverse sort
      }
    });
    Book latestBook = books.get(0);
    books.remove(0);
    for (Book b : books) {
      if (latestBook.getVersion() == null || latestBook.getVersion().length() == 0) {
        latestBook.setVersion(b.getVersion());
      }
      if (latestBook.getLastModified() == null || latestBook.getLastModified().length() == 0) {
        latestBook.setLastModified(b.getLastModified());
      }
      if (latestBook.getTitle() == null || latestBook.getTitle().length() == 0) {
        latestBook.setTitle(b.getTitle());
      }
      if (latestBook.getAuthor() == null || latestBook.getAuthor().length() == 0) {
        latestBook.setAuthor(b.getAuthor());
      }
      if (latestBook.getTranslator() == null || latestBook.getTranslator().length() == 0) {
        latestBook.setTranslator(b.getTranslator());
      }
      if (latestBook.getLanguage() == null || latestBook.getLanguage().length() == 0) {
        latestBook.setLanguage(b.getLanguage());
      }
      if (latestBook.getReleaseDate() == null || latestBook.getReleaseDate().length() == 0) {
        latestBook.setReleaseDate(b.getReleaseDate());
      }

      for (Comment c : b.getTextArray()) {
        if (!latestBook.getTextArray().stream().anyMatch(t -> t.getId() == c.getId())) {
          latestBook.getTextArray().add(c);
        }
      }
    }

    latestBook.setUuid(UUID.randomUUID().toString());
    latestBook.setLastModified(TimeUtil.epochToIso(Long.parseLong(latestBook.getLastModified())));
    latestBook.setTextArray(
        latestBook.getTextArray().stream()
            .sorted(Comparator.comparing(Comment::getId))
            .collect(Collectors.toList()));

    GsonUtil.writeJsonFile(latestBook, "../../../../src/main/resources/newOutput.json");
    System.out.println("program ends");
  }
}
