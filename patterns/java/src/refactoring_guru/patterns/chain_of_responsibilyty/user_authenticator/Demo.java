package refactoring_guru.patterns.chain_of_responsibilyty.user_authenticator;

import java.io.*;

public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // RU: Проверки должны быть связаны в цепь в конечном итоге.
        Middleware middleware = new ThrottlingMiddleware(2)
                .linkWith(new UserExistsMiddleware())
                .linkWith(new RoleCheckMiddleware());

        // RU: Сервер получает цепочку от клиента.
        server.setMiddleware(middleware);
    }

    public static void main(String[] args) throws IOException {
        init();

        boolean success;
        do {
            System.out.print("Enter email: ");
            String email = reader.readLine();
            System.out.print("Input password: ");
            String password = reader.readLine();
            success = server.logIn(email, password);
        } while (!success);
    }
}
