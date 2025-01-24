package com.rainer.veebipood.service;


import com.rainer.veebipood.entity.Histogram;
import com.rainer.veebipood.entity.LogValues;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.Integer.parseInt;

@Service
public class TestService {
    private static final String filePath = "D:/Programming/Java/veebipood/src/main/java/com/rainer/veebipood/files/";
    static String fileName = "timing.log";
    static long startTime = System.currentTimeMillis();
    static long endTime;


    public List<Histogram> getMinuteHistogramParse() {
        List<Histogram> final_list = histogramListParse();
        endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime);
        System.out.println("Total time in ms: " + totalTime);
        System.out.println(final_list);
        return final_list;
    }


    public static List<Histogram> histogramListParse() {
        List<Histogram> list = new ArrayList<>();
        int TIME_INDEX = 2;

        try {
            File myFile = fileCheck();
            Scanner readFile = new Scanner(myFile);
            while (readFile.hasNextLine()) {
                String data = readFile.nextLine();
                String[] split_array = data.split("[:\\s+]");
                String time_name = split_array[TIME_INDEX];
                boolean found = false;
                for (Histogram histogram : list) {
                    if (histogram.getTime().contains(time_name)) {
                        int count = histogram.getCount() + 1;
                        histogram.setCount(count);

                        found = true;
                        break;
                    }
                }
                if (!found) {
                    Histogram histogram = new Histogram();
                    histogram.setTime(time_name);
                    histogram.setCount(parseInt(split_array[TIME_INDEX]));
                    list.add(histogram);
                }
            }
            readFile.close();

        }
        catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return list;
    }

    public List<LogValues> getDurationParse(int topNumber) {
        List<LogValues> list = parseFileByNumber(topNumber);
        endTime = System.currentTimeMillis();
        double totalTime = (endTime - startTime);
        System.out.println("Total time in ms: " + totalTime);
        return list;
    }
    public static List<LogValues> parseFileByNumber(int top_Number) {
        // + I want to save unique strings, the count of unique strings and the total duration
        // + make list by all the returned objects
        // + sort the new array by numbers.
        // + return top N of those.
        List<LogValues> list = new ArrayList<>();
        List<LogValues> newList = new ArrayList<>();

        int URI_REQUEST = 4;
        try {
            File myFile = fileCheck();
            Scanner readFile = new Scanner(myFile);
            while (readFile.hasNextLine()) {
                String data = readFile.nextLine();
                String[] split_array = data.split("\\s+");
                String[] split_arrayTwo = split_array[URI_REQUEST].split(".do?");
                String requestName = split_arrayTwo[0] + ".do?";

                int MS_DURATION = parseInt(split_array[split_array.length - 1]);

                boolean found = false;
                for (LogValues logValue : list) {
                    if (logValue.getRequest_name().contains(requestName)) {
                        // get
                        int duration = logValue.getResponse_duration() + MS_DURATION;
                        int count = logValue.getRequest_count() + 1;
                        // set
                        logValue.setResponse_duration(duration);
                        logValue.setRequest_count(count);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    LogValues logValues = setLogValue(requestName, 1, MS_DURATION);
                    list.add(logValues);
                }

            }
            readFile.close();
            list.sort((o1, o2) -> Integer.compare(o2.getResponseAverageDuration(), o1.getResponseAverageDuration()));

            //  filter by top_number
            if (top_Number == 0) {
                return list;
            } else {
                list = list.subList(0, top_Number);
                return list;
            }


        }
        catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return list;
    }

    public static LogValues setLogValue(String request_name, int count, int duration) {
        LogValues logValues = new LogValues();
        logValues.setRequest_name(request_name);
        logValues.setRequest_count(count);
        logValues.setResponse_duration(duration);
        return logValues;
    }


    static File fileCheck() {
        String path = filePath + fileName;
        File myFile = new File(path);
        File emptyFile = new File("");

        if (!myFile.exists()) {
            System.out.println("File does not exist");
            return emptyFile;
        }
        return myFile;
    }
}
