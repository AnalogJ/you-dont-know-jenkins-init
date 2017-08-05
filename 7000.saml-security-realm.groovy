import jenkins.model.*
import hudson.security.*
import org.jenkinsci.plugins.saml.*

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: SAML security realm"
println "///////////////////////////////////////////////////////////////////////////"
def instance = Jenkins.getInstance()

def spEntityId = "jenkins-saml-id"
def usernameAttributeName = "username"
def emailAttributeName = "email"
def groupsAttributeName = "groups"
def idpMetadata = """
XML PAYLOAD SAML METADATA
"""

def advConfig = new SamlAdvancedConfiguration(
        false,                              // Boolean forceAuthn
        "",                                 // String authnContextClassRef,
        spEntityId,                         // String spEntityId,
        null                                // Integer maximumSessionLifetime
)

def securityRealm = new SamlSecurityRealm(
        "",                                 // String signOnUrl
        idpMetadata,                        // String idpMetadata,
        "",                                 // String displayNameAttributeName,
        groupsAttributeName,                // String groupsAttributeName,
        null,                               // Integer maximumAuthenticationLifetime,
        usernameAttributeName,              // String usernameAttributeName,
        emailAttributeName,                 // String emailAttributeName,
        "",                                 // String logoutUrl,
        advConfig,                          // SamlAdvancedConfiguration advancedConfiguration,
        null                                // SamlEncryptionData encryptionData,
)


instance.setSecurityRealm(securityRealm)
instance.save()