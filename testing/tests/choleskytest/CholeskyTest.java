package choleskytest;

import cholesky.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by fan.noli.morina on 22.02.2017.
 */
public class CholeskyTest {
  static final String PATH = "files";
  @Before
  public void setUp() throws Exception {


  }

  @After
  public void tearDown() throws Exception {

  }

  private boolean matrixEquals(Matrix a, Matrix m){
      if(m.hoehe() != a.hoehe() || m.breite() != a.breite())
        return false;
      for(int i = 0; i < m.hoehe();i++)
        for(int j = 0; j< m.breite();j++) {
          if (BigDecimal.valueOf(m.getElement(i, j)).subtract(BigDecimal.valueOf(a.getElement(i, j))).equals(BigDecimal.ZERO))
            return false;
        }
      return true;
  }

  @Test
  public void testSerialization() throws Exception{
    String file = PATH + "/"+1+"/A";
    Matrix m = new Matrix(file);
    Assert.assertTrue( Arrays.equals( Files.readAllBytes(Paths.get(file))  , m.toString().getBytes()));
  }

  @Test
  public void testDeserialization() throws Exception{
    Matrix m = new Matrix(4,4);
    m.setElement(0,0, 4);
    m.setElement(0,1, 0);
    m.setElement(0,2, -2);
    m.setElement(0,3, -2);

    m.setElement(1,0, 0);
    m.setElement(1,1, 1);
    m.setElement(1,2, 3);
    m.setElement(1,3, 0);

    m.setElement(2,0, -2);
    m.setElement(2,1,3 );
    m.setElement(2,2, 11);
    m.setElement(2,3, 3);

    m.setElement(3,0, -2);
    m.setElement(3,1, 0);
    m.setElement(3,2, 3);
    m.setElement(3,3, 14);
    Matrix x = new Matrix(PATH + "/"+1+"/A");
    Assert.assertTrue(matrixEquals(m,x));
  }

  @Test
  public void malformedInputHeaderSize() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "invalidHeaderSize");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }

  }

  @Test
  public void malformedInputHeaderFormat() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "invalidHeaderFormat");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }
  }

  @Test
  public void malformedInputHeaderValues() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "invalidHeaderValues");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }
  }

  @Test
  public void missingHeader() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "noHeader");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }
  }

  @Test
  public void inconsistentMatrix() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "inconsistentHeaderOk");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }
  }

  @Test
  public void inconsistentMatrixBody() throws Exception{
    try {
      Matrix m = new Matrix(PATH + "/error/" + "inconsistentBody");
    } catch (Exception e){
      e.printStackTrace();
      Assert.assertTrue(e instanceof CholeskyException);
    }
  }

  @Test
  public void invalidSetElement() throws Exception{
    Matrix m = new Matrix(2,2);
    try {
      m.setElement(-2, -2, 1);
    }catch (Exception e){
      Assert.assertTrue( e instanceof CholeskyException);
    }
    try {
      m.setElement(3, 3, 1);
    }catch (Exception e){
      Assert.assertTrue( e instanceof CholeskyException);
    }
  }

  @Test
  public void invalidGetElement() throws Exception{
    Matrix m = new Matrix(2,2);
    m.setElement(0,0,1);
    m.setElement(0,0,2);
    m.setElement(1,0,3);
    m.setElement(1,0,4);

    try {
      m.setElement(-2, -2, 1);
    }catch (Exception e){
      Assert.assertTrue( e instanceof CholeskyException);
    }
    try {
      m.setElement(3, 3, 1);
    }catch (Exception e){
      Assert.assertTrue( e instanceof CholeskyException);
    }


  }


  @Test
  public void testAll() throws Exception {
    Cholesky c = new Cholesky();
    Matrix A1, b1,x1;

    /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+1+"/A");
    b1 = new Matrix(PATH + "/"+1+"/b");
    x1 = new Matrix(PATH + "/"+1+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (Exception e){
      e.printStackTrace();
      Assert.fail();
    }
        /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+2+"/A");
    b1 = new Matrix(PATH + "/"+2+"/b");
    x1 = new Matrix(PATH + "/"+2+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (Exception e){
      e.printStackTrace();
      Assert.fail();
    }
        /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+3+"/A");
    b1 = new Matrix(PATH + "/"+3+"/b");
    x1 = new Matrix(PATH + "/"+3+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (Exception e){
      e.printStackTrace();
      Assert.fail();
    }
        /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+4+"/A");
    b1 = new Matrix(PATH + "/"+4+"/b");
    x1 = new Matrix(PATH + "/"+4+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (CholeskyException e){
      Assert.assertTrue(e instanceof CholeskyException);
      e.printStackTrace();
    }
        /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+5+"/A");
    b1 = new Matrix(PATH + "/"+5+"/b");
    x1 = new Matrix(PATH + "/"+5+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (CholeskyException e){
      Assert.assertTrue(e instanceof CholeskyException);
      e.printStackTrace();
    }
        /* Test Operation1 */
    A1 = new Matrix(PATH + "/"+6+"/A");
    b1 = new Matrix(PATH + "/"+6+"/b");
    x1 = new Matrix(PATH + "/"+6+"/x");
    try{
      Matrix res = c.loese(A1, b1);
      Assert.assertTrue(matrixEquals(res, x1));
    } catch (Exception e){
      e.printStackTrace();
      Assert.fail();
    }


  }

}