import jenkins.model.Jenkins;
import jenkins.model.*;
import hudson.plugins.sonar.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Sonar plugin"
println "///////////////////////////////////////////////////////////////////////////"
def instance = Jenkins.instance

def sonar_url = "https://sonar.corp.example.com"
def sonar_token = "12345"

def sonarGlobalConfiguration = instance.getDescriptor("SonarGlobalConfiguration")
def sonarInstallation = new SonarInstallation(
        'sonar', //name
        sonar_url, //serverUrl
        '5.3', //serverVersion
        sonar_token, //serverAuthenticationToken
        '', //databaseUrl
        '', //databaseLogin
        '', //databasePassword
        '', //mojoVersion
        '', //additionalProperties
        new hudson.plugins.sonar.model.TriggersConfig(), //triggers
        '', //sonarLogin
        '', //sonarPassword
        '' //additionalAnalysisProperties
)
sonarGlobalConfiguration.setInstallations(sonarInstallation)
sonarGlobalConfiguration.save()


//configure the Sonar Scanner tool.
def sonarPluginExtension = Jenkins.instance.getExtensionList(hudson.plugins.sonar.SonarRunnerInstallation.DescriptorImpl.class)[0]

sonarPluginExtension.setInstallations(
        new hudson.plugins.sonar.SonarRunnerInstallation('Default',null,
                [new hudson.tools.InstallSourceProperty([new hudson.plugins.sonar.SonarRunnerInstaller('3.0.1.733')])]
        )
)