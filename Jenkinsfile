pipeline {
    agent any

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
        stage('Build and Push Docker Image') {
            when {
                expression {
                    def dockerImageExists = sh(script: "docker image ls | grep -q 'rg123717/devops_project'", returnStatus: true)
                    def hasChangeset = currentBuild.changeSets.size() > 0
                    return dockerImageExists != 0 || hasChangeset
                }
            }
            steps {
                script {
                    // Check if the container is already running
                    def image_name = "rg123717/devops_project"

                    def isContainerRunning = sh(script: "docker ps -q -f ancestor=${image_name}", returnStatus: true)
                    if (isContainerRunning == 0) {
                        // Container is already running, stop and remove it
                        sh "docker stop \$(docker ps -q -f ancestor=${image_name})"
                        sh "docker rm \$(docker ps -aq -f ancestor=${image_name})"
                    }

                    sh 'docker build -t rg123717/devops_project:latest .'
                    //sh 'docker run -d -p 8085:8085 rg123717/devops_project'

                    def danglingImages = sh(script: 'docker images -q --filter "dangling=true"', returnStdout: true).trim()
                    if (!danglingImages.isEmpty()) {
                        // Obtain a list of container IDs using the dangling image
                        def containerIDs = sh(script: "docker ps -a --format '{{.ID}} {{.Image}}' | grep ${danglingImages} | awk '{print \$1}'", returnStdout: true).trim()

                        // Split the container IDs into an array
                        def containerIDsArray = containerIDs.tokenize()

                        // Stop and remove each container
                        for (def containerID in containerIDsArray) {
                            // Stop each container
                            sh "docker stop $containerID"
                            // Remove the stopped containers
                            sh "docker rm $containerID"
                        }

                        // Remove the dangling image
                        sh "docker rmi ${danglingImages}"
                    }
                }

            withCredentials([string(credentialsId: 'docker-credential', variable: 'DOCKER_PASSWORD')]) {
            sh "echo \$DOCKER_PASSWORD | docker login -u rg123717 --password-stdin"
            sh "docker push rg123717/devops_project:latest"}
            }
        }
        stage('Verify Docker Compose Installation') {
            steps {
                sh 'docker compose version'    
            }
        }
        stage('Docker Compose') {
            steps {
                sh 'docker compose up -d'
                sh 'docker compose down'
                sh 'docker compose up -d'    
            }
        }
        stage('check Docker Compose') {
            steps {
                sh 'docker compose ps'    
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }
        /* 
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
        stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=rg123717'
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
        }*/


    }
    post {
        always {
            cleanWs()
        }
    }
}
