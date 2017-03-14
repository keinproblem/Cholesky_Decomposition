package cholesky;

/**
 * Created by fan.noli.morina on 21.02.2017.
 */
public class CholeskyException extends RuntimeException {
  public CholeskyException() {
    super();
  }

  public CholeskyException(String s) {
    super(s);
  }

  public CholeskyException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public CholeskyException(Throwable throwable) {
    super(throwable);
  }
}
