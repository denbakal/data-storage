package ua.challenge.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    public static List<String> readAsStrings(String url) throws IOException {
        File file = new File(CsvReader.class.getClassLoader().getResource(url).getFile());
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static ArrayList extractFromCommas(String dataLine) {
        ArrayList data = new ArrayList();
        String theString = "";
        for (int i = 0; i < dataLine.length(); i++) { //go down the whole string
            if (dataLine.charAt(i) == ',') {
                if (i == 0) {
                    //do nothing
                } else {
                    data.add(theString); //this means that the next comma has been reached
                    theString = ""; //reset theString Variable
                }
            } else {
                theString = theString + dataLine.charAt(i); //otherwise, just keep piling the chars onto the cumulative string
            }
        }
        if (!theString.equalsIgnoreCase("")) //only if the last position is not occupied with nothing then add the end on
        {
            data.add(theString);
        }
        return data;
    }
}
