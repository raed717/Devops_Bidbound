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






        stage('Verify Docker Compose Installation') {
                 steps {
                                sh 'docker compose version'
                                }
                            }
                            stage('Docker Compose') {
                                steps {
                                    sh 'docker compose -p devops_project_firas up -d'

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
