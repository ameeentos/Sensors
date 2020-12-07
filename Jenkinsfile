pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'chmod 775 /var/lib/jenkins/workspace/app_builder/gradlew'
        withGradle() {
          build 'build'
        }

      }
    }

    stage('Post-Build') {
      steps {
        archiveArtifacts 'app/build/outputs/apk/debug/*.apk'
      }
    }

  }
}