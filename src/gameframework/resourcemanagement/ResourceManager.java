package gameframework.resourcemanagement;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ResourceManager
{
    private static final String RESOURCE_FOLDER = "/resources/";
    private HashMap<String, Object> resourceMap;

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
            try {
                //load from JAR file
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




    public boolean resourceIsAlreadyLoaded(String name)
    {
        return resourceMap.get(name) != null;
    }
    public void freeResources() {resourceMap.clear();}


}
