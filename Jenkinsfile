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

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }


        stage('Build Docker Image') {
            steps {
                script {
            sh 'docker build -t firasyazid12/devops_project_firas:test -f  Dockerfile .'
                }
                }
                 }



               stage('Push Docker Image') {
                          steps {
                              script {
                        withCredentials([usernamePassword(credentialsId: 'dockerhubpwd', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                  sh "echo \$DOCKER_PASSWORD | docker login -u firasyazid12 --password-stdin"
                                  }
                                  sh 'docker push firasyazid12/devops_project_firas:test'
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
                                    sh 'docker compose up -d'
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
                cleanWs()
            }
        }


}
