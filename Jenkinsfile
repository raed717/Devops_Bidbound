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
        sh 'docker build -t rg123717/devops_project:latest .'
        def danglingImages = sh(script: 'docker images -q --filter "dangling=true"', returnStdout: true).trim()
        
        if (!danglingImages.isEmpty()) {
            // Get a list of container IDs using the dangling image
            def containerIDs = sh(script: "docker ps -a --format '{{.ID}} {{.Image}}' | grep ${danglingImages} | awk '{print \$1}'", returnStdout: true).trim()

            // Stop and remove containers using the dangling image
            sh "docker stop ${containerIDs}"
            sh "docker rm ${containerIDs}"

            // Remove the dangling image
            sh "docker rmi ${danglingImages}"
        }

        withCredentials([string(credentialsId: 'docker-credential', variable: 'DOCKER_PASSWORD')]) {
            sh "echo \$DOCKER_PASSWORD | docker login -u rg123717 --password-stdin"
            sh "docker push rg123717/devops_project:latest"
        }
    }
}



        stage('Compile') {
            steps {
                sh 'mvn clean compile'
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
        }


    }
    post {
        always {
            cleanWs()
        }
    }
}
