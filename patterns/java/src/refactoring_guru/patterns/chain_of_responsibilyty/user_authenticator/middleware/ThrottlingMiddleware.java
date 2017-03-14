package refactoring_guru.patterns.chain_of_responsibilyty.user_authenticator.middleware;

/**
 * RU: Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class ThrottlingMiddleware extends Middleware {
    private int requestPerMinute;
    private int request;
    private long currentTime;

    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * RU: КЛЮЧЕВОЙ МОМЕНТ: вызов  checkNext() можно вставить как в начале этого метода,
     * так и в середине или в конце. Это даёт еще один уровень гибкости по сравнению
     * с проверками по списку. Например, элемент цепи может пропустить все остальные
     * проверки вперёд и проверять себя в конце.
     */
    @SuppressWarnings("deprecation")
    public boolean check(String email, String password) {
        if (System.currentTimeMillis() > currentTime + 60_000) {
            request = 0;
            currentTime = System.currentTimeMillis();
        }
        request++;
        if (request > requestPerMinute) {
            System.out.println("Request limit exceeded!");
            Thread.currentThread().stop();
        }
        return checkNext(email, password);
    }
}
