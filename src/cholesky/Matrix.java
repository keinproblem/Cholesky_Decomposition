package cholesky;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;

/**
 * Created by fan.noli.morina on 21.02.2017.
 */
public class Matrix {
  private int rows;
  private int cols;
  private final double[][] data; //rows -> cols

  public int hoehe(){
        return rows;
    }
  public int breite(){
        return cols;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(rows).append(' ').append(cols);
        for(int i = 0; i < rows;i++) {
            sb.append('\n');
            for (int j = 0; j < data[i].length-1; j++) {
                sb.append(getElement(i, j)).append(' ');
            }
            sb.append(getElement(i, data[i].length-1));
        }
        return sb.toString();
    }
  public Matrix(String dateiname) throws IOException{
    data = deserialize(new String(Files.readAllBytes(Paths.get(dateiname))));
    rows = data.length;
    cols = data[0].length;  //safe since deserialzed correctly
  }

  public Matrix(int rows, int cols) throws CholeskyException{
    if(rows<1 || cols<1)//|| rows != cols)
      throw new CholeskyException("Invalid Input rows: " + rows + " cols: " + cols);
      this.data = new double[rows][cols];
    this.rows = rows;
    this.cols = cols;

  }
  public void setElement(int zeile, int spalte, double wert){
    if(zeile>=rows || zeile<0 ||spalte>=cols|| spalte<0 )
      throw new CholeskyException("Element out of bounds!");
    data[zeile][spalte] = wert;
  };
  public double getElement(int zeile, int spalte){
    if(zeile>=rows || zeile<0 ||spalte>=cols|| spalte<0 )
      throw new CholeskyException("Element out of bounds.");
    return data[zeile][spalte];
  }
  public double[] zeile(int _zeile){
    if(_zeile < 0 || _zeile>=cols)
      throw new CholeskyException("Row out of bounds.");
    return data[_zeile];
  }
  public double[] spalte(int _spalte){
    if(_spalte <0 || _spalte>=cols)
      throw new CholeskyException("Row out of bounds.");
    double[] localCol = new double[cols];
    for(int i = 0; i< rows; i++)
      localCol[i] = data[i][_spalte];
    return localCol;
  }


  private double[][] deserialize(String input){
    String[] lines = input.split("\n");

    if(lines.length <1)
      throw new CholeskyException("Malformed input");

    int[] headerResult = decodeHeader(lines[0]);
    if(headerResult.length != 2)
      throw new RuntimeException("Error while decoding header");
    int rows = headerResult[0];
    int cols = headerResult[1];

    if(rows <1 || cols <1)
      throw new CholeskyException("Malformed header");
    if(lines.length-1 != rows)
      throw new CholeskyException("Inconsistent data rows");

    String[][] intermediate = new String[rows][];

    for( int i = 0; i < rows; i++)
        intermediate[i] = lines[i+1].split(" ");
    for(String[] line : intermediate)
      if(line.length!=cols) throw new CholeskyException("Incosistent data cols");

    double[][] deserialized = new double[rows][cols];
    try{
      for(int i = 0; i < rows; i++){
        for(int j = 0; j < cols; j++){
          deserialized[i][j] = Double.valueOf(intermediate[i][j]);
        }
      }
    } catch(NumberFormatException numberFormatException){
    throw new CholeskyException("Could not parse body values");
    }
    return deserialized;
  }

  private int[] decodeHeader(String headerLine){
    int[] header = new int[2];
    String[] head = headerLine.split(" ");

    if(head.length != 2)
      throw new CholeskyException("Malformed Inputheader");

    try {
      int rows = Integer.valueOf(head[0]);
      int cols = Integer.valueOf(head[1]);
      header[0] = rows;
      header[1] = cols;
    } catch(NumberFormatException numberFormatException){
      throw new CholeskyException("Could not parse header values");
    }
    return header;
  }

  public void toFile(String dateiname) throws Exception{
      Files.write(FileSystems.getDefault().getPath(".", dateiname), toString().getBytes());
  }

  public Matrix transponierte(){
    Matrix trans = new Matrix(cols, rows);
      for(int i = 0; i < rows; i++){
        for (int j = 0; j < data[i].length; j++) {
          trans.setElement(j,i,data[i][j]);
        }
      }
    return trans;
  }

  public boolean isSymetric(){
    if(!isQuadratic())
      return false;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < i; j++) {
        if (data[i][j] != data[j][i]) return false;
      }
    }
    return true;
  }

  private static final double FLP_EPSILON = 0.00000001;
  @Override
  public boolean equals(Object o) {
    if(o instanceof Matrix){
      Matrix m = (Matrix)o;
      if(m.hoehe() != this.hoehe() || m.breite() != this.breite())
        return false;
      for(int i = 0; i <m.hoehe();i++)
        for(int j = 0; j<m.breite();j++) {
          if (BigDecimal.valueOf(m.getElement(i, j)).subtract(BigDecimal.valueOf(this.getElement(i, j))).equals(BigDecimal.ZERO))
            return false;
        }
        return true;
    }
    return super.equals(o);
  }

  public boolean isQuadratic(){
    return rows == cols;
  }
}
