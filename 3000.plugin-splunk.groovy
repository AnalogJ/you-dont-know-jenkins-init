import jenkins.model.Jenkins;
import jenkins.model.*;
import com.splunk.splunkjenkins.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Splunk plugin"
println "///////////////////////////////////////////////////////////////////////////"

def splunk_app_url = "https://splunk.corp.example.com/en-US/app/splunk_app_jenkins/"
def splunk_collector_hostname = "splunk-collector.corp.example.com"
def splunk_collector_token = "123456"
def splunk_collector_port = 8088
def splunk_index = "jenkins_index_name"
def jenkins_hostname = "jenkins.corp.example.com"

def splunkInstallation = SplunkJenkinsInstallation.get()
splunkInstallation.setEnabled(true)
splunkInstallation.setHost(splunk_collector_hostname)
splunkInstallation.setPort(splunk_collector_port)
splunkInstallation.setToken(splunk_collector_token)
splunkInstallation.setUseSSL(true)
splunkInstallation.setMetadataHost(jenkins_hostname)

splunkInstallation.setMetaDataConfig("""
        console_log.index=${splunk_index}
        build_event.index=${splunk_index}
      """.stripIndent())
splunkInstallation.setMetadataSource('jenkins')
splunkInstallation.setSplunkAppUrl(splunk_app_url)
splunkInstallation.save()