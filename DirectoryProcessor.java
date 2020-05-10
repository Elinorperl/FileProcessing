package filesprocessing;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The "manager" of the whole operation. Serves as the middle man for all the classes, directing the
 * work.
 */
public class DirectoryProcessor {
    private static final int ARGUMENTNUMBER = 2;

    /**
     * A function that manages our operation by converting two of our strings into files
     * and sending them through the program to be filtered.
     * @param file1 - The directory files, for which we must filter.
     * @param file2 - The filter commands, how to filter our file.
     */
    public static void filterCommands(String file1, String file2) {
        File commandFile = new File(file2);
        File[] sourceFiles = new File(file1).listFiles(File::isFile);
        List<List<String>> parsedFile;
        Predicate<File> filter;
        Comparator<File> comparator;
        try {
            parsedFile = Parser.parsingFile(commandFile);
        } catch (ExceptionError2 error2) {
            System.err.println("ERROR");
            return;
        }
        for (List<String> list : parsedFile) {
            Section section;
            try {
                section = new Section(Parser.checkSection(list));
            } catch (ExceptionError2 error2) {
                System.err.println("ERROR");
                return;
            }
            try {
                filter = FilterFactory.chooseFilter(section);
                comparator = OrderFactory.chooseOrder(section);
                printFiles(sourceFiles, filter, comparator);
            } catch (ExceptionErrorFilter filtererror) {
                try {
                    printFiles(sourceFiles, FilterFactory.defaultPredicate(), OrderFactory.chooseOrder(section));
                } catch (ExceptionErrorOrder order){
                    comparator = OrderFactory.DEFAULT;
                    printFiles(sourceFiles, FilterFactory.defaultPredicate(), comparator);
                }
            } catch (ExceptionErrorOrder ordererror)  {
                try {
                    comparator = OrderFactory.DEFAULT;
                    printFiles(sourceFiles, FilterFactory.chooseFilter(section), comparator);
                } catch (ExceptionErrorFilter filter1){
                    comparator = OrderFactory.DEFAULT;
                    printFiles(sourceFiles, FilterFactory.defaultPredicate(), comparator);
                }
            }
        }
    }

    /**
     * Prints the relevant files.
     * @param files - Sourcefiles, for which we'd like to filter and order
     * @param predicate - the predicate that filters our files
     * @param comparator - comparator for which we order.
     */
    private static void printFiles(File[] files, Predicate<File> predicate, Comparator<File> comparator) {
        Consumer<File> consumer = file -> System.out.println(file.getName());
        Arrays.stream(files).filter(predicate).sorted(comparator).forEach(consumer);
    }


    /**
     * The "main" function that operates the filter.
     *
     * @param args - arguments given by the user (supposed to be a directory and file)
     * @throws ExceptionError2
     * @throws ExceptionError1
     */
    public static void main(String[] args) throws ExceptionError2, ExceptionError1 {
        if (args.length != ARGUMENTNUMBER)
            System.err.println("Arguments must take two files");
        else
            filterCommands(args[0], args[1]);
        }
    }
