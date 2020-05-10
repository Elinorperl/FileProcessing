package filesprocessing;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser splits the file up.
 */
public class Parser {
    private static int lineCounter;
    private static final int SECTIONSIZE = 5;
    private static final int DEFAULTSECTIONSIZE = 4;

    /**
     * @param file - parses the file into a list of lists (the inner lists are our potential "sections")
     * @return the list of lists of sections.
     * @throws ExceptionError2
     */
    public static List<List<String>> parsingFile(File file) throws ExceptionError2 {
        lineCounter = 0;
        String path = file.getPath();
        List<List<String>> sections;
        List<String> readFile = null;
        try {
            readFile = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            throw new ExceptionError2("Bad format");
        }
        sections = new ArrayList<List<String>>();
        List<String> section = new ArrayList<String>();

        for (String part : readFile) {
            if (lineCounter == 0 && !part.equals("FILTER"))
                throw new ExceptionError2("ERROR");
            lineCounter++;
            if (part.equals("FILTER")) {
                section = new ArrayList<String>();
                sections.add(section);
                section.add(Integer.toString(lineCounter));
            }
            section.add(part);
        }
        return sections;

    }


    /**
     * Checks problems within each sections.
     * @param section - one of the sections to check
     */
    public static String[] checkSection(List<String> section) throws ExceptionError2 {
        String[] workingSection = new String[3];
        if (section.size() == DEFAULTSECTIONSIZE || section.size() == SECTIONSIZE) {
            if (section.size() == DEFAULTSECTIONSIZE) {
                if (section.get(1).equals("FILTER"))
                    if (section.get(3).equals("ORDER")) {
                        workingSection[0] = Integer.toString(Integer.parseInt(section.get(0)));
                        workingSection[1] = section.get(2);
                        workingSection[2] = "abs";
                    }else
                        throw new ExceptionError2("ERROR");
            } else if (section.size() == SECTIONSIZE) {
                if (section.get(1).equals("FILTER"))
                    if (section.get(3).equals("ORDER")) {
                        workingSection[0] = Integer.toString(Integer.parseInt(section.get(0)));
                        workingSection[1] = section.get(2);
                        workingSection[2] = section.get(4);
                    } else
                        throw new ExceptionError2("ERROR");
            }return workingSection;
        } else
            throw new ExceptionError2("ERROR");
    }


}