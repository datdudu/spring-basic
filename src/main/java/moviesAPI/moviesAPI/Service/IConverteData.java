package moviesAPI.moviesAPI.Service;

public interface IConverteData {
    <T> T getData(String json, Class<T> object);
}
