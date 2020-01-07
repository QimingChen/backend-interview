package ai.brace;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class EmployeeProcessor
{
    public static Path getPathForResource(final String path)
    {
        try
        {
            return Paths.get(ClassLoader.getSystemResource(path).toURI());
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        final Map<Employee, Integer> duplicateCount = new HashMap<>();

        try (Stream<String> stream = Files.lines(getPathForResource("employees.csv")))
        {
            stream.forEach(line -> {
                final String[] elements = line.split(",");
                final Employee emp = new Employee(elements[0], elements[1], elements[2], elements[3]);

                // if using an object as key, it directly uses the address it has
                // so each address is different, that's the culprit for the duplicates
                // To make object as key and perform correctly
                // override the hashCode and equals method in the Employee class
                duplicateCount.put(emp, duplicateCount.getOrDefault(emp, 0) + 1);
            });
        }
        catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }

        for (Map.Entry<Employee, Integer> entry : duplicateCount.entrySet())
        {
            final Employee emp = entry.getKey();
            System.out.println(emp.firstName + " " + emp.middleInitial + " " + emp.lastName + ": " + entry.getValue());
        }
    }
}
