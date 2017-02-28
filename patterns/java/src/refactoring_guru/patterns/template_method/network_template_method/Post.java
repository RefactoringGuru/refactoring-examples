package refactoring_guru.patterns.template_method.network_template_method;

public class Post {
    private String post;

    public Post(String message) {
        post = message;
    }

    public byte[] getPost() {
        return post.getBytes();
    }
}
