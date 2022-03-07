package indi.elliot.toolset.utils;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static indi.elliot.toolset.constants.ToolSetConstant.STRING_SPLIT;
import static indi.elliot.toolset.constants.ToolSetConstant.log;

public class PDFUtil {
    final String TAG_DECRYPT = ".decrypt.";

    public PDFUtil (String method, String params){
        if(method.equals("decrypt")){
            decrypt(params.split(STRING_SPLIT));
        } else {
            log.warn("No such at method : {}", method);
            System.exit(1);
        }
    }

    public boolean decrypt(String ...params){
        String filePath = null, password = null;
        if(params.length == 2) {
            filePath = params[0].trim();
            password = params[1].trim();
        } else {
            log.error("PDFUtil.decrypt.error : Missing input parameter, the input params should be : decrypt(String filePath, String password)");
            return false;
        }

        log.info("PDFUtil.decrypt : Start");
        log.info("PDFUtil.decrypt.filePath : {}", filePath);
        PDDocument pdDocument = null;
        try {
            File sourceFile = Paths.get(filePath).toFile();
            File decryptFile = Paths.get(Paths.get(filePath).getParent() + "\\" + FileNameUtils.getBaseName(filePath) + TAG_DECRYPT + FileNameUtils.getExtension(filePath)).toFile();
            pdDocument = PDDocument.load(sourceFile, password);
            pdDocument.setAllSecurityToBeRemoved(true);
            pdDocument.save(decryptFile);
            log.info("PDFUtil.decrypt : Completed");
            return true;
        } catch (IOException e) {
            log.error("PDFUtil.decrypt.error : " + e.getMessage());
        } finally {
            if(pdDocument != null){
                try {
                    pdDocument.close();
                } catch (IOException e) {
                    log.error("PDFUtil.decrypt.error : " + e.getMessage());
                }
            }
            log.info("PDFUtil.decrypt : End");
        }
        return false;
    }
}
