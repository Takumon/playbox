package sample.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.net.URL;
import java.util.Date;

/**
 * Says "Hi" to the user.
 */
@Mojo( name = "sayhi")
public class GreetingMojo extends AbstractMojo{
    public enum Color {
        GREEN, RED, BLUE
    }

    @Parameter(property = "sayhi.greeting", defaultValue="Hello World")
    private String greeting;

    @Parameter(property = "sayhi.myBoolean")
    private boolean myBoolean;

    @Parameter(property = "sayhi.myInteger", defaultValue = "0")
    private Integer myInteger;


    @Parameter(property = "sayhi.myDate", defaultValue = "2005-10-06 2：22：55.1 PM")
    private Date myDate;

    @Parameter(property= "sayhi.myUrl", defaultValue = "")
    private URL myURL;

    @Parameter(property = "sayhi.myColor", defaultValue = "RED")
    private Color myColor;

    public void execute() throws MojoExecutionException {
        getLog().info(myInteger.toString() + myBoolean + greeting + "@" + myDate + myURL + myColor);
    }
}
