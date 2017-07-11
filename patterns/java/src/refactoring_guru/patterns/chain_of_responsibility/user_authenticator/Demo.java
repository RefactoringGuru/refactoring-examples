package refactoring_guru.patterns.chain_of_responsibility.user_authenticator;

import refactoring_guru.patterns.chain_of_responsibility.user_authenticator.middleware.*;
import refactoring_guru.patterns.chain_of_responsibility.user_authenticator.server.Server;

import java.io.*;

/**
 * EN: Demo class. Everything comes together here.
 * 
 * RU: Демо-класс. Здесь всё сводится воедино.
 */
public class Demo {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Server server;

    private static void init() {
        server = new Server();
        server.register("admin@example.com", "admin_pass");
        server.register("user@example.com", "user_pass");

        // EN: All checks are linked. Client can build various chains using the
        // same components.
        // 
        // RU: Проверки связаны в одну цепь. Клиент может строить различные
        // цепи, используя одни и те же компоненты.
        Middleware middleware = new ThrottlingMiddleware(2);
        middleware.linkWith(new UserExistsMiddleware())
                .linkWith(new RoleCheckMiddleware());

        // EN: Server gets a chain from client code.
        // 
        // RU: Сервер получает цепочку от клиентского кода.
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
