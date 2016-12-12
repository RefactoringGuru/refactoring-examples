package refactoring_guru.patterns.factory_method.example.buttons;

/**
 * EN: Common interface for all buttons.
 *
 * RU: Общий интерфейс для всех продуктов.
 */
public interface Button {
    public void render();
    public void onClick();
}
