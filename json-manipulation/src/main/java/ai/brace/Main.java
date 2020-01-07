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

public class Main
{
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Hello, world.");


        Book json = GsonReader.readJsonFile("a1.json", Book.class);
        List<Comment> comments = json.getTextArray();
        comments = comments.stream().sorted(Comparator.comparing(Comment::getId)).collect(Collectors.toList());

        comments.forEach(comment -> System.out.println(comment.getTextdata()));
    }
}
