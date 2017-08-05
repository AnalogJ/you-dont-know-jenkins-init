import jenkins.model.*;
import hudson.tasks.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Configure admin email address, hostname, smtp"
println "///////////////////////////////////////////////////////////////////////////"
def instance = Jenkins.instance

def jenkins_username = 'jenkins-ci'
def corp_email_suffix = '@example.com'
def jenkins_email_address = jenkins_username + corp_email_suffix
def jenkins_hostname = 'https://jenkins.corp.example.com'

def location = JenkinsLocationConfiguration.get()
location.setAdminAddress(jenkins_email_address)
location.setUrl(jenkins_hostname)
location.save()

def mailer = instance.getDescriptor("hudson.tasks.Mailer")
mailer.setReplyToAddress(jenkins_email_address)
mailer.setSmtpHost("localhost")
mailer.setDefaultSuffix(corp_email_suffix)
mailer.setUseSsl(false)
mailer.setSmtpPort("25")
mailer.setCharset("UTF-8")
instance.save()

def emailExtPluginExtension = Jenkins.instance.getExtensionList(hudson.plugins.emailext.ExtendedEmailPublisherDescriptor.class)[0]
emailExtPluginExtension.setDefaultSuffix(corp_email_suffix)
emailExtPluginExtension.upgradeFromMailer()
emailExtPluginExtension.save()


def gitscm = instance.getDescriptor('hudson.plugins.git.GitSCM')
gitscm.setGlobalConfigName(jenkins_username)
gitscm.setGlobalConfigEmail(jenkins_email_address)
instance.save()