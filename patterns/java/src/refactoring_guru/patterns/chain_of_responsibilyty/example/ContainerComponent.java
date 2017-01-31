package refactoring_guru.patterns.chain_of_responsibilyty.example;

import java.util.ArrayList;
import java.util.List;

public class ContainerComponent extends Component {
    List<Component> children = new ArrayList<>();

    public void add(Component child) {
        children.add(child);
    }

    public void click(int x, int y) {
        if (onClick != null) {
            onClick.doJob();
        }
    }

    @Override
    public void render() {}
}
