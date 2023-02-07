package ke.natujenge.whatsappbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ke.natujenge.whatsappbot.config.ApplicationProperties;
import ke.natujenge.whatsappbot.dto.ProductDTO;
import ke.natujenge.whatsappbot.dto.ProductItem;
import ke.natujenge.whatsappbot.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CatalogService {

    private final ApplicationProperties applicationProperties;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CatalogService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    public List<ProductItem> getCatalog() {
        // make an api call

        // request
        /**
         * {
         *   "query": "query ListProducts{ listProducts { items { id product_name status }}}"
         * }
         */

        String query = "query ListProducts{ listProducts { items { id product_name status }}}";
        log.debug("Query : {}", query);

        Map<String, String> request = new HashMap<>();
        request.put("query", query);
        log.debug("Json query : {}", query);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer " + applicationProperties.getOauthToken());

        try {
            String jsonRequest = objectMapper.writeValueAsString(request);
            String response = HttpUtil.post(applicationProperties.getJumbaProductsUrl(), jsonRequest, headerMap,  MediaType.get("application/json; charset=utf-8"));
            ProductDTO productDTO = objectMapper.readValue(response, ProductDTO.class);
            return productDTO.getData().getListProducts().getItems();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public String queryProduct(String id) {
        return "DummyCement";
    }
}
