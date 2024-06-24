package org.example.goods.utils.files;

import org.example.goods.exceptions.files.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 * <p>
 * Утилитный класс для работы с файлами
 */
public class FilesUtil {
    private static final String filesPath = "src\\main\\resources\\files\\";

    public static void saveFile(String fileName, String path, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("File is empty");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = Paths.get(getPath(path, fileName) + ".jpg");
                File file1 = new File(filePath.toString());
                System.out.println(Files.exists(Path.of(filesPath + "icons")));
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //throw new FileStorageException("Could not save file " + fileName + " " + e.getLocalizedMessage());
        }
    }

    public static void delete(String path, String fileName) {
        try {
            Files.delete(Path.of(getPath(path, fileName)));
        } catch (FileNotFoundException e) {
            throw new FileStorageException("File " + fileName + " not found");
        } catch (IOException e) {
            throw new FileStorageException("Could not delete file " + fileName + " " + e.getLocalizedMessage());
        }
    }

    public static Resource getResource(String path, String fileName) {
        try {
            Path file = Path.of(getPath(path, fileName));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("File " + fileName + " not found");
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException(fileName);
        }
    }

    private static String getPath(String path, String fileName) {
        return filesPath + path + "/" + fileName;
    }
}
