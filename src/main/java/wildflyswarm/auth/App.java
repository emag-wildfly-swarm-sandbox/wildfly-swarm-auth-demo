package wildflyswarm.auth;

public class App {

  public static void main(String[] args) throws Exception {
    MyContainer.newContainer()
        .start()
        .deploy(MyDeployment.createDeployment());
  }

}
