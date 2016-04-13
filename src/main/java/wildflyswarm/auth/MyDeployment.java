package wildflyswarm.auth;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.jaxrs.JAXRSArchive;
import org.wildfly.swarm.undertow.descriptors.WebXmlAsset;

public class MyDeployment {

  public static JAXRSArchive createDeployment() throws Exception {
    JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);

    deployment.addPackage(App.class.getPackage());
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("META-INF/persistence.xml", App.class.getClassLoader()), "classes/META-INF/persistence.xml");
    deployment.addAsWebInfResource(
        new ClassLoaderAsset("META-INF/load.sql", App.class.getClassLoader()), "classes/META-INF/load.sql");

    WebXmlAsset webXmlAsset = deployment.findWebXmlAsset();
    webXmlAsset.setLoginConfig("BASIC", "my-realm");
    webXmlAsset.protect("/*")
      .withRole("admin")
      .withMethod("GET");

    deployment.setSecurityDomain("dbdomain");

      return deployment;
  }
}
