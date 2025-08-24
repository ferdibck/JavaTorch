public class Add extends Operation {
    public Add(Node A, Node B) {
        super();

        this.parents.add(A);
        this.parents.add(B);

        A.children.add(this);
        B.children.add(this);
    }

}
