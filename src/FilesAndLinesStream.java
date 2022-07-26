import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FilesAndLinesStream {
  public static void main(String[] args) {

    Path pathCsvFile = Paths.get("SomeData.csv");
    createTheFile(pathCsvFile);


    // NO-STREAM: Use try/resources||try/catch directory closed
    try {
      Files.readAllLines(pathCsvFile)
           .forEach(System.out::println);
    }
    catch (IOException io) {  System.out.println(io);  }


    // STREAM-LINE: Use try/resources||try/catch directory closed
    try (Stream<String> str = Files.lines(pathCsvFile)) {
      str
           .forEach(System.out::println);
    }
    catch (IOException io) {  System.out.println(io);  }

  }

  private static void createTheFile(Path pathFile) {

    try {
      Files.deleteIfExists(pathFile);

      List<String> list = List.of(
           "1,George,ABC",
           "2,Carol,DEF",
           "3,Mary,EFG",
           "4,Ralph,ABC",
           "5,Arthur,ABC",
           "6,Maggie,DEF",
           "7,Brandy,EFG"
      );

      // Write List<String to the file in a single line
      Files.write(pathFile, list);

    }
    catch (IOException io) {
      System.out.println(io);
    }
  }
}