package refactoring_guru.patterns.template_method.network_template_method;

public class Demo {
    public static void main(String[] args) {
        // EN: Build the message.
        // RU: Вводим сообщение.
        String message = "I like the new article about design patterns on the 'refactoring.guru/ru/design-patterns'!";
        Post post = new Post(message);
        // EN: Instantiate the network objects and publish.
        // RU: Создаем сетевые объекты и публикуем пост.
        Network facebook = new Facebook();
        Network twitter = new Twitter();
        facebook.post(post);
        twitter.post(post);
    }
}
