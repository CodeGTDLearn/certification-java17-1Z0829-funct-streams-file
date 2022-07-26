import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilesAndStreams {
    public static void main(String[] args)  {

        Path src = Paths.get("src");
        System.out.println("-----------------------------------");
        System.out.println("Listing of " + src + " Directory");
        System.out.println("-----------------------------------");

        // Need to wrap in try/resources or try/catch so that directory
        // is appropriately closed.
        try (Stream<Path> str = Files.list(src)) {
            str
                    .limit(5)  // Can use any stream operation 
                    .forEach(System.out::println);
        } catch (IOException io) {
            System.out.println("Problem with listing " + io);
        }

        Path out = Paths.get("testA");
        System.out.println("-----------------------------------");
        System.out.println("Walk of " + out + " Directory");
        System.out.println("-----------------------------------");

        // Need to wrap in try/resources or try/catch so that directory
        // is appropriately closed
        try (Stream<Path> str = Files.walk(out)) {
            str
                    .limit(8) // Can use any stream operation
                    .forEach(System.out::println);
        } catch (IOException io) {
            System.out.println("Problem with walk " + io);
        }
    }
}