import java.lang.reflect.*;
import jenkins.model.Jenkins;
import jenkins.model.*;
import org.jenkinsci.plugins.scriptsecurity.scripts.*;
import org.jenkinsci.plugins.scriptsecurity.sandbox.whitelists.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Whitelist methods/functions for script approval plugin"
println "///////////////////////////////////////////////////////////////////////////"


def scriptApproval = ScriptApproval.get()

// whitelist all org.codehaus.groovy.runtime.DefaultGroovyMethods methods.
Class c = org.codehaus.groovy.runtime.DefaultGroovyMethods.class;
Method[] methods = c.getDeclaredMethods();
for (int i = 0; i < methods.length; i++){
    def m = methods[i]

    //WARNING: this may white list some methods that may introduce security vulnerabilities, you should manually validate this list.
    scriptApproval.approveSignature("staticMethod ${EnumeratingWhitelist.getName(m.getDeclaringClass())} ${m.getName()}${printArgumentTypes(m.getParameterTypes())}")
}

// add all manual whitelist methods here.
scriptApproval.approveSignature('field hudson.model.AbstractItem name')
scriptApproval.approveSignature('field hudson.model.Cause$UpstreamCause upstreamCauses')
scriptApproval.approveSignature('field java.util.HashMap$Entry key')
scriptApproval.approveSignature('field java.util.HashMap$Entry value')
scriptApproval.approveSignature('method groovy.json.JsonBuilder call java.util.List')
scriptApproval.approveSignature('method groovy.json.JsonSlurper parseText java.lang.String')
scriptApproval.approveSignature('method groovy.json.JsonSlurperClassic parseText')
scriptApproval.approveSignature('method hudson.model.Actionable getAction java.lang.Class')
scriptApproval.approveSignature('method hudson.model.Actionable getActions')
scriptApproval.approveSignature('method hudson.model.ItemGroup getItem java.lang.String')
scriptApproval.approveSignature('method hudson.model.Item getUrl')
scriptApproval.approveSignature('method hudson.model.Job getBuildByNumber int')
scriptApproval.approveSignature('method hudson.model.Job getLastBuild')
scriptApproval.approveSignature('method hudson.model.Job getLastSuccessfulBuild')
scriptApproval.approveSignature('method hudson.model.Job isBuilding')
scriptApproval.approveSignature('method hudson.model.Run getCauses')
scriptApproval.approveSignature('method hudson.model.Run getEnvironment hudson.model.TaskListener')
scriptApproval.approveSignature('method hudson.model.Run getParent')
scriptApproval.approveSignature('method hudson.model.Run getNumber')
scriptApproval.approveSignature('method hudson.model.Run getResult')
scriptApproval.approveSignature('method hudson.model.Run getUrl')
scriptApproval.approveSignature('method hudson.model.Run getLogFile')
scriptApproval.approveSignature('method java.io.InputStream getText')
scriptApproval.approveSignature('method java.lang.Appendable append java.lang.CharSequence')
scriptApproval.approveSignature('method java.lang.Class isInstance java.lang.Object')
scriptApproval.approveSignature('method java.lang.Object clone')
scriptApproval.approveSignature('method java.lang.Object getClass')
scriptApproval.approveSignature('method java.lang.String contains java.lang.CharSequence')
scriptApproval.approveSignature('method java.lang.String getBytes')
scriptApproval.approveSignature('method java.lang.String replaceAll java.lang.String java.lang.String')
scriptApproval.approveSignature('method java.lang.String toURL')
scriptApproval.approveSignature('method java.lang.String trim')
scriptApproval.approveSignature('method java.lang.Throwable getMessage')
scriptApproval.approveSignature('method java.net.URL getContent')
scriptApproval.approveSignature('method java.net.URL getText')
scriptApproval.approveSignature('method java.security.MessageDigest digest byte[]')
scriptApproval.approveSignature('method java.text.Format format java.lang.Object')
scriptApproval.approveSignature('method java.util.Collection remove java.lang.Object')
scriptApproval.approveSignature('method java.util.Collection removeAll java.util.Collection')
scriptApproval.approveSignature('method java.util.Collection retainAll java.util.Collection')
scriptApproval.approveSignature('method java.util.Map containsKey java.lang.Object')
scriptApproval.approveSignature('method java.util.Map entrySet')
//... Any others you would like to manually whitelist.

scriptApproval.save()

// Utility methods
String printArgumentTypes(Object[] args) {
    StringBuilder b = new StringBuilder();
    for (Object arg : args) {
        b.append(' ');
        b.append(EnumeratingWhitelist.getName(arg));
    }
    return b.toString();
}