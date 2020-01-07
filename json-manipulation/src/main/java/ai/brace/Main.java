package ai.brace;

import ai.brace.entity.Book;
import ai.brace.entity.Comment;
import ai.brace.utils.GsonReader;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello, world.");

        // task1
        System.out.println("####Task1");
        Book a1 = GsonReader.readJsonFile("a1.json", Book.class);
        List<Comment> a1Comments = a1.getTextArray();
        a1Comments = a1Comments.stream().sorted(Comparator.comparing(Comment::getId)).collect(Collectors.toList());
        a1Comments.forEach(comment -> System.out.println(comment.getTextdata()));
        System.out.println("\n\n");

        // task2
        System.out.println("####Task2");
        Book a2 =  GsonReader.readJsonFile("a2.json", Book.class);
        List<Comment> a2Comments = a2.getTextArray();
        List<Comment> comments = Stream.of(a1Comments, a2Comments)
            .flatMap(x -> x.stream())// merge
            .sorted(Comparator.comparing(Comment::getId)) // sort
            .collect(Collectors.toList());
        comments.forEach(comment -> System.out.println(comment.getTextdata()));
        System.out.println("\n\n");

        // task3
        System.out.println("####Task3");
        Map<String, Integer> wordCount = comments.parallelStream()
            .map(comment -> comment.getTextdata())
            .map(text -> text.replaceAll("[^a-zA-Z]", " ").toLowerCase().trim())
            .map(text->text.split(" "))
            .flatMap(word -> Stream.of(word))
            .filter(text -> text.length() > 0)
            .collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));

        List<String> keys = new ArrayList(wordCount.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareTo(s2);
            }
        });
        keys.forEach(key-> System.out.printf("(%s) : %d\n", key, wordCount.get(key)));
    }

}
