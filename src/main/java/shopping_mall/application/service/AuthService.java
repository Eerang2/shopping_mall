package shopping_mall.application.service;

public interface AuthService<T> {

    boolean checkId(String id);

    void register(T user);

    String login(T user);
}
