package filesprocessing;

import java.io.File;
import java.util.Comparator;

/**
 * Order factory - defines the order in which the files will be arranged.
 */
public abstract class OrderFactory {
    private static int BYTECONVERTER = 1024;

    public static Comparator<File> DEFAULT = (file1, file2) ->
            file1.getAbsolutePath().compareTo(file2.getAbsolutePath());

    /**
     * Picks the order operation and implements it.
     * @param section - section of filter and order.
     * @return comparator of files.
     */
    public static Comparator<File> simpleChooseOrder(Section section) throws ExceptionErrorOrder {
        String[] order = section.getSplitOrder();
        Comparator<File> compareFile;
        switch (order[0]) {
            case "abs":
                compareFile = (file1, file2) -> file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
                break;
            case "type":
                compareFile = (file1, file2) -> file1.getName().substring(file1.getName().lastIndexOf(".")).compareTo
                        (file2.getName().substring(file2.getName().lastIndexOf(".")));
                break;
            case "size":
                compareFile = (file1, file2) -> Double.compare(file1.length()*BYTECONVERTER, file2.length()*BYTECONVERTER);
                break;
            default:
                    throw new ExceptionErrorOrder(section.line + 3);
        } return compareFile.thenComparing(DEFAULT);
    }

    /**
     * A function that takes the comparator and reverses it's order if needed.
     * @param comparator the current comparator
     * @param section the section that determines the comparators tendency
     * @return - comparator, reversed if expected, otherwise regular.
     */
    public static Comparator<File> toReverse(Comparator<File> comparator, Section section) {
        String[] order = section.getSplitOrder();
        if (order.length>1 && order[1].equals("REVERSE"))
            return comparator.reversed();
        else
            return comparator;
    }

    /**
     * The funtion that chooses the order by checking the basic order, and then checking if it needs to be reversed.
     * @param section that determines the order
     * @return returns the relevant comparator.
     * @throws ExceptionErrorOrder
     */
    public static Comparator<File> chooseOrder(Section section) throws ExceptionErrorOrder {
        Comparator<File> comparator = simpleChooseOrder(section);
        return toReverse(comparator,section);

    }
}


