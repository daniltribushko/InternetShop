package org.example.goods.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Tribusko Danil
 * @since 03.06.2024
 *
 * Класс http ответа
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HttpResponse {
    private int statusCode;
    private String response;
}
