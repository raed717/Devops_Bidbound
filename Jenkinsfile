 pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Checkout Git') {
            steps {
                script {
                    echo 'Pulling...'
                    git branch: 'Firass', url: 'https://github.com/raed717/Devops_Bidbound.git'
                }
            }
        }
        stage('Check Maven Version') {
            steps {
                script {
                    def mavenVersion = sh(script: 'mvn --version', returnStatus: true)
                    if (mavenVersion == 0) {
                        echo 'Maven is installed. Here is the version:'
                        sh 'mvn -version'
                    } else {
                        error 'Maven is not installed.'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Compile') {
         steps {
                    sh 'mvn compile'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

         stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=maman'
            }
        }
    }


}
