package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormatRow {

    /***
     * Format one row with a format.
     * @param str           The String that is going to output in this row
     * @param totalLength   The minimum length of this row
     * @param type          The type of alignment ("centre" / "left" / "right")
     */
    public static String formatOneRow(String str, int totalLength, String type){

        String result = "";
        String begin = "|";
        String end = "";
        int strLength = str.length();
        int totalSpace = 0;

        switch (type){
            case "centre":
                int beginSpace = (totalLength - strLength) / 2;
                begin += loopAdd(" ", beginSpace - 1);
                int endSpace = (totalLength - strLength) - ((totalLength - strLength) / 2);
                end += loopAdd(" ", endSpace - 1);
                end += "|";
                result = begin + str + end;
                break;

            case "left":
                begin = "|";
                end = "";
                totalSpace = totalLength - strLength - 2;
                if (totalSpace > 4) {
                    begin += "  ";
                    end += loopAdd(" ", totalSpace - 2);
                    end += "|";
                    result = begin + str + end;
                    break;
                } else {
                    formatOneRow(str, totalLength, "centre");
                }

            case "right":
                begin = "|";
                end = "";
                totalSpace = totalLength - strLength - 2;
                if (totalSpace > 4) {
                    end += "  ";
                    begin += loopAdd(" ", totalSpace - 2);
                    end += "|";
                    result = begin + str + end;
                    break;
                } else {
                    formatOneRow(str, totalLength, "centre");
                }
        }
        return result;
    }

    public static String formatOneTable(List<List> dataList, int totalLength, int cellNumber){

        String result = "";
        List<Integer> dataLengthList = getEachCellLengthTable(dataList, cellNumber);

        for (int j = 0; j < dataList.size(); j++) {
            for (int i = 0; i < dataList.get(j).size(); i++) {
                // Strings in list

                List<String> data = dataList.get(j);
                String begin = (i != 0) ? "" : "|";
                String end = "";
                int strLength = data.get(i).length();

                int beginSpace = (dataLengthList.get(i) - strLength) / 2;
                begin += loopAdd(" ", beginSpace);
                int endSpace = (dataLengthList.get(i) - strLength) - ((dataLengthList.get(i) - strLength) / 2);
                end += loopAdd(" ", endSpace);

                result += begin + data.get(i) + end;
                result += "|";

            }

            result +=  (j != dataList.size() - 1) ? "\n" : "";
        }

        return result;
    }

    public static List<Integer> getEachCellLengthTable(List<List> dataList, int cellNumber){
        List<Integer> dataLengthList = new ArrayList<Integer>();
        for (int i = 0; i < cellNumber; i++) {
            List<Integer> lengthList = new ArrayList<Integer>();
            for (int j = 0; j < dataList.size(); j++) {
                List<String> data = dataList.get(j);
                lengthList.add(data.get(i).length());
            }
            dataLengthList.add(Collections.max(lengthList));
        }
        return dataLengthList;
    }

    /***
     * Loop increments character.
     * @param str       The increased String
     * @param number    Total number of times increased
     * @return          Increased String
     */
    public static String loopAdd(String str, int number){
        String result = "";
        for (int i = 0; i < number; i++){
            result += str;
        }
        return result;
    }

}
