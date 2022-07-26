import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FilesAndStreams {
  public static void main(String[] args) {

    // STREAM-LIST: Use try/resources||try/catch directory closed
    Path src = Paths.get("src");
//    try (Stream<Path> str = Files.list(src)) {
//      str
//           .limit(5)
//           .forEach(System.out::println);
//    }
//    catch (IOException io) {
//      System.out.println("Problem: " + io);
//    }
//    //------------------------------------------------------------------------------
//    // STREAM-WALK: Use try/resources||try/catch directory closed
//    Path out = Paths.get("testA");
//    try (Stream<Path> str = Files.walk(out)) {
//      str.limit(8)
//         .forEach(System.out::println);
//    }
//    catch (IOException io) {
//      System.out.println("Problem: " + io);
//    }
//    //------------------------------------------------------------------------------
//    try (Stream<Path> str = Files.walk(out, 1)) {
//      str.limit(8)
//         .forEach(System.out::println);
//    }
//    catch (IOException io) {
//      System.out.println("Problem: " + io);
//    }
//    //------------------------------------------------------------------------------
//    try (Stream<Path> str = Files.walk(out)) {
//      str.filter(file -> Files.isRegularFile(file))
//         .limit(8)
//         .forEach(System.out::println);
//    }
//    catch (IOException io) {
//      System.out.println("Problem: " + io);
//    }
    //------------------------------------------------------------------------------

    // Use try/resources||try/catch directory closed
    src = Paths.get("src");
    try (Stream<Path> str =
              Files.find(
                   src,
                   5,
                   (file, basicFileAttributes) -> {
                     return basicFileAttributes.isRegularFile(); }))
    {
      str
           .limit(8)
           .forEach(System.out::println);
    }
    catch (IOException io) {System.out.println("Problem: " + io);}
  }
}