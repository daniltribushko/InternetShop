package org.example.goods.exceptions.files;

import org.example.goods.exceptions.GlobalAppException;

/**
 * @author Tribushko Danil
 * @since 24.06.2024
 */
public class FileStorageException extends GlobalAppException {
    public FileStorageException(String message) {
        super(400, message);
    }
}
