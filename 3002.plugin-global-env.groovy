import jenkins.model.Jenkins;
import jenkins.model.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Global Env plugin"
println "///////////////////////////////////////////////////////////////////////////"

def instance = Jenkins.instance
def globalNodeProperties = instance.getGlobalNodeProperties()
def envVarsNodePropertyList = globalNodeProperties.getAll(hudson.slaves.EnvironmentVariablesNodeProperty.class)
def envVars

if ( envVarsNodePropertyList == null || envVarsNodePropertyList.size() == 0 ) {
    def newEnvVarsNodeProperty = new hudson.slaves.EnvironmentVariablesNodeProperty();
    globalNodeProperties.add(newEnvVarsNodeProperty)
    envVars = newEnvVarsNodeProperty.getEnvVars()
} else {
    envVars = envVarsNodePropertyList.get(0).getEnvVars()
}
envVars.put('CUSTOM_ENVIRONMENTAL_VAR', 'variable_value')

instance.save()