package moviesAPI.moviesAPI.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moviesAPI.moviesAPI.Model.SerieData;

public class ConvertData implements IConverteData {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> object) {
        try {
            return mapper.readValue(json, object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
