pipeline {
    agent any

    tools {
        jdk 'java8'
    }

    parameters {
        choice(
            name: 'BUILD_TYPE',
            choices: ['snapshot', 'beta', 'release'],
            description: 'Versionstyp des Builds'
        )
    }

    stages {
        stage('Build') {
            agent {label 'Linux-Build'}
            
            steps {
                cleanWs()
        
                checkout scm
                sh './gradlew build -PbuildType=${params.BUILD_TYPE}'

                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
        }
    }
}
