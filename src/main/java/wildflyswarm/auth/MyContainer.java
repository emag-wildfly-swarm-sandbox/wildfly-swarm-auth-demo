package wildflyswarm.auth;

import org.wildfly.swarm.config.security.Flag;
import org.wildfly.swarm.config.security.SecurityDomain;
import org.wildfly.swarm.config.security.security_domain.ClassicAuthentication;
import org.wildfly.swarm.config.security.security_domain.authentication.LoginModule;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.JPAFraction;
import org.wildfly.swarm.security.SecurityFraction;

import java.util.HashMap;

public class MyContainer {

  public static Container newContainer() throws Exception {
    Container container = new Container();

    container.fraction(new DatasourcesFraction()
        .jdbcDriver("h2", (d) -> {
          d.driverClassName("org.h2.Driver");
          d.xaDatasourceClass("org.h2.jdbcx.JdbcDataSource");
          d.driverModuleName("com.h2database.h2");
        })
        .dataSource("MyDS", (ds) -> {
          ds.driverName("h2");
          ds.connectionUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
          ds.userName("sa");
          ds.password("sa");
        })
    );

    container.fraction(new JPAFraction()
        .inhibitDefaultDatasource()
        .defaultDatasource("jboss/datasources/MyDS")
    );

    container.fraction(SecurityFraction.defaultSecurityFraction()
        .securityDomain(new SecurityDomain("dbdomain")
            .classicAuthentication(new ClassicAuthentication()
                .loginModule(new LoginModule("Database")
                    .code("Database")
                    .flag(Flag.REQUIRED).moduleOptions(new HashMap<Object, Object>() {{
                      put("dsJndiName", "java:jboss/datasources/MyDS");
                      put("principalsQuery", "SELECT password FROM employees WHERE name=?");
                      put("rolesQuery", "SELECT role, 'Roles' FROM employees WHERE name=?");
                    }})))));

    return container;
  }
}
