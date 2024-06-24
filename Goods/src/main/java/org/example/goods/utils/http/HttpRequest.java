package org.example.goods.utils.http;

import org.example.goods.exceptions.AppException;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

/**
 * @author Tribushko Danil
 * @since 04.06.2024
 * <p>
 * Класс для работы с http запросами
 */
public class HttpRequest {
    private HttpRequest() {
    }

    public static HttpRequestBuilder createRequest(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url)
                    .openConnection();
            return new HttpRequestBuilder(connection);
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }

    public static class HttpRequestBuilder {
        private final HttpURLConnection connection;

        public HttpRequestBuilder(HttpURLConnection connection) {
            this.connection = connection;
            connection.setDoOutput(true);
        }

        public HttpRequestBuilder setMethod(String method) throws ProtocolException {
            connection.setRequestMethod(method);
            return this;
        }

        public HttpRequestBuilder setBaseAuth(String username, String password) {
            String auth = username + ":" + password;
            String bytes = Base64.getEncoder().encodeToString(auth.getBytes());
            connection.setRequestProperty("Authorization", "Basic " + bytes);
            return this;
        }

        public HttpRequestBuilder setBearerAuth(String token) {
            connection.setRequestProperty("Authorization", "Bearer " + token);
            return this;
        }

        public HttpRequestBuilder setHeader(String key, String value) {
            connection.setRequestProperty(key, value);
            return this;
        }

        public HttpRequestBuilder addBody(String body) {
            try (BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(connection.getOutputStream()))) {
                writer.write(body);
                return this;
            } catch (IOException e) {
                throw new AppException(e.getLocalizedMessage());
            }
        }

        public HttpResponse sendRequest() {
            StringBuilder response = new StringBuilder();
            String inputLine;
            int statusCode = HttpStatus.BAD_REQUEST.value();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                while ((inputLine = reader.readLine()) != null) {
                    statusCode = connection.getResponseCode();
                    response.append(inputLine);
                }
            } catch (IOException e) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    statusCode = connection.getResponseCode();
                    while ((inputLine = reader.readLine()) != null) {
                        response.append(inputLine);
                    }
                } catch (IOException ex) {
                    throw new AppException(ex.getLocalizedMessage());
                }
            }
            return new HttpResponse(statusCode, response.toString());
        }
    }

}
