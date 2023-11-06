pipeline {
    agent any

     environment {
        registryCredential = 'Docker_credential'
    }

    stages {
        stage('Checkout Git') {
            steps {
                script {
                    echo 'Pulling...'
                    git branch: 'raed', url: 'https://github.com/raed717/Devops_Bidbound.git'
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
        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=rg123717'
            }
        }
        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Integration Tests') {
            steps {
                sh 'mvn integration-test'
            }
        }
        //livrable
        stage('artifact construction') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Deploy Nexus') {
            steps {
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
            }
        }
        stage('Generate JaCoCo Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t rg123717/devops_project:latest .'
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    // Authenticate to Docker Hub using your credentials
                    sh "echo \$Linq2016** | docker login -u \$rg123717 --password-stdin"

                    // Push the Docker image to Docker Hub
                    sh "docker push rg123717/devops_project:latest"
                }
            }
        }
        stage('Run Docker Container') {
            steps {
                script {
                    sh 'docker run -d -p 8085:8085 --name devops_container rg123717/devops_project:latest'
                }
            }
        }

    }
    post {
        always {
            cleanWs()
        }
    }
}