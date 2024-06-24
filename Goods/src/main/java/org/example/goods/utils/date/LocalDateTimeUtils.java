package org.example.goods.utils.date;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Tribushko Danil
 * @since 07.06.2024
 * <p>
 * Утилитный класс для работы с датами в проекте
 */
public class LocalDateTimeUtils {
    public static boolean checkDate(LocalDateTime creationDateEntity,
                                    LocalDateTime updateDateEntity,
                                    LocalDateTime creationDate,
                                    LocalDateTime updateDate,
                                    LocalDateTime minCreationDate,
                                    LocalDateTime maxCreationDate,
                                    LocalDateTime minUpdateDate,
                                    LocalDateTime maxUpdateDate) {
        return (creationDate == null || Objects.equals(creationDateEntity, creationDate) &&
                updateDate == null || Objects.equals(updateDateEntity, updateDate) &&
                (minCreationDate == null || creationDateEntity.isAfter(minCreationDate)) &&
                (maxCreationDate == null || creationDateEntity.isBefore(maxCreationDate)) &&
                (minUpdateDate == null || updateDateEntity.isAfter(minUpdateDate)) &&
                (maxUpdateDate == null || updateDateEntity.isBefore(maxUpdateDate)));
    }
}
