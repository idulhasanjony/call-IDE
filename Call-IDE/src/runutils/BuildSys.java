package runutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Configures build files, make executions, logs the compiler outputs.
 * @author Abdullah Talayhan
 */
public class BuildSys {
    
    public static void setPropsForCompile(String init ,String build, String src) {
        try {
            //String filepath = "/Users/ATTJ/Desktop/xmlPlay/build.xml";
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            //Node project = doc.getFirstChild();
            Node prop1 = doc.getElementsByTagName("property").item(0);
            Node prop2 = doc.getElementsByTagName("property").item(1);
            
            
            // get project attributes
            //NamedNodeMap attr = project.getAttributes();
            
            //Node nodeAttr = attr.getNamedItem("basedir");
            //nodeAttr.setTextContent("2");
            //System.out.println(nodeAttr.getTextContent());
            //nodeAttr.setTextContent("/Users/ATTJ/Desktop/code");
            //System.out.println(nodeAttr.getTextContent());
            
            NamedNodeMap srcAttr = prop1.getAttributes();
            Node srcName = srcAttr.getNamedItem("location");
            System.out.println(srcName.getTextContent());
            srcName.setTextContent(src);
            
            NamedNodeMap buildAttr = prop2.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            System.out.println(buildName.getTextContent());
            buildName.setTextContent(build);
            
            
            updateBuildFile( filepath, doc);
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }
    
    public static void setPropsForJar(String init, String buildDir, String distDir, String mainClassWithPackage) {
        
        try {
            //String filepath = "/Users/ATTJ/Desktop/xmlPlay/build.xml";
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get project 
            
            // get property, location build
            Node prop1 = doc.getElementsByTagName("property").item(0);
            // get property, location dist
            Node prop2 = doc.getElementsByTagName("property").item(1);
            
            // get target, jar
            Node jarTarget = doc.getElementsByTagName("attribute").item(0);
            // get main class attribute
            //Node mainClass = jarTarget.getFirstChild();
            Node mainClass = jarTarget;
            
            System.out.println(mainClass);
            NamedNodeMap buildAttr = prop1.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            System.out.println(buildName.getTextContent());
            buildName.setTextContent(buildDir);
            
            NamedNodeMap distAttr = prop2.getAttributes();
            Node distName = distAttr.getNamedItem("location");
            System.out.println(distName.getTextContent());
            distName.setTextContent(distDir);
            
            NamedNodeMap mainAttr = mainClass.getAttributes();
            Node mainName = mainAttr.getNamedItem("value");
            System.out.println(mainName.getTextContent());
            mainName.setTextContent(mainClassWithPackage);
            
            updateBuildFile( filepath, doc);
            
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            pce.printStackTrace();
        }
    }
    
    public static void setPropsForJavaDoc(String init, String srcDir, String docsDir) {
        
        try {
            //String filepath = "/Users/ATTJ/Desktop/xmlPlay/build.xml";
            String filepath = init;
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(filepath);
            
            // get property, location src
            Node prop1 = doc.getElementsByTagName("property").item(0);
            // get property, location docs
            Node prop2 = doc.getElementsByTagName("property").item(1);
            
            NamedNodeMap buildAttr = prop1.getAttributes();
            Node buildName = buildAttr.getNamedItem("location");
            System.out.println(buildName.getTextContent());
            buildName.setTextContent(srcDir);
            
            NamedNodeMap distAttr = prop2.getAttributes();
            Node distName = distAttr.getNamedItem("location");
            System.out.println(distName.getTextContent());
            distName.setTextContent(docsDir);
            
            updateBuildFile( filepath, doc);
            
        } catch (ParserConfigurationException | IOException | SAXException pce) {
            pce.printStackTrace();
        }
    }
    
    
    public static void updateBuildFile(String filepath, Document doc) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filepath));
            transformer.transform(source, result);
            
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } 
        
    }
    
    public static void altCompile( String file, PrintStream out, PrintStream err) {
        
        // File buildFile = new File("build.xml");
        File buildFile = new File(file);
        Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());     
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(err);
        consoleLogger.setOutputPrintStream(out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        
        try {
            p.fireBuildStarted();
            p.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            p.addReference("ant.projectHelper", helper);
            helper.parse(p, buildFile);
            p.executeTarget(p.getDefaultTarget());
            p.fireBuildFinished(null);
        } catch (BuildException e) {
            p.fireBuildFinished(e);
        }
    }
    
    public static void compile( String file) {
        
        // File buildFile = new File("build.xml");
        File buildFile = new File(file);
        Project p = new Project();
        p.setUserProperty("ant.file", buildFile.getAbsolutePath());     
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        p.addBuildListener(consoleLogger);
        
        try {
            p.fireBuildStarted();
            p.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            p.addReference("ant.projectHelper", helper);
            helper.parse(p, buildFile);
            p.executeTarget(p.getDefaultTarget());
            p.fireBuildFinished(null);
        } catch (BuildException e) {
            p.fireBuildFinished(e);
        }
    }
    
}
