package refactoring_guru.patterns.template_method.network_template_method;

/**
 * EN: Base class of social network.
 *
 * RU: Базовый класс социальной сети.
 */
public abstract class Network {

    /**
     * EN: Publish the data to whatever network.
     *
     * RU: Публикация данных в любой сети.
     */
    public boolean post(Post post) {
        // EN: Authenticate before posting. Every network uses a different
        // authentication method.
        // RU: Проверка данных пользлвателя перед постом в соцсеть.
        // каждая сеть для проверки использует разные методы.
        if (authenticate()) {
            // EN: Send the post data.
            // RU: Отправка данных.
            return sendData(post.getPost());
        }
        return false;
    }

    abstract boolean authenticate();
    abstract boolean sendData(byte[] data);
}