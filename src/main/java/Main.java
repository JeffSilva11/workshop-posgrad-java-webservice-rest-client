import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation.Builder;
//import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class Main {
    public static void main(String[] args) {
//        Client client = ClientBuilder.newClient();        
//        WebTarget targetBase = client.target("http://localhost:8080/livraria-virtual");
//        WebTarget targetLivro = targetBase.path("livro");        
//        Builder request = targetLivro.request(MediaType.APPLICATION_XML);
//        Livros livros = request.get(Livros.class);
    	
    	SslConfigurator config = SslConfigurator
    	        .newInstance()
    	        .trustStoreFile("server.keystore")
    	        .trustStorePassword("livraria");

    	SSLContext context = config.createSSLContext();
   	
    	//Client client = ClientBuilder.newClient();
    	Client client = ClientBuilder.newBuilder()
    	        .sslContext(context)
    	        .build();
    	
    	HttpAuthenticationFeature auth = HttpAuthenticationFeature.basic("admin", "password");
    	client.register(auth);    	
    	
    	//Livros livros = ClientBuilder.newClient()
    	Livros livros = client
    	        //.target("http://localhost:8080/livraria-virtual")
    	        .target("https://localhost:8443/livraria-virtual")
    	        .path("livro")
    	        .request(MediaType.APPLICATION_XML)
    	        .get(Livros.class);
    	

        for (Livro livro : livros.getLivros()) {
            System.out.println(livro.getTitulo());
        }
    }
}