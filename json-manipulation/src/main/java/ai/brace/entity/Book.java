package ai.brace.entity;

import ai.brace.utils.TimeUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Book {

  private String version;
  private String uuid;
  private String lastModified;
  private String title;
  private String author;
  private String translator;
  private String releaseDate;
  private String language;
  private List<Comment> textArray;

  public Book() {

  }

  public static Book merge(List<Book> books){
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

    return latestBook;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public String getLastModified() {
    return lastModified;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTranslator() {
    return translator;
  }

  public void setTranslator(String translator) {
    this.translator = translator;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public List<Comment> getTextArray() {
    return textArray;
  }

  public void setTextArray(List<Comment> textArray) {
    this.textArray = textArray;
  }
}
