class Test {

    public static void main(String[] args) {
        Matrix m = new Matrix(1, 2);
        Matrix n = new Matrix(2, 1);
        m.print();
        n.print();

        Matrix l = Matrix.matmul(m, n);
        l.print();
    }

}