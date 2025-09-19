package gameframework.resourcemanagement;
import java.util.HashMap;

public class ResourceManager
{
    private static final String RESOURCE_FOLDER = "/resources/";
    private HashMap<String, Object> resourceMap;

    public ResourceManager()
    {
        resourceMap = new HashMap<String,Object>();
    }

    public Object loadGeneralResource(String name)
    {
        String resourcePath = RESOURCE_FOLDER + name;
        Object resource = resourceMap.get(name);

        if (resource == null)
        {
            try {
                //load from JAR file
                resource = getClass().getResourceAsStream(resourcePath);
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


    public boolean resourceIsAlreadyLoaded(String name)
    {
        return resourceMap.get(name) != null;
    }
    public void freeResources() {resourceMap.clear();}


}
