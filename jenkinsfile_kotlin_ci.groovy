pipeline {
    agent none
    stages {
        stage('Build') {
            agent any
            steps{
                checkout scm
                withGradle{
                    sh('./gradle build')
                }
                sh("chmod 775 /var/lib/jenkins/workspace/app_builder/gradlew")
            }
            post {
                always {
                    archiveArtifacts artifacts: 'app/build/outputs/apk/debug/*.apk', followSymlinks: false, onlyIfSuccessful: true
                }
            }
        }
    }
}
