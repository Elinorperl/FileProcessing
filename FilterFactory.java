package filesprocessing;
import java.io.File;
import java.util.function.Predicate;

/**
 * Factory containing all the filters.
 */
public abstract class FilterFactory {
    private static int BYTECONVERTER = 1024;
    /**
     * A function that filters the file according to the input.
     *@param section - a section of filter and order
     *@return a lambda function is returned to filter the files according to the input
     */
    public static Predicate<File> chooseFilter(Section section) throws ExceptionErrorFilter {
        String[] filter = section.getSplitFilter();
        String filterType = filter[0];
        Double firstValue;
        Double secondValue;
        String stringValue;
        Predicate<File> fileFilter;
        Boolean isNot = false;
        Boolean isTrue = true;

        for (String value : filter) {
            if (value.equals("NOT"))
                isNot = true;
            if (value.equals("NO"))
                isTrue = false;
        }

        switch (filterType) {
            case "greater_than":
                firstValue = Double.parseDouble(filter[1]);
                fileFilter = file -> file.length()/BYTECONVERTER > firstValue;
                break;
            case "between":
                firstValue = Double.parseDouble(filter[1]);
                secondValue = Double.parseDouble(filter[2]);
                if (firstValue>secondValue) {
                    throw new ExceptionErrorFilter(section.line+1);
                }
                fileFilter = file -> (file.length()/BYTECONVERTER >= firstValue) &&
                        (file.length()/BYTECONVERTER <= secondValue);
                break;
            case "smaller_than":
                firstValue = Double.parseDouble(filter[1]);
                fileFilter = file -> file.length()/BYTECONVERTER < firstValue;
                break;
            case "file":
                stringValue = filter[1];
                fileFilter = file -> file.getName().equals(stringValue);
                break;
            case "contains":
                stringValue = filter[1];
                fileFilter = file -> file.getName().contains(stringValue);
                break;
            case "prefix":
                stringValue = filter[1];
                fileFilter = file -> file.getName().startsWith(stringValue);
                break;
            case "suffix":
                stringValue = filter[1];
                fileFilter = File -> File.getName().endsWith(stringValue);
                break;
            case "writable":
                stringValue = filter[1];
                if (!stringValue.equals("YES") && !stringValue.equals("NO"))
                    throw new ExceptionErrorFilter(section.line+1);
                fileFilter = file -> (file.canWrite());
                if (!isTrue)
                    fileFilter = file -> (!file.canWrite());
                break;
            case "executable":
                stringValue = filter[1];
                if (!stringValue.equals("YES") && !stringValue.equals("NO"))
                    throw new ExceptionErrorFilter(section.line+1);
                fileFilter = file -> (file.canExecute());
                if (!isTrue)
                    fileFilter = file -> (!file.canExecute());
                break;
            case "hidden":
                stringValue = filter[1];
                if (!stringValue.equals("YES") && !stringValue.equals("NO"))
                    throw new ExceptionErrorFilter(section.line+1);
                fileFilter = file -> (file.isHidden());
                if (!isTrue)
                    fileFilter = file -> (!file.isHidden());
                break;
            case "all":
                fileFilter = file -> true;
                break;
            default:
                throw new ExceptionErrorFilter(section.line+1);
        }
        if (isNot) {
            return fileFilter.negate();
        }
        return fileFilter;
    }

    /**
     * The default predicate.
     * @return default predicate function.
     */
    public static Predicate<File> defaultPredicate() {
        return file -> true;
    }

}
