import java.util.ArrayList;
import java.util.List;

public class Node {
    List<Node> parents;
    List<Node> children;

    Node() {
        this.parents = new ArrayList<>();
        this.children = new ArrayList<>();
    }

}
