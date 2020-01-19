package zpo.project.fuelscanner.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
public class FileService {

    public File copyFile(InputStream input) {
        File file = new File("E:\\Semestr V\\Zaawansowane programowanie obiektowe\\fuel-scanner\\files\\"+ UUID.randomUUID()+".jpg");
        try(OutputStream outputStream = new FileOutputStream(file)){
            IOUtils.copy(input, outputStream);
        } catch (FileNotFoundException e) {
            // handle exception here
        } catch (IOException e) {
            // handle exception here
        }

        return file;
    }

}
