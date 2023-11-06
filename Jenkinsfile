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
        stage('Generate JaCoCo Coverage Report') {
             steps {
                  sh 'mvn jacoco:report'
                    }
                }
         stage('artifact construction') {
                    steps {
                          sh 'mvn package'
                            }
                        }
         stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=maman'
            }
        }
        stage('Deploy Nexus') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://http://192.168.33.10:8081/repository/maven-releases/'
            }
        }

    }


}
