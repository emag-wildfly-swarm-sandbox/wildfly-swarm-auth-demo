package wildflyswarm.auth;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class MyDeployment {

  public static JAXRSArchive createDeployment() throws Exception {
    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);

    deployment.addPackage(App.class.getPackage());
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("META-INF/persistence.xml", App.class.getClassLoader()), "classes/META-INF/persistence.xml");
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("META-INF/load.sql", App.class.getClassLoader()), "classes/META-INF/load.sql");
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("WEB-INF/web.xml", App.class.getClassLoader()), "web.xml");
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("WEB-INF/jboss-web.xml", App.class.getClassLoader()), "jboss-web.xml");

    return deployment;
  }
}
