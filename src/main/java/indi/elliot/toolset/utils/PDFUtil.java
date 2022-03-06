package indi.elliot.toolset.utils;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.lang.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static indi.elliot.toolset.constants.ToolSetConstant.log;

public class PDFUtil {
    final String TAG_DECRYPT = ".decrypt.";

    public boolean decrypt(@NonNull String filePath, @NonNull String password){
        log.info("PDFUtil.decrypt : Start");
        log.info("PDFUtil.decrypt.filePath : {}", filePath);
        PDDocument pdDocument = null;
        try {
            log.info("test : {} ", Paths.get(filePath).getFileName());
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
