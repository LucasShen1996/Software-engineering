package util;

import org.apache.poi.ss.usermodel.Cell;

public class VerifyString {

    public static boolean stringIsInteger(String str){
        char[] stringArr = str.toCharArray();
        for (char s : stringArr){
            if (!Character.isDigit(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * To check whether a string is Letter
     *
     * @param: The String to be check
     * @return: a boolean indicates the result
     */
    public static boolean stringLetter(String aString)
    {
        if (aString.length() == 0 ||aString.trim().length() == 0)
            return false;
        for (int index = 0; index < aString.length(); index++)
        {
            char thisCharacter = aString.charAt(index);
            if(Character.isLetter(thisCharacter) != true)
                return false;
        }
        return true;
    }

    /**
     * convertCellValueToString
     *
     * @param cell
     * @return
     */
    public static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:
                Double doubleValue = cell.getNumericCellValue();

                returnValue = doubleValue.toString();
                break;
            case STRING:
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:
                break;
            case FORMULA:
                returnValue = cell.getCellFormula();
                break;
            case ERROR:
                break;
            default:
                break;
        }
        return returnValue;
    }
}

