package shopping_mall.application.auth.service;

public interface AuthService<T> {

    boolean checkId(String id);

    void register(T user);

    String login(T user);
}
