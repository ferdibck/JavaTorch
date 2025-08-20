

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

    void setEntryBroadcasted(int x, int y, double e) {
        data[flattenIndexBroadcasted(x, y)] = e;

        
        }
    }

    /* 
    static Matrix[] broadcast(Matrix A, Matrix B) {
        int[] shapeA = A.getShape();
        int[] shapeB = B.getShape();

        int dim0_broadcasted = Math.max(shapeA[0], shapeB[0]);
        int dim1_broadcasted = Math.max(shapeA[1], shapeB[1]);

        Matrix A_broadcasted = new Matrix(dim0_broadcasted, dim1_broadcasted);
        Matrix B_broadcasted = new Matrix(dim0_broadcasted, dim1_broadcasted);

        for(int i = 0; i < dim0_broadcasted; i++) {

            for(int j = 0; j < dim1_broadcasted; j++) {
                A_broadcasted.setEntryBroadcasted(i, j, A.getEntry(i, j));
                B_broadcasted.setEntryBroadcasted(i, j, B.getEntry(i, j));
            }
    }
    */

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
}
