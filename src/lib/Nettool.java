package lib;

import java.io.IOException;
import java.io.OutputStream;

public class Nettool {
    public static void send(OutputStream out,byte[] b)throws IOException
    {
        try{
            out.write(b);
        }
        catch (IOException e0)
        {
            e0.printStackTrace();
            try {
                out.write(b);
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
                out.write(b);
            }
        }
    }
}
