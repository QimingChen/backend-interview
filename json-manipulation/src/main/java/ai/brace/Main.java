package ai.brace;

import ai.brace.entity.Book;
import ai.brace.entity.Comment;
import ai.brace.utils.GsonReader;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
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
    }
}
