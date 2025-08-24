
public class Matrix {
    int dim0;
    int dim1;
    int size;
    double[] data;

    Matrix(int x, int y) {
        dim0 = x;
        dim1 = y;
        size = x*y;
        data = new double[size];

        for (int i= 0; i<size; i++)
            data[i] = Math.random();
    }

    int flattenIndex(int[] indices) {
        return indices[0]*dim1 + indices[1];
    }

    int[] unravelIndex(int idx) {
        int y = idx % dim1;
        idx -= y;

        int x = idx/dim1;

        return new int[] {x, y};
    }

    void print() {
        System.out.println("("+dim0+" x "+dim1+")-Matrix :");
        
        for (int i = 0; i < dim0; i++) {                    
            for (int j = 0; j < dim1; j++) {
                int flattened = flattenIndex(new int[] {i, j});

                System.out.print(data[flattened] + " ");
            }
            System.out.println();
        }

        System.out.println();
    }

    double getEntry(int x, int y) {
        return data[flattenIndex(new int[] {x, y})];
    }

    void setEntry(int x, int y, double e) {
        data[flattenIndex(new int[] {x, y})] = e;
    }

    int[] getShape() {
        return new int[] {dim0, dim1};
    }

    int getSize() {
        return size;
    }

    double getEntryBroadcasted(int x, int y) {
        int xb;
        int yb;

        if (x >= dim0) xb = 0;
        else xb = x;

        if (y >= dim1) yb = 0;
        else yb = y;

        return getEntry(xb, yb);
    }

    static Boolean broadcastable(Matrix A, Matrix B) {
        int[] shapeA = A.getShape();
        int[] shapeB = B.getShape();

        for(int i = 0; i < 2; i++) {
            if(shapeA[i] != shapeB[i] && shapeA[i] != 1 && shapeB[i] != 1)
                throw new Error("Tensor shapes are not broadcastable.");
        }

        return true;
    
    }

    static Matrix matmul(Matrix A, Matrix B) {
        if(A.getShape()[1] != B.getShape()[0])
            throw new Error("Inner shapes must match.");
        
        else {
            Matrix res = new Matrix(A.getShape()[0], B.getShape()[1]);
            
            for(int i = 0; i < A.getShape()[0]; i++) {
                for(int j = 0; j < B.getShape()[1]; j++) {
                    double entry = 0;

                    for(int k = 0; k < A.getShape()[1]; k++) {
                        entry += A.getEntry(i, k) * B.getEntry(k, j);
                    }
                    
                    res.setEntry(i, j, entry);
                }
            }

            return res;

        }
    }

    static Matrix add(Matrix A, Matrix B) {
        if(broadcastable(A, B)) {
            int dim0 = Math.max(A.getShape()[0], B.getShape()[0]);
            int dim1 = Math.max(A.getShape()[1], B.getShape()[1]);

            Matrix res = new Matrix(dim0, dim1);

            for(int i = 0; i < dim0; i++) {
                for(int j = 0; j < dim1; j++) {
                    res.setEntry(i, j, A.getEntryBroadcasted(i, j) + B.getEntryBroadcasted(i, j));
                }
            }

            return res;
        }

        else {
            throw new Error("Tensor shapes are not broadcastable.");

        }
    }
}