package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.files.FileHandleStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jcs
 */
public class JarFileHandleStream extends FileHandleStream {

    private JarFile jarFile = null;
    private String jarRelResDir;
    private String[] pathString;

    public JarFileHandleStream(String path) {
        super(path);
        try {
            String[] args = path.split("!");
            pathString = args[0].split("/application");
            jarRelResDir = args[1].substring(1);

            String jarFilePath = args[0];
            String test = pathString[0] + pathString[1];
            jarFile = new JarFile(test);
            System.out.println(test); 
        } catch (IOException ex) {
            Logger.getLogger(JarFileHandleStream.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public InputStream read() {

        InputStream is = null;
        try {
            is = jarFile.getInputStream(jarFile.getEntry(jarRelResDir));
        } catch (IOException ex) {
            Logger.getLogger(JarFileHandleStream.class.getName()).log(Level.SEVERE, null, ex);
        }

        return is;
    }

    @Override
    public OutputStream write(boolean overwrite) {
        return super.write(overwrite);
    }
}
