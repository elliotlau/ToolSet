package indi.elliot.toolset;
import indi.elliot.toolset.utils.PDFUtil;

import static indi.elliot.toolset.constants.ToolSetConstant.*;

public class ToolSet {

    public static void main(String[] args) {
        log.info("ToolSet : Start");
        String sClass = System.getProperty(_CLASS);
        String sMethod = System.getProperty(_METHOD);
        String sParams = System.getProperty(_PARAMS);
        log.info("ToolSet.{} : {}, {} : {}, {} : {}", _CLASS, sClass, _METHOD, sMethod, _PARAMS, sParams);
        if(sClass.equals(PDFUtil_CLASS)){
            PDFUtil pdfUtil = new PDFUtil(sMethod, sParams);
        } else {
            log.warn("No such at class : {}", sClass);
            System.exit(1);
        }
        log.info("ToolSet : End");
    }

    public static boolean isInteger(String sNum){
        try {
            Integer.parseInt(sNum);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean isDouble(String sNum){
        try {
            Double.parseDouble(sNum);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}
