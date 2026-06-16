pipeline {
    agent any

    tools {
        jdk 'java8'
    }

    stages {
        stage('Build') {
            agent {label 'Linux-Build'}
            steps {
                cleanWs()
        
                checkout scm
                sh './gradlew build -PbuildType=snapshot'

                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
        }
    }
}
