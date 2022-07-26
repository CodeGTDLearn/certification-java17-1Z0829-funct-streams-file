import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class IOFilesCopyMove {

  // File walker: Info about Directory
  private static void printDir(Path path) throws IOException {
    System.out.println(path + " is directory? " + Files.isDirectory(path));
    if (Files.isDirectory(path)) {
      try (Stream<Path> folders = Files.walk(path)) {
        folders.forEach((folder) -> System.out.println("\t\t" + folder));
      }
    }
  }

  // Executes Files.copy(Path source, Path target, CopyOption... options)
  private static void copyMethod(Path source, Path target) throws IOException {

    // Path is returned from this Files.copy method
    Path result = null;
    try {
      // Make first attempt without options
      System.out.println("First attempt, no options");
      result = Files.copy(source, target);

    }
    catch (IOException io) {

      System.out.println("\tFirst attempt failed: " + io);
      System.out.println("\tSecond attempt, Use REPLACE_EXISTING");
      try {
        // Make Second attempt with REPLACE_EXISTING options
        result = Files.copy(source, target,
                            StandardCopyOption.REPLACE_EXISTING
        );
      }
      catch (IOException ioNested) {
        System.out.println("\t\tSecond attempt failed: " + ioNested);
      }
    }
    if (result != null) {
      // Print information about result
      System.out.println("\tCopy was successful: " + result +
                              " : " + (Files.isDirectory(result) ? " Directory" : "File"));
      if (Files.isDirectory(result)) printDir(result);

    }
  }

  // Tests copying file to file
  private static void testFileToFile() throws IOException {

    Path fileSource = Path.of("copiedFrom.txt");
    Path fileTarget = Path.of("copiedTo.txt");

    // Delete source file if it exists.
    Files.deleteIfExists(fileSource);
    Files.deleteIfExists(fileTarget);

    // Create source file, add a String
    Files.writeString(fileSource, "Hello");

    // First Test - copy existing source file to a
    // non-existing target file
    System.out.println("--------------------------------------");
    System.out.println("Existing File to Non-Existing File");
    System.out.println("--------------------------------------");
    copyMethod(fileSource, fileTarget);

    // Second Test - copy existing source file to an
    // existing target file
    System.out.println("--------------------------------------");
    System.out.println("Existing File to Existing File");
    System.out.println("--------------------------------------");
    copyMethod(fileSource, fileTarget);
  }

  public static void main(String[] args) throws IOException {
    //        testFileToFile();
    testFileToDir();
  }

  // Uses Files.walk recursively to delete all elements in a directory.
  private static void deleteStreamDir(Path path) throws IOException {

    if (Files.isDirectory(path)) {
      // Walk using only a depth of 1
      try (Stream<Path> str = Files.walk(path, 1)) {
        str
             // walk returns all path-folders, filter the path needed
             .filter((path1) -> ! path1.equals(path))

             // Recursively execute this method.
             .forEach((s) ->
                      {
                        try {
                          IOFilesCopyMove.deleteStreamDir(s);
                        }
                        catch (IOException io) {}});
      }
    }
    // Finally delete current element
    Files.deleteIfExists(path);
  }

  // Tests copying a File to a existing Directory
  private static void testFileToDir() throws IOException {

    Path fileSource = Path.of("copiedFrom.txt");
    Path directoryTarget = Path.of("copiedToDir");

    // Delete source file if it exists.
    Files.deleteIfExists(fileSource);
    // Create source file, add a String
    Files.writeString(fileSource, "Hello");

    // Delete target directory if it exists.
    deleteStreamDir(directoryTarget);
    // Recreate target directory
    Files.createDirectory(directoryTarget);

    System.out.println("--------------------------------------");
    System.out.println("Existing File to Existing Empty Directory");
    System.out.println("--------------------------------------");
    // First Test - copy existing source file to a
    // existing directory
    printDir(directoryTarget);
    copyMethod(fileSource, directoryTarget);

    System.out.println("--------------------------------------");
    System.out.println("Existing File to Existing Non-Empty Directory");
    System.out.println("--------------------------------------");
    // Second Test - copy existing source file to a directory that
    // is not empty
    Files.deleteIfExists(directoryTarget);
    directoryTarget = Path.of("copiedToDir/subDirectory");
    // Create a directory with a sub directory
    Files.createDirectories(directoryTarget);
    // Use the printDir method to print contents of copied directory
    printDir(directoryTarget.getParent());
    copyMethod(fileSource, directoryTarget.getParent());

  }
}