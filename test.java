class Test {

    public static void main(String[] args) {
        Matrix m = new Matrix(3, 1);
        Matrix n = new Matrix(1, 3);
        m.print();
        n.print();

        Matrix l = Matrix.matmul(m, n);
        l.print();
    }

}