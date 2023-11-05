pipeline {
    agent any
    cleanWs()
    stages {
        stage('Checkout Git') {
            steps {
                echo 'Pulling...'
                checkout scm
            }
        }

        stage('Check Maven Version') {
            steps {
                sh 'mvn -B -v'
            }
        }

        stage('Build and Test') {
            steps {
                parallel(
                    "Compile": {
                        sh 'mvn compile'
                    },
                    "Unit Tests": {
                        sh 'mvn test'
                    },
                    "Integration Tests": {
                        sh 'mvn integration-test'
                    }
                )
            }
        }

        stage('Static Code Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=rg123717'
            }
        }

        stage('Package and Deploy') {
            steps {
                sh 'mvn package'
                sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
            }
        }

        stage('Generate JaCoCo Coverage Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
