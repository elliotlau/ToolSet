import indi.elliot.toolset.utils.PDFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static indi.elliot.toolset.constants.ToolSetConstant.*;

public class ToolSet {
    @Autowired
    PDFUtil pdfUtil;

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(LOGGER_NAME);
        log.info("ToolSet : Start");
        String sClass = System.getProperty(_CLASS);
        String sMethod = System.getProperty(_METHOD);
        String sParams = System.getProperty(_PARAMS);
        log.info("ToolSet.{} : {}, {} : {}, {} : {}", _CLASS, sClass, _METHOD, sMethod, _PARAMS, sParams);
        if(Arrays.stream(LIST_CLASS_NAME).noneMatch(className -> className.equals(sClass))){
            log.warn("No such at class : {}", sClass);
            System.exit(1);
        }
        try {
            Class<?> _class = Class.forName(sClass);
            Object _instance = _class.getDeclaredConstructor().newInstance();
            Method [] methods = _class.getDeclaredMethods();
            if(Arrays.stream(methods).noneMatch(method -> method.getName().equals(sMethod))){
                log.warn("No such at method {} ", sMethod);
                System.exit(1);
            }
            Object [] sysParams = sParams.isEmpty() ? null : Arrays.stream(sParams.split(STRING_SPLIT)).toArray(String[]::new);
            List<Class<?>> params = new ArrayList<>();
            if(sysParams != null) {
                for (Object param : sysParams) {
                    if(isInteger(param.toString())){
                        params.add(Integer.TYPE);
                    } else if(isDouble(param.toString())){
                        params.add(Double.TYPE);
                    } else {
                        params.add(String.class);
                    }
                }
            }
            Method _method = _class.getDeclaredMethod(sMethod, params.toArray(new Class<?>[0]));
            _method.invoke(_instance, sysParams);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            log.error("ToolSet.error : " + e);
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
