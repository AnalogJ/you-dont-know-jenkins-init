import jenkins.model.*;
import org.jenkinsci.plugins.periodicbackup.*;

println "///////////////////////////////////////////////////////////////////////////"
println "Init Hook: Periodic backups"
println "///////////////////////////////////////////////////////////////////////////"
def jenkins_config_backup_path = '/var/jenkins_config_backup'

def periodicBackup = PeriodicBackupLink.get()
periodicBackup.setTempDirectory('/tmp/jenkins_backup_temp')
periodicBackup.setCron('0 10 * * *')
periodicBackup.setCycleQuantity(30)
periodicBackup.setCycleDays(30)
periodicBackup.setFileManagerPlugin(new ConfigOnly())
def periodicBackupStorages = periodicBackup.getStorages()
if(periodicBackupStorages.size() == 0){
    periodicBackupStorages.add(new TarGzStorage())
}
def periodicBackupLocations = periodicBackup.getLocations()
if(periodicBackupLocations.size() == 0){
    periodicBackupLocations.add(new LocalDirectory(new File(jenkins_config_backup_path),true))
    }
    else{
      periodicBackupLocations[0].setPath(new File(jenkins_config_backup_path))
}
println(periodicBackupLocations[0].getPath())
periodicBackup.save()
