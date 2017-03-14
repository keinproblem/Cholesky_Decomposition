package cholesky;

/**
 * Created by fan.noli.morina on 21.02.2017.
 */
public class Cholesky {
  public Cholesky(){
  }
  public Matrix createDecompositeMatrix(Matrix a){
    if(!a.isQuadratic())
      throw new CholeskyException("Matrix must be Quadratic");
    if(!a.isSymetric())
      throw new CholeskyException("Matrix must be Symetric");

    Matrix result = new Matrix(a.hoehe(),a.breite());

    for (int i = 0; i < a.hoehe(); i++)  {
      for (int j = 0; j <= i; j++) {
        double sum = 0.0;
        for (int k = 0; k < j; k++) {
          sum += result.getElement(i,k) * result.getElement(j,k);
        }
        if (i == j) result.setElement(i,i,Math.sqrt(a.getElement(i,i)- sum));
        else        result.setElement(i,j,(1.0 / result.getElement(j,j) * (a.getElement(i,j) - sum)));
      }
      if (result.getElement(i,i) <= 0 || Double.isNaN(result.getElement(i,i))) {
        throw new CholeskyException("Matrix not positive definite");
      }
    }

    return result;
  }


  public Matrix loese(Matrix a, Matrix b){  //TODO; SOLVE
    if(b.hoehe() != a.breite())
      throw new CholeskyException("Incompatible dim");

    Matrix decomp = createDecompositeMatrix(a);
    Matrix decompTrans = decomp.transponierte();

    Matrix intermediate = new Matrix(a.breite(),1);
    Matrix result = new Matrix(a.breite(),1);
    int i,k,n;
    double sum;
    n = a.breite();
    for (i=0;i<n;i++) {
      for (sum=b.getElement(i,0),k=i-1;k>=0;k--)
        sum -= decomp.getElement(i,k)*intermediate.getElement(k,0);;
      intermediate.setElement(i,0, sum/decomp.getElement(i,i));
    }

    for (i=n-1;i>=0;i--) {
      for (sum=intermediate.getElement(i,0),k=i+1;k<n;k++)
        sum -= decompTrans.getElement(i,k)*result.getElement(k,0);;
      result.setElement(i,0, sum/decompTrans.getElement(i,i));
    }


    return result;
  }
}
