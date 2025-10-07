package gameframework.resourcemanagement;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/* This is a class that we use to easily load images, audio clips and other resources embedded in
 * the executable (jar file). It also does resource management by making sure we never load the same
 * resource twice from secondary storage.
 */
public class ResourceManager
{
    private static final String RESOURCE_FOLDER = "/resources/";
    private final HashMap<String, Object> resourceMap;

    public ResourceManager()
    {
        resourceMap = new HashMap<String,Object>();
    }

    public interface ResourceProcessor
    {
        Object process(InputStream resourceStream) throws Exception;
    }

    public Object loadGeneralResource(String name, ResourceProcessor resourceProcessor)
    {
        String resourcePath = RESOURCE_FOLDER + name;
        Object resource = resourceMap.get(name);

        if (resource == null)
        {
            /* if the resource isn't registered already then load it from the jar file and store
             * it in the resource map so that it can be retrieved easily using the resource name */
            try
            {
                resource = resourceProcessor.process(getClass().getResourceAsStream(resourcePath));
                resourceMap.put(name, resource);
            }
            catch (Exception e)
            {
                System.out.println("Unable to load resource: " +
                        name);
            }
        }
        return resource;
    }

    public BufferedImage loadImageResource(String name)
    {
        return (BufferedImage)loadGeneralResource(name, new ResourceProcessor() {
            @Override
            public Object process(InputStream resourceStream) throws Exception
            {
                return ImageIO.read(resourceStream);
            }
        });

    }

    public ArrayList<String> loadTextResource(String name)
    {
        return (ArrayList<String>)loadGeneralResource(name, new ResourceProcessor() {
            @Override
            public Object process(InputStream resourceStream) throws Exception
            {
                ArrayList<String> text = new ArrayList<String>();
                Scanner fileScanner = new Scanner(resourceStream);

                while (fileScanner.hasNextLine())
                    text.add(fileScanner.nextLine());

                return text;
            }
        });

    }

    /* Returns true if a resource has already been loaded from file and
     * is currently stored in RAM (in the resource map) for faster access. */
    public boolean resourceIsAlreadyLoaded(String name)
    {
        return resourceMap.get(name) != null;
    }

    public void freeResources() {resourceMap.clear();}

}
