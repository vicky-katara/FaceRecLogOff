import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import java.io.File;
public class Hello
{
   public static void main( String[] args )
   {
      
      //System.out.println(new File("\"C:\\Users\\Vicky Katara\\Documents\\NetBeansProjects\\FaceRecLogOff\\haarcascade_frontalface_alt.xml").getPath());
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
      System.out.println( "mat = " + mat.dump() );
   }
}