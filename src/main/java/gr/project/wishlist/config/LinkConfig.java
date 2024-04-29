package gr.project.wishlist.config;


import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@ConfigurationProperties("link")
public class LinkConfig {
    public final String prefix;
    public final String postfix;
    public final String protocol;
    public final String host;
    public final Integer port;
}

//@AllArgsConstructor
//public class LinkConfig {
//    public final String prefix = "/wishlist/";
//    public final String postfix = "/gifts";
//    public final String protocol = "http";
//    public final String host = "localhost";
//    public final Integer port = 8080;
//}

