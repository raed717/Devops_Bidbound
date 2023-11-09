 pipeline {
    agent any



    stages {
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
                    sh 'mvn clean compile'
            }
        }

stage('Push Docker Image') {
    steps {
        script {
            // Check if the Docker image already exists locally
            def dockerImageExists = sh(script: 'docker images -q firasyazid12/devops_project_firas:test', returnStatus: true) == 0

            if (!dockerImageExists) {
                // Build the Docker image if it doesn't exist
                sh 'docker build -t firasyazid12/devops_project_firas:test -f  Dockerfile .'
            }

            // Log in to Docker Hub and push the image
            withCredentials([usernamePassword(credentialsId: 'dockerhubpwd', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                sh "echo \$DOCKER_PASSWORD | docker login -u firasyazid12 --password-stdin"
                sh 'docker push firasyazid12/devops_project_firas:test'
            }
        }
    }
}





        stage('Verify Docker Compose Installation') {
                 steps {
                                sh 'docker compose version'
                                }
                            }
                            stage('Docker Compose') {
                                steps {
                                    sh 'docker compose -p project_firas up -d'

                                }
                            }
                            stage('check Docker Compose') {
                                steps {
                                    sh 'docker compose ps'
                                }
                            }




    }

      post {
              always {
                  script {
                       sh 'docker compose down'
                  }
              }
          }


}
